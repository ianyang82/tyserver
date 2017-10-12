package tv.huan.master.common.enums;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2014/9/1
 * Time: 17:23
 * To change this template use File | Settings | File Templates
 */
public enum StatusEnum {
    SUCCESS(0, "操作成功"),
    ERROR(1, "访问失败"),
    AUTHERROR(2, "权限验证失败"),
    ;
    private final int error;
    private final String msg;

    StatusEnum(final int error, String msg) {
        this.error = error;
        this.msg = msg;
    }

    public static StatusEnum valueof(int code) {
        for (StatusEnum f : StatusEnum.values()) {
            if (f.error == code) {
                return f;
            }
        }
        return null;
    }

    public int getError() {
        return error;
    }

    public String getMsg() {
        return msg;
    }
}
