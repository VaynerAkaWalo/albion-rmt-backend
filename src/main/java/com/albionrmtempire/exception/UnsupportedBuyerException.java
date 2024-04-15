package com.albionrmtempire.exception;

public class UnsupportedBuyerException extends RuntimeException {

    public UnsupportedBuyerException(String buyer) {
        super("%s is not a supported buyer".formatted(buyer));
    }
}
