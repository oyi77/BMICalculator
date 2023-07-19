package com.mybmiapp.util;

import com.mybmiapp.models.User;

import javax.servlet.http.HttpSession;

public class UserSessionUtil {
    private static final String USER_SESSION_KEY = "currentUser";

    public static void setCurrentUser(HttpSession session, User user) {
        session.setAttribute(USER_SESSION_KEY, user);
    }

    public static User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}
