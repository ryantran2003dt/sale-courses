package com.salecourseweb.service;

import com.salecourseweb.dto.reponse.BaseReponse;
import com.salecourseweb.dto.request.IdTokenRequest;

public interface AuthService {
    BaseReponse<?> loginOAuthGoogle(IdTokenRequest requestBody);
}
