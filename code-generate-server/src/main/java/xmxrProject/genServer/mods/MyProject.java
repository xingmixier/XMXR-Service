package xmxrProject.genServer.mods;

import java.util.StringJoiner;

/**
 * @FileName: MyProject.java
 * @Author: 行米希尔
 * @Date: 2020/8/25 20:50
 * @Description:
 */
public class MyProject {

    public static void main(String[] args) throws CloneNotSupportedException {
        Project p1 = new Project("1","1",1,1);
        Project p2 = (Project) p1.clone();
        p1.setS1("2").setS2("2").setI1(2).setI2(2);
        System.out.println(p1);
        System.out.println(p2);
    }
}//end

class Project implements Cloneable{

    private String s1;
    private String s2;
    private int i1;
    private int i2;

    public Project() {
    }

    public Project(String s1, String s2, int i1, int i2) {
        this.s1 = s1;
        this.s2 = s2;
        this.i1 = i1;
        this.i2 = i2;
    }

    @Override
    public Object clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public String toString() {
        return new StringJoiner(", ", Project.class.getSimpleName() + "[", "]")
                .add("s1='" + s1 + "'")
                .add("s2='" + s2 + "'")
                .add("i1=" + i1)
                .add("i2=" + i2)
                .toString();
    }

    public String getS1() {
        return s1;
    }

    public Project setS1(String s1) {
        this.s1 = s1;
        return this;
    }

    public String getS2() {
        return s2;
    }

    public Project setS2(String s2) {
        this.s2 = s2;
        return this;
    }

    public int getI1() {
        return i1;
    }

    public Project setI1(int i1) {
        this.i1 = i1;
        return this;
    }

    public int getI2() {
        return i2;
    }

    public Project setI2(int i2) {
        this.i2 = i2;
        return this;
    }
}
