package com.salecoursecms.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseRequestGW <T>{
    private String language;
    private String token;
    private String versionApp;
    private String sessionId;
    private String username;
    private String apiKey;
    private String wsCode;
    private T wsRequest;
}
