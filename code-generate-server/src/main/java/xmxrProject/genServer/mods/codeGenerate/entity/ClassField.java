package xmxrProject.genServer.mods.codeGenerate.entity;

import xmxrProject.genServer.common.baseMod.BaseEntity;

/**
 * @Aether 行米希尔
 * @Date 2020/7/11 18:38
 */
public class ClassField extends BaseEntity<ClassField> {

    private static final long serialVersionUID = 3333L;

    private String annotations; // 属性注解
    private String state; //属性状态
    private String type; //属性类型
    private String name;  //属性名
    private String comment;  //属性注释
    private String upName; //首字母大写 属性名
    private String sqlName; //sql 字段名
    private String judgeSymbol; //判断符号 sql 判断
    // =
    // = ''
    // <> ''
    // <
    // >
    // <=
    // >=
    // <> ,
    // LIKE         不支持
    // LIKE '%x'    不支持
    // LIKE 'x%'    不支持
    // LIKE '%%'
    // NOT LIKE
    // NOT LIKE '%x'    不支持
    // NOT LIKE 'x%'    不支持
    // NOT LIKE '%%'
    // BETWEEN
    // NOT BETWEEN
    // IN
    // NOT IN
    // IS NULL
    // IS NOT NULL

    public ClassField() {
    }

    public ClassField(String anNOTations, String state, String type, String name, String comment, String upName, String sqlName, String judgeSymbol) {
        this.annotations = anNOTations;
        this.state = state;
        this.type = type;
        this.name = name;
        this.comment = comment;
        this.upName = upName;
        this.sqlName = sqlName;
        this.judgeSymbol = judgeSymbol;
    }


    public String getAnnotations() {
        return annotations;
    }

    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUpName() {
        return upName;
    }

    public void setUpName(String upName) {
        this.upName = upName;
    }

    public String getSqlName() {
        return sqlName;
    }

    public void setSqlName(String sqlName) {
        this.sqlName = sqlName;
    }

    public String getJudgeSymbol() {
        return judgeSymbol;
    }

    public void setJudgeSymbol(String judgeSymbol) {
        this.judgeSymbol = judgeSymbol;
    }
}
