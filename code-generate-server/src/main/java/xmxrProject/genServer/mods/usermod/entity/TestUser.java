package xmxrProject.genServer.mods.usermod.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xmxrProject.genServer.common.baseMod.BaseEntity;

/**
 * @Aether 行米希尔
 * @Date 2020年07月11日  21时31分06秒
 */
@Component
public class TestUser extends BaseEntity<User> {




@Value("123")
    private String names;         //用户名
@Value("321")
    private String pwd;         //密码

    public TestUser() {
    }

    public TestUser(String name, String pwd) {
        this.names = name;
        this.pwd = pwd;
    }

    public String getName() {
        return names;
    }

    public void setName(String name) {
        this.names = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "TestUser{" +
                "name='" + names + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}