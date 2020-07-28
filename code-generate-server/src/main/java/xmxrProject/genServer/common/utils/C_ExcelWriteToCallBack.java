package xmxrProject.genServer.common.utils;

public interface C_ExcelWriteToCallBack<O> {

    String getValue(String title, O o);

    String getPKValue(String PKTitle, O o);
}
