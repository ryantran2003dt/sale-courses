package com.salecourseweb.service;

import com.salecourseweb.dto.request.IdTokenRequest;

public interface AuthService {
    String loginOAuthGoogle(IdTokenRequest requestBody);
}
