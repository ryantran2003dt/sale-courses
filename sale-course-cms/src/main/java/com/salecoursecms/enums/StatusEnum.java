package com.salecoursecms.enums;

public enum StatusEnum {
    ACTIVE("ACTIVE", 0),
    INACTIVE("INACTIVE", 1),
    ON("ON",0),
    OFF("OFF",1);

    StatusEnum(String status, int code) {
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    private final String status;
    private final int code;
}
