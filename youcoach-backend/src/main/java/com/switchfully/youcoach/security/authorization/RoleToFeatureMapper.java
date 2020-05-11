package com.switchfully.youcoach.security.authorization;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.switchfully.youcoach.security.authorization.Feature.ACCEPT_SESSION;
import static com.switchfully.youcoach.security.authorization.Feature.REQUEST_SESSION;

@Component
public class RoleToFeatureMapper {

    public List<Feature> mapRoleToFeature(Role role) {
        switch (role) {
            case COACH:
                return newArrayList(ACCEPT_SESSION);
            case STUDENT:
                return newArrayList(REQUEST_SESSION);
        }
        return new ArrayList<>();
    }
}
