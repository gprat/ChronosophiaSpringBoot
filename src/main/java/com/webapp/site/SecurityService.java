package com.webapp.site;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
