package com.reda.engine.center.common.groovy.exception;

/**
 * 功能概述：
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-06-12 13-53
 * <p>Copyright: Copyright(c)2019 RedaFlight.com All Rights Reserved
 * @since JDK 1.7
 */
public class RuleException extends Exception {

    public RuleException() {

    }

    public RuleException(String message) {
        super(message);
    }

    public RuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleException(Throwable cause) {
        super(cause);
    }

    protected RuleException(String message, Throwable cause,
                            boolean enableSuppression,
                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
