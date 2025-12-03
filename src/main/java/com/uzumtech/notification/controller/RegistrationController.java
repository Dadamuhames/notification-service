package com.uzumtech.notification.controller;


import com.uzumtech.notification.dto.request.RegistrationRequest;
import com.uzumtech.notification.dto.response.CommonResponse;
import com.uzumtech.notification.dto.response.RegistrationResponse;
import com.uzumtech.notification.service.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class RegistrationController {
    private final RegisterService registerService;


    @PostMapping("/registration")
    public ResponseEntity<CommonResponse<RegistrationResponse>> registration(@Valid @RequestBody RegistrationRequest request) {
        CommonResponse<RegistrationResponse> response = registerService.register(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
