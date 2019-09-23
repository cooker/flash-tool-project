package org.grant;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * grant
 * 16/9/2019 2:21 PM
 * 描述：
 */
@Component
public class FirstRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
       try {
           mk();
       }catch (Exception e){
           e.printStackTrace();
       }
    }


    public void mk() throws IOException {
        String logPath = "/ocr/xforc/app2/yili-LOG/test.log";
        System.out.println("日志文件输出路径："  + logPath);
        File file = Paths.get(logPath).toFile();
        System.out.println("文件是否存在：" + file.exists());
        if (!file.exists()){
            System.out.println("文件不存在：创建文件");
            file.createNewFile();
        }

        System.out.println("文件是否可写：" + file.canWrite());
        FileWriter fout = new FileWriter(file);

        System.out.println("输出日志内容：12345678");
        fout.write("12345678");
        fout.flush();
        fout.close();
    }
}
