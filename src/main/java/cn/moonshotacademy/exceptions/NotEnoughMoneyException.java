package cn.moonshotacademy.exceptions;

import cn.moonshotacademy.exceptions.BaseException;

public class NotEnoughMoneyException extends BaseException {
    public NotEnoughMoneyException(String msg) {
        super(msg);
    }
}