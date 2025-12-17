CREATE OR REPLACE FUNCTION generate_merchants(num_rows INTEGER DEFAULT 1000000)
RETURNS VOID AS $$
DECLARE
    last_merchant_id INTEGER;
BEGIN
	SELECT COALESCE(MAX(id), 0) INTO last_merchant_id FROM postgres.merchants;

    INSERT INTO postgres.merchants(name, email, webhook, tax_number, login, password, created_at, updated_at)
	SELECT
			SUBSTRING(md5(random()::text), 1, 20),
	        'user' || floor(random() * num_rows)::int || '@gmail.com',
	        'https://' || floor(random() * num_rows)::int || '.com',
	        LPAD(s.id::text, 9, '0'),
	        SUBSTRING(md5(random()::text), 1, 10),
	        '$2a$10$yXWnv64by25Wz3UH9m7b8Oq3aA7OdJLqtxt6JziCsROY2irJXAA4O',
	        '2000-01-01'::date + trunc(random() * 7300)::int,
	        '2000-01-01'::date + trunc(random() * 7300)::int
	FROM (
		SELECT generate_series(last_merchant_id+1, last_merchant_id+num_rows) AS id
	) s;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION generate_notifications(num_rows INTEGER DEFAULT 1000000)
RETURNS VOID AS $$
DECLARE
    per_merchant INTEGER;
    last_notif_id INTEGER;
BEGIN
	SELECT num_rows / COALESCE(COUNT(id), 1) INTO per_merchant FROM postgres.merchants;
	SELECT COALESCE(MAX(id), 0) INTO last_notif_id FROM postgres.notifications;

    INSERT INTO postgres.notifications(merchant_id, receiver_info, type, text, status, created_at, updated_at)
    SELECT
        m.id,
		'user' || floor(random() * num_rows)::int || '@gmail.com',
       	'EMAIL'::postgres.notification_type,
        SUBSTRING(md5(random()::text), 1, 50),
        (CASE floor(random() * 3)::int
            WHEN 0 THEN 'CREATED'
            WHEN 1 THEN 'SENT'
            ELSE 'FAILED'
        END)::postgres.notification_status,
        '2000-01-01'::date + trunc(random() * 7300)::int,
        '2000-01-01'::date + trunc(random() * 7300)::int
    FROM
        postgres.merchants m
    CROSS JOIN
        generate_series(last_notif_id+1, last_notif_id+per_merchant) s;
END;
$$ LANGUAGE plpgsql;

truncate postgres.merchants cascade;

select generate_merchants(2000::integer);

select generate_notifications(10000000::integer);