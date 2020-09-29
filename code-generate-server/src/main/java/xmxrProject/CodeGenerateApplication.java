package xmxrProject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xmxrProject.genServer.$XMXR;
import xmxrProject.genServer.common.task.GlobalDirectivesTask;

@SpringBootApplication
@MapperScan
public class CodeGenerateApplication implements $XMXR {



    public static void main(String[] args) throws ClassNotFoundException {
        GlobalDirectivesTask.BEANS = SpringApplication.run(CodeGenerateApplication.class, args);




    }

}
