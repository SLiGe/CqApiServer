package cn.fanlisky.api.exception;

/**
 * @author Gary
 * @since 2020/01/28 16:47
 */
public class DownFileException extends RuntimeException {
    public DownFileException(String message) {
        super(message);
    }

    public DownFileException() {
    }
}
