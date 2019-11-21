package cn.moonshotacademy.exceptions;

import cn.moonshotacademy.exceptions.BaseException;

public class UserNotExistException extends BaseException {
    public UserNotExistException(String msg) {
        super(msg);
    }
}