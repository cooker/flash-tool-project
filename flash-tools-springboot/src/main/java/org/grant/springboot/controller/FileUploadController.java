package org.grant.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.grant.springboot.magic.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * grant
 * 29/8/2019 10:40 AM
 * 描述：文件上传
 */
@Slf4j
public class FileUploadController extends BaseController{

    protected String bashPath = SystemUtils.getUserHome().getPath() + "/temp/";

    @RequestMapping("/upload")
    @ResponseBody
    public R upload(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return R.builder().code(R.FAIL).message("上传失败，文件为空").build();
        }

        String fileName = RandomStringUtils.random(20, true, true) +
                getFileSuffix(file.getOriginalFilename());

        String filePath =  bashPath + fileName;
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            log.info("上传成功 >>> {} >> {}", file.getOriginalFilename(), fileName);
            return R.builder().code(R.OK).message("上传成功").body(fileName).build();
        } catch (IOException e) {
            log.error("上传失败", e);
        }
        return R.builder().code(R.FAIL).message("上传失败").build();
    }

    @RequestMapping("/multiUpload")
    @ResponseBody
    public R upload(HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        if (null == files || files.isEmpty()) {
            return R.builder().code(R.FAIL).message("上传失败，文件为空").build();
        }
        String batchNo = UUID.randomUUID().toString();
        String fileName = null, filePath = null;
        File dest = null;
        int num = 0;
        try {
            for (MultipartFile file : files){
                fileName = RandomStringUtils.random(20, true, true) +
                        getFileSuffix(file.getOriginalFilename());
                filePath =  bashPath + batchNo + "/" + fileName;
                dest = new File(filePath + fileName);
                file.transferTo(dest);
                num ++;
            }
        }catch (IOException e){
            log.error("上传失败", e);
        }

        if (num > 0){
            return R.builder().code(R.OK).message(num + "").body(bashPath + batchNo + "/").build();
        }else {
            return R.builder().code(R.FAIL).message("上传失败").build();
        }
    }


    protected String getFileSuffix(String fileName){
        String suffix = StringUtils.substringAfter(fileName, ".");
        return StringUtils.isEmpty(suffix) ? "" : "." + suffix;
    }
}
