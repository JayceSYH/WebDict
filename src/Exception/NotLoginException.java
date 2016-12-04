package Exception;

/**
 * Created by Sun YuHao on 2016/12/3.
 */
public class NotLoginException extends Exception {
    public NotLoginException() {
        super("User not login");
    }
}
