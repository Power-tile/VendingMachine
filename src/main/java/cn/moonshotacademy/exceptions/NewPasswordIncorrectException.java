package cn.moonshotacademy.exceptions;

import cn.moonshotacademy.exceptions.BaseException;

public class NewPasswordIncorrectException extends BaseException {
    public NewPasswordIncorrectException(String msg) {
        super(msg);
    }
}