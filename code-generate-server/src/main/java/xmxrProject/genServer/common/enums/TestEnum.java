package xmxrProject.genServer.common.enums;

/**
 * @FileName: TestEnum.java
 * @Author: 行米希尔
 * @Date: 2020/7/28 16:08
 * @Description:
 */
public enum TestEnum {
    O(1),T(2),S(3);

    public int code;

    TestEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
