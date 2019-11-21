package cn.moonshotacademy.exceptions;

import cn.moonshotacademy.exceptions.BaseException;

public class NotEnoughStorageException extends BaseException {
    public NotEnoughStorageException(String msg) {
        super(msg);
    }
}