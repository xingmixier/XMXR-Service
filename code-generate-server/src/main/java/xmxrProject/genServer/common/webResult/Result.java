package xmxrProject.genServer.common.webResult;

import xmxrProject.genServer.common.baseMod.BaseEntity;

/**
 * @Aether 行米希尔
 * @Date 2020/7/11 17:27
 */
public class Result extends BaseEntity<Result> {

    public static final int SUCCESS = 0;
    public static final int WARNING = 2;
    public static final int ERROR = 4;


    private int status = ERROR;
    private String msg = "操作失败";
    private String errorInfo;
    private Object data;

    public Result() {
    }

    public Result(int code, String msg, String errorInfo, Object data) {
        this.status = code;
        this.msg = msg;
        this.errorInfo = errorInfo;
        this.data = data;
    }

    public void setResult(int code, String msg, String errorInfo, Object data) {
        this.status = code;
        this.msg = msg;
        this.errorInfo = errorInfo;
        this.data = data;
    }

    public void setCodeMsg(int code, String msg) {
        this.status = code;
        this.msg = msg;
    }

    public void setCodeMsgData(int code, String msg, Object data) {
        this.status = code;
        this.msg = msg;
        this.data = data;
    }

    public void setCodeMsgErrorInfo(int code, String msg, String errorInfo) {
        this.status = code;
        this.msg = msg;
        this.errorInfo = errorInfo;
    }

    public void setCodeMsgErrorInfoData(int code, String msg, String errorInfo, Object data) {
        this.status = code;
        this.msg = msg;
        this.errorInfo = errorInfo;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
