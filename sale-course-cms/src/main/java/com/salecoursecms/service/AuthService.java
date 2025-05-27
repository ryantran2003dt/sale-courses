package com.salecoursecms.service;

import com.salecoursecms.dto.reponse.BaseReponse;
import com.salecoursecms.dto.request.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Locale;

public interface AuthService {
    BaseReponse<?> login(LoginRequest loginRequestDTO, HttpServletResponse httpServletResponse, Locale locale);
    BaseReponse<?> logout(HttpServletRequest request, HttpServletResponse response);
    BaseReponse<?> getInformation(HttpServletRequest httpServletRequest);
    BaseReponse<?> refreshToken(HttpServletRequest request, HttpServletResponse response);
}
