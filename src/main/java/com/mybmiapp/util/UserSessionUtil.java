package com.mybmiapp.util;

import com.mybmiapp.models.User;

public class UserSessionUtil {

    private static UserSessionUtil instance;

    private User user;

    private UserSessionUtil(User user) {
        this.user = user;
    }

    public static UserSessionUtil getInstance(User user) {
        if(instance == null) {
            instance = new UserSessionUtil(user);
        }
        return instance;
    }

    public User getCurrentUser() {
        return user;
    }

    public void cleanUserSession() {
        user = null;
        instance = null;
    }

    @Override
    public String toString() {
        return "UserSessionUtil{" +
                "user=" + user +
                '}';
    }
}
