package cn.moonshotacademy.exceptions;

import cn.moonshotacademy.exceptions.BaseException;

public class PasswordIncorrectException extends BaseException {
    public PasswordIncorrectException(String msg) {
        super(msg);
    }
}