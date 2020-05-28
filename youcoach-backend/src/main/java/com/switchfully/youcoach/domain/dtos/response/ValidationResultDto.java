package com.switchfully.youcoach.domain.dtos.response;

public class ValidationResultDto {
    private boolean emailAddressValidated;

    public ValidationResultDto(){}
    public ValidationResultDto(boolean emailAddressValidated){
        this.emailAddressValidated = emailAddressValidated;
    }

    public boolean isEmailAddressValidated() {
        return emailAddressValidated;
    }
}
