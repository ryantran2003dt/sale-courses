package com.salecoursecms.controller;

import com.salecoursecms.constant.UrlConst;

import com.salecoursecms.dto.request.LoginRequest;
import com.salecoursecms.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(UrlConst.AUTH)
public class AuthController {

    private final AuthService authService;

    @PostMapping(UrlConst.LOGIN)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequestDTO, HttpServletResponse httpServletResponse, Locale locale){
        return ResponseEntity.ok(authService.login(loginRequestDTO, httpServletResponse, locale));
    }
    @PostMapping(UrlConst.LOGOUT)
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        return ResponseEntity.ok(authService.logout(httpServletRequest, httpServletResponse));
    }
    @PostMapping(UrlConst.GET_INFO)
    public ResponseEntity<?> getInfo(HttpServletRequest httpServletRequest){
        return ResponseEntity.ok( authService.getInformation(httpServletRequest));
    }
    @PostMapping(UrlConst.REFRESH_TOKEN)
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(authService.refreshToken(request, response));
    }

}
