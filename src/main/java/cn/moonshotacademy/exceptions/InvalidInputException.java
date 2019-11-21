package cn.moonshotacademy.exceptions;

import cn.moonshotacademy.exceptions.BaseException;

public class InvalidInputException extends BaseException {
    public InvalidInputException(String msg) {
        super(msg);
    }
}