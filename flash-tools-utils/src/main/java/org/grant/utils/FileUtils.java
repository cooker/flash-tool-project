package org.grant.utils;


import org.grant.utils.annotation.Describe;

import java.io.File;

/**
 * grant
 * 17/9/2019 3:20 PM
 * 描述：
 */
public class FileUtils {


    @Describe("文件重命名")
    public static boolean rename(@Describe("文件实例") File file, @Describe("新文件绝对路径") String newName){
        if (file == null || !file.exists()) {
            return false;
        }
        if (!newName.contains("/")) {
            newName = file.getParent() + "/" + newName;
        }
        return file.renameTo(new File(newName));
    }

    /**
     * Makes file path if it doesn't exist
     **/
    public static boolean makeFilePath(File file) {
        String parent = file.getParent();
        if (parent != null) {
            File dir = new File(parent);
            try {
                if (!dir.exists() && !dir.mkdirs()) {
                    throw new SecurityException("File.mkdirs() returns false");
                }
            } catch (SecurityException e) {
                return false;
            }
        }

        return true;
    }
}
