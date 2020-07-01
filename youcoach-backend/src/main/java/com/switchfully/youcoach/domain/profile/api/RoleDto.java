package com.switchfully.youcoach.domain.profile.api;

public class RoleDto {

    private final String name;
    private final String label;

    public RoleDto(String name, String label) {
        this.name = name;
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }
}
