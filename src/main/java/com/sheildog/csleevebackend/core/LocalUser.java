package com.sheildog.csleevebackend.core;

import com.sheildog.csleevebackend.model.User;

import java.util.HashMap;
import java.util.Map;

public class LocalUser {

    private static ThreadLocal<Map<String, Object>> mapThreadLocal = new ThreadLocal<>();

    public static void set(User user, Integer scope) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("scope", scope);
        LocalUser.mapThreadLocal.set(map);
    }

    public static void clear() {
        LocalUser.mapThreadLocal.remove();
    }

    public static User getUser() {
        Map<String, Object> map = LocalUser.mapThreadLocal.get();
        User user = (User) map.get("user");
        return user;
    }

    public static Integer getScope() {
        Map<String, Object> map = LocalUser.mapThreadLocal.get();
        Integer scope = (Integer) map.get("scope");
        return scope;
    }
}
