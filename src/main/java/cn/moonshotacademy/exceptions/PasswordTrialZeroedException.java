package cn.moonshotacademy.exceptions;

import cn.moonshotacademy.exceptions.BaseException;

public class PasswordTrialZeroedException extends BaseException {
    public PasswordTrialZeroedException(String msg) {
        super(msg);
    }
}