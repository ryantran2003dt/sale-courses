package com.salecourseweb.controller;

import com.salecourseweb.constant.UrlConst;
import com.salecourseweb.dto.request.IdTokenRequest;
import com.salecourseweb.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(UrlConst.OAUTH)
public class AuthCotroller {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity LoginWithGoogleOauth2(@RequestBody IdTokenRequest requestBody, HttpServletResponse response) {
        try{
            log.info("-------------- "+requestBody);
            String authToken = authService.loginOAuthGoogle(requestBody);
            final ResponseCookie cookie = ResponseCookie.from("AUTH-TOKEN", authToken)
                    .httpOnly(true)
                    .maxAge(7 * 24 * 3600)
                    .path("/")
                    .secure(false)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            log.info("[Token] "+ authToken);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("[ERROR] " + e.getMessage());
            return ResponseEntity.ok().build();
        }
    }
}
