package cn.iyowei.stockai.exception;

/**
 * 用户未授权访问异常
 *
 * @author - Vick.liu (vicklin123@gmail.com)
 */
public class UserUnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = 325627226489094585L;

    public UserUnauthorizedException() {
        super();
    }

    public UserUnauthorizedException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UserUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserUnauthorizedException(String message) {
        super(message);
    }

    public UserUnauthorizedException(Throwable cause) {
        super(cause);
    }

}
