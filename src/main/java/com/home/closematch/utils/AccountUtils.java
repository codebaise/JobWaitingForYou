package com.home.closematch.utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountUtils {
    public static final Integer USER_TYPE_SEEKER = 0;
    public static final Integer USER_TYPE_HR = 1;
    public static final Integer USER_TYPE_ADMIN = 2;

    public static final Integer USER_INFO_FILL_LEVEL_NO_BASE_INFO = 0;
    public static final Integer USER_INFO_FILL_LEVEL_NO_COMPANY = 1;
    public static final Integer USER_INFO_FILL_LEVEL_HR_SUCCESS = 2;

    public static final String USER_ROLE_HR = "hr";
    public static final String USER_ROLE_SEEKER = "seeker";
    public static final String USER_ROLE_ADMIN = "admin";

    public static String getHomeLink(Integer userType) {
        String link = "";
        if (USER_TYPE_SEEKER.equals(userType))
            link = "/account/home";
        if (USER_TYPE_HR.equals(userType))
            link = "http://localhost:6789/";
        if (USER_TYPE_ADMIN.equals(userType))
            link = "http://localhost:9528/";
        return link;
    }

    public static List<SimpleGrantedAuthority> getAuthorities(String role) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    public static List<String> getRoles(Integer userType){
        ArrayList<String> roles = new ArrayList<>();
        switch(userType) {
            case 0:
                roles.add(AccountUtils.USER_ROLE_SEEKER);
                break;
            case 1:
                roles.add(AccountUtils.USER_ROLE_HR);
                break;
            case 2:
                roles.add(AccountUtils.USER_ROLE_ADMIN);
                break;
        }
        return roles;
    }
}
