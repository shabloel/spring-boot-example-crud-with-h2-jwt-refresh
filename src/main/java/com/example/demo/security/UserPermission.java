package com.example.demo.security;

public enum UserPermission {

    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");

    private String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }
}