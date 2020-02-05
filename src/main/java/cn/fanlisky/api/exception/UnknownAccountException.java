package cn.fanlisky.api.exception;

public class UnknownAccountException extends RuntimeException {
    public UnknownAccountException() {
    }

    public UnknownAccountException(String message) {
        super(message);
    }
}
