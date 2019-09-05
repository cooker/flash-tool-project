package org.grant.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * tools-ocr
 * Created by 何志龙 on 2019-03-22.
 */
public class OcrUtils {

    public static String ocrImg(byte[] imgData) {
        int i = Math.abs(UUID.randomUUID().hashCode()) % 4;
        switch (i){
            case 0:
                return bdGeneralOcr(imgData);
            case 1:
                return bdAccurateOcr(imgData);
            case 2:
                return sogouMobileOcr(imgData);
            default:
                return sogouWebOcr(imgData);
        }
    }

    public static byte[] imageToBytes(BufferedImage img) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MemoryCacheImageOutputStream outputStream = new MemoryCacheImageOutputStream(byteArrayOutputStream);
        try {
            Iterator iter = ImageIO.getImageWritersByFormatName("png");
            ImageWriter writer = (ImageWriter) iter.next();
            ImageWriteParam iwp = writer.getDefaultWriteParam();
//            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//            iwp.setCompressionQuality(0.5f);
            writer.setOutput(outputStream);
            IIOImage image = new IIOImage(img, null, null);
            writer.write(null, image, iwp);
            writer.dispose();
            byte[] result = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            outputStream.close();
            return result;
        } catch (IOException e) {
            StaticLog.error(e);
            return new byte[0];
        }
    }

    private static String bdGeneralOcr(byte[] imgData){
        return bdBaseOcr(imgData, "general_location");
    }

    private static String bdAccurateOcr(byte[] imgData){
        return bdBaseOcr(imgData, "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate");
    }

    private static String bdBaseOcr(byte[] imgData, String type){
        String[] urlArr = new String[]{"http://ai.baidu.com/tech/ocr/general", "http://ai.baidu.com/index/seccode?action=show"};
        StringBuilder cookie = new StringBuilder();
        for (String url : urlArr) {
            HttpResponse cookieResp = WebUtils.get(url);
            List<String> ckList = cookieResp.headerList("Set-Cookie");
            for (String s : ckList) {
                cookie.append(s.replaceAll("expires[\\S\\s]+", ""));
            }
        }
        HashMap<String, String> header = new HashMap<>();
        header.put("Referer", "http://ai.baidu.com/tech/ocr/general");
        header.put("Cookie", cookie.toString());
        String data = "type="+URLUtil.encodeQuery(type)+"&detect_direction=false&image_url&image=" + URLUtil.encodeQuery("data:image/jpeg;base64," + Base64.encode(imgData)) + "&language_type=CHN_ENG";
        HttpResponse response = WebUtils.postRaw("http://ai.baidu.com/aidemo", data, 0, header);
        return extractBdResult(WebUtils.getSafeHtml(response));
    }

    public static String sogouMobileOcr(byte[] imgData) {
        String boundary = "------WebKitFormBoundary8orYTmcj8BHvQpVU";
        String url = "http://ocr.shouji.sogou.com/v2/ocr/json";
        String header = boundary + "\r\nContent-Disposition: form-data; name=\"pic\"; filename=\"pic.jpg\"\r\nContent-Type: image/jpeg\r\n\r\n";
        String footer = "\r\n" + boundary + "--\r\n";
        byte[] postData = mergeByte(header.getBytes(CharsetUtil.CHARSET_ISO_8859_1), imgData, footer.getBytes(CharsetUtil.CHARSET_ISO_8859_1));
        return extractSogouResult(postMultiData(url, postData, boundary.substring(2)));
    }

    public static String sogouWebOcr(byte[] imgData) {
        String url = "https://deepi.sogou.com/api/sogouService";
        String referer = "https://deepi.sogou.com/?from=picsearch&tdsourcetag=s_pctim_aiomsg";
        String imageData = Base64.encode(imgData);
        long t = new Date().getTime();
        String sign = SecureUtil.md5("sogou_ocr_just_for_deepibasicOpenOcr" + t + imageData.substring(0, Math.min(1024, imageData.length())) + "7f42cedccd1b3917c87aeb59e08b40ad");
        Map<String, Object> data = new HashMap<>();
        data.put("image", imageData);
        data.put("lang", "zh-Chs");
        data.put("pid", "sogou_ocr_just_for_deepi");
        data.put("salt", t);
        data.put("service", "basicOpenOcr");
        data.put("sign", sign);
        HttpRequest request = HttpUtil.createPost(url).timeout(15000);
        request.form(data);
        request.header("Referer", referer);
        HttpResponse response = request.execute();
        return extractSogouResult(WebUtils.getSafeHtml(response));
    }

    private static String extractSogouResult(String html) {
        if (StrUtil.isBlank(html)) {
            return "";
        }
        JSONObject jsonObject = JSONUtil.parseObj(html);
        if (jsonObject.getInt("success", 0) != 1) {
            return "";
        }
        JSONArray jsonArray = jsonObject.getJSONArray("result");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jObj = jsonArray.getJSONObject(i);
            sb.append(jObj.getStr("content").trim()).append("\n");
        }
        return sb.toString();
    }

    private static String extractBdResult(String html) {
        if (StrUtil.isBlank(html)) {
            return "";
        }
        JSONObject jsonObject = JSONUtil.parseObj(html);
        if (jsonObject.getInt("errno", 0) != 0) {
            return "";
        }
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("words_result");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jObj = jsonArray.getJSONObject(i);
            sb.append(jObj.getStr("words").trim()).append("\n");
        }
        return sb.toString();
    }

    static byte[] mergeByte(byte[]... bytes) {
        int length = 0;
        for (byte[] b : bytes) {
            length += b.length;
        }
        byte[] resultBytes = new byte[length];
        int offset = 0;
        for (byte[] arr : bytes) {
            System.arraycopy(arr, 0, resultBytes, offset, arr.length);
            offset += arr.length;
        }
        return resultBytes;
    }

    static String postMultiData(String url, byte[] data, String boundary) {
        return postMultiData(url, data, boundary, "", "");
    }

    private static String postMultiData(String url, byte[] data, String boundary, String cookie, String referer) {
        try {
            HttpRequest request = HttpUtil.createPost(url).timeout(15000);
            request.contentType("multipart/form-data; boundary=" + boundary);
            request.body(data);
            if (StrUtil.isNotBlank(referer)) {
                request.header("Referer", referer);
            }
            if (StrUtil.isNotBlank(cookie)) {
                request.cookie(cookie);
            }
            HttpResponse response = request.execute();
            return WebUtils.getSafeHtml(response);
        } catch (Exception ex) {
            StaticLog.error(ex);
            return null;
        }
    }

}
