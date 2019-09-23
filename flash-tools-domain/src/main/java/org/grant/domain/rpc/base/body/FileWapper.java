package org.grant.domain.rpc.base.body;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URL;

/**
 * grant
 * 23/9/2019 11:07 AM
 * 描述：文件传输
 */
public class FileWapper implements Serializable{
    private static final long serialVersionUID = -2492978876051867820L;
    //文件名
    private String fileName;
    //扩展字段
    private String fileExtension;
    private byte[] datas = null;

    public FileWapper(File file) throws IOException {
        datas = IOUtils.toByteArray(file.toURL());
        fileName = file.getName();
        fileExtension(fileName);
    }

    public FileWapper(URL url) throws IOException {
        datas = IOUtils.toByteArray(url);
        fileName = url.getFile();
        fileExtension(fileName);
    }

    private void fileExtension(String fileName){
        int index = fileName.lastIndexOf(".");
        if (index != -1){
            fileExtension = fileName.substring(index);
        }
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public ByteArrayInputStream getInputStream(){
        ByteArrayInputStream bin = new ByteArrayInputStream(datas);
        return bin;
    }
}
