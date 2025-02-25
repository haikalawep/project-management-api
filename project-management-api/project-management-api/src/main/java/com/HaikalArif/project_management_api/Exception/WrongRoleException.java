package com.HaikalArif.project_management_api.Exception;

public class WrongRoleException extends RuntimeException{
    public WrongRoleException(String message) {
        super(message);
    }
}