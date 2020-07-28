package xmxrProject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xmxrProject.genServer.common.task.GlobalDirectivesTask;

@SpringBootApplication
@MapperScan
public class CodeGenerateApplication {



    public static void main(String[] args) {
        GlobalDirectivesTask.BEANS = SpringApplication.run(CodeGenerateApplication.class, args);
    }

}
