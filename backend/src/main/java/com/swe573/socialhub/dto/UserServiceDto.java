package com.swe573.socialhub.dto;

public class UserServiceDto {
    private Boolean hasServiceRequest;
    private Boolean ownsService;

    public UserServiceDto(Boolean hasServiceRequest, Boolean ownsService) {
        this.hasServiceRequest = hasServiceRequest;
        this.ownsService = ownsService;
    }

    public Boolean getHasServiceRequest() {
        return hasServiceRequest;
    }

    public Boolean getOwnsService() {
        return ownsService;
    }



}
