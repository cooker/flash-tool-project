package nio;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.grant.utils.FileUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * grant
 * 19/9/2019 9:51 AM
 * 描述：
 */
public class FileIOTest {

    @Test
    public void sa() throws IOException {
        String file = "/Volumes/GTM/temp/no_inv_taskId.txt";
        IOUtils.readLines(new FileInputStream(file), "utf-8").stream()
        .forEach(s->{
            String xx = StringUtils.substringAfter(s, "taskId=");
            xx = StringUtils.substringBefore(xx, "} >> {");
            System.out.println(xx);
        });
    }
}
