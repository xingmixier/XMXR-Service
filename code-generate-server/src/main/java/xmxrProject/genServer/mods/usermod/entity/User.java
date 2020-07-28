package xmxrProject.genServer.mods.usermod.entity;

import xmxrProject.genServer.common.baseMod.BaseEntity;
import xmxrProject.genServer.common.utils.C_Excel;

/**
 * @Aether 行米希尔
 * @Date 2020/7/11 16:43
 */
public class User extends BaseEntity<User> {

    @C_Excel(title = "",isDate = false)
    private String id;
    private String name;
    private String pwd;

    public User() {
    }

    public User(String id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
