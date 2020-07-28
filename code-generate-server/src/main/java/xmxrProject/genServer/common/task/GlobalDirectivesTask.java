package xmxrProject.genServer.common.task;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.SpringProperties;

import java.lang.reflect.Field;
import java.util.Scanner;

/**
 * @FileName: GlobalDirectivesTask.java
 * @Author: 行米希尔
 * @Date: 2020/7/27 9:35
 * @Description: 全局指令 用于在运行中查看 对象中的属性注入
 */
public class GlobalDirectivesTask implements Runnable {

    private static final String PREFIX = "xmxrProject.genServer.mods.";

    public static ConfigurableApplicationContext BEANS;

    private static String[] ARGS = new String[33];

    /**
     * text - example
     * usermod.entity.TestUser
     */

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                String params = sc.nextLine();
                setARGS(params);
                switch (ARGS[0]) {
                    case "show":
                        show();
                        break;
//                    case "change":          change();
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void show() throws Exception {
        switch (ARGS[1]) {
            case "-a":
                show_a();
                break;
            case "-c":
                show_c();
                break;
            case "-f":
                show_f();
                break;
            case "-p":
                show_p();
        }
    }

    private void show_p() throws Exception {
        System.out.println(SpringProperties.getProperty(ARGS[2]));
    }

    private void show_f() throws Exception {
        Object obj = BEANS.getBean(ARGS[2]);
        Field field = obj.getClass().getDeclaredField(ARGS[3]);
        field.setAccessible(true);
        Object value = field.get(obj);
        System.out.println(value);
    }

    private void show_c() {
        System.out.println(BEANS.getBean(ARGS[2]));
    }

    private void show_a() {
        for (String name : BEANS.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }


    private void setARGS(String params) {
        int i = 0;
        for (String arg : params.split(" ")) {
            if (arg.trim().isEmpty()) {
                continue;
            }
            ARGS[i++] = arg;
        }
    }

}
