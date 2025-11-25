package com.uzumtech.notification.controller;


import com.uzumtech.notification.dto.request.NotificationSendRequest;
import com.uzumtech.notification.dto.request.RegistrationRequest;
import com.uzumtech.notification.dto.response.CommonResponse;
import com.uzumtech.notification.dto.response.NotificationSendResponse;
import com.uzumtech.notification.dto.response.RegistrationResponse;
import com.uzumtech.notification.entity.MerchantEntity;
import com.uzumtech.notification.service.NotificationService;
import com.uzumtech.notification.service.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {
    private final RegisterService registerService;
    private final NotificationService notificationService;

    @PostMapping("/registration")
    public ResponseEntity<CommonResponse<RegistrationResponse>> registration(@Valid @RequestBody final RegistrationRequest request) {
        CommonResponse<RegistrationResponse> response = registerService.register(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("/sending")
    public ResponseEntity<CommonResponse<NotificationSendResponse>> sending(@Valid @RequestBody final NotificationSendRequest request, @AuthenticationPrincipal final MerchantEntity merchant) {
        CommonResponse<NotificationSendResponse> response = notificationService.send(request, merchant);

        return ResponseEntity.ok(response);
    }
}
