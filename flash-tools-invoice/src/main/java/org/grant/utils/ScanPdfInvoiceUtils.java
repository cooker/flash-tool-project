package org.grant.utils;

import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.grant.utils.invoice.InvoiceInfo;
import org.grant.utils.invoice.InvoiceType;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * grant
 * 2/9/2019 11:17 AM
 * 描述：
 */
public class ScanPdfInvoiceUtils {


    public InvoiceInfo ocrInvoice(String filePath) throws IOException {
        InvoiceInfo info = new InvoiceInfo();
        File file = Paths.get(filePath).toFile();
        InputStream in = new FileInputStream(file);
        RandomAccessRead rbuffer = new RandomAccessBuffer(in);
        PDFParser parser = new PDFParser(rbuffer);
        parser.parse();

        PDDocument document = parser.getPDDocument();
        PDPageTree pageTree = document.getPages();
        Iterator<PDPage> it = pageTree.iterator();
        while (it.hasNext()) {
            PDPage pdPage = (PDPage) it.next();
            PDResources pdResources = pdPage.getResources();
            Iterable<COSName> iterable = pdResources.getXObjectNames();
            if (iterable != null) {
                Iterator<COSName> iter = iterable.iterator();
//				while (iter.hasNext()) {
                COSName cosName = iter.next();
                System.out.println(">>>>>>>>>>>" + cosName.getName());
//				}
            }
        }


        int pages = document.getNumberOfPages();

        PDFTextStripper stripper = new PDFTextStripper();
        // 设置按序输出
        stripper.setSortByPosition(true);
        stripper.setStartPage(1);
        stripper.setEndPage(pages);

        String content = stripper.getText(document);
        System.out.println(content);

        document.close();
        rbuffer.close();
        in.close();

        return info;
    }

    public InvoiceInfo ocrInoivceArea(String filePath) throws IOException {
        InvoiceInfo info = new InvoiceInfo();
        File file = Paths.get(filePath).toFile();
        InputStream in = new FileInputStream(file);
        RandomAccessRead rbuffer = new RandomAccessBuffer(in);
        PDFParser parser = new PDFParser(rbuffer);
        parser.parse();

        PDDocument document = parser.getPDDocument();

        PDPage pdPage = document.getPage(0);

        PDFTextStripperByArea area = new PDFTextStripperByArea();
        area.setSortByPosition(true);
        area.addRegion("invoiceType", new Rectangle2D.Double(190,0,210,90));
        area.addRegion("invoiceRightT", new Rectangle2D.Double(417,0,171,90));

        area.addRegion("secretArea", new Rectangle2D.Double(355,59,354,84));

        area.addRegion("hsje", new Rectangle2D.Double(471,280,105,17));
        area.addRegion("bhsje", new Rectangle2D.Double(381,253,88,17));
        area.addRegion("se", new Rectangle2D.Double(500,253,88,17));

        area.extractRegions(pdPage);

        area.getRegions().stream().forEach((name)->{
            String temp = area.getTextForRegion(name);
            switch (name){
                case "invoiceType":
                    if (temp.contains(InvoiceType.PLAIN_INVOICE.getName()) || temp.contains("普")){
                        info.setInvoiceType(InvoiceType.PLAIN_INVOICE.getType());
                    }
                    break;
                case "hsje":
                    if (null != temp && !temp.isEmpty()){
                        info.setHsje(temp.replaceAll("￥", "").trim());
                    }
                    break;
                case "bhsje":
                    if (null != temp && !temp.isEmpty()){
                        info.setBhsje(temp.replaceAll("￥", "").trim());
                    }
                    break;
                case "se":
                    if (null != temp && !temp.isEmpty()){
                        info.setSe(temp.replaceAll("￥", "").trim());
                    }
                    break;
                case "secretArea":
                    info.setSecretArea(temp);
                    break;
                case "invoiceRightT":
                    Object[] sp = Arrays.stream(temp.split("\n"))
                            .map(s-> {
                                String xx = subString(s);
                                xx = StringUtils.replaceAll(xx, "\\s*", "");
                                return StringUtils.replaceAll(xx, "[^\\x00-\\xff]", "");
                            }).toArray();
                    info.setInvoiceCode(Objects.toString(sp[0]));
                    info.setInvoiceNo(Objects.toString(sp[1]));
                    info.setKprq(Objects.toString(sp[2]));
                    info.setCheckCode(Objects.toString(sp[3]));
                    break;
                default: break;
            }
        });

        document.close();
        rbuffer.close();
        in.close();

        saveAsPng(filePath, info);
        return info;
    }

    public static void saveAsPng(String filePath, InvoiceInfo info) throws IOException {
        File file = Paths.get(filePath).toFile();
        InputStream in = new FileInputStream(file);
        RandomAccessRead rbuffer = new RandomAccessBuffer(in);
        PDFParser parser = new PDFParser(rbuffer);
        parser.parse();

        PDDocument document = parser.getPDDocument();

        PDFRenderer pdfRenderer = new PDFRenderer(document);
        BufferedImage img = pdfRenderer.renderImageWithDPI(0, 300f);

        //备注
        BufferedImage remarkImg = img.getSubimage(1517,1221, 954, 233);
        byte[] remkarB = OcrUtils.imageToBytes(remarkImg);
        info.setRemark(
               OcrUtils.ocrImg(remkarB)
        );

        //收款人、复核人、开票人
        BufferedImage threeImg = img.getSubimage(83,1458, 705, 183);
        byte[] threeB = OcrUtils.imageToBytes(threeImg);
        String threeS = OcrUtils.ocrImg(threeB);
        threeS = StringUtils.replaceAll(threeS, "\\s*", "");
        threeS = subString(threeS);
        info.setSkr(threeS);

        threeImg = img.getSubimage(790,1458, 509, 183);
        threeB = OcrUtils.imageToBytes(threeImg);
        threeS = OcrUtils.ocrImg(threeB);
        threeS = StringUtils.replaceAll(threeS, "\\s*", "");
        threeS = subString(threeS);
        info.setFhr(threeS);

        threeImg = img.getSubimage(1300,1458, 499, 183);
        threeB = OcrUtils.imageToBytes(threeImg);
        threeS = OcrUtils.ocrImg(threeB);
        threeS = StringUtils.replaceAll(threeS, "\\s*", "");
        threeS = subString(threeS);
        info.setKpr(threeS);


        //销方信息
        BufferedImage sellerImg;
        byte[] sellerB;
        String sellerS;

        sellerImg = img.getSubimage(183,1214, 1260, 60);
        sellerB = OcrUtils.imageToBytes(sellerImg);
        sellerS = OcrUtils.ocrImg(sellerB);
        sellerS = StringUtils.replaceAll(sellerS, "\\s*", "");
        sellerS = subString(sellerS);
        info.setSellerName(sellerS);

        sellerImg = img.getSubimage(183,1271, 1260, 60);
        sellerB = OcrUtils.imageToBytes(sellerImg);
        sellerS = OcrUtils.ocrImg(sellerB);
        sellerS = StringUtils.replaceAll(sellerS, "\\s*", "");
        sellerS = subString(sellerS);
        info.setSellerTaxNo(sellerS);


        sellerImg = img.getSubimage(183,1335, 1260, 60);
        sellerB = OcrUtils.imageToBytes(sellerImg);
        sellerS = OcrUtils.ocrImg(sellerB);
        sellerS = StringUtils.replaceAll(sellerS, "\\s*", "");
        sellerS = subString(sellerS);
        info.setSellerAddrAndTel(sellerS);


        sellerImg = img.getSubimage(183,1393, 1260, 60);
        sellerB = OcrUtils.imageToBytes(sellerImg);
        sellerS = OcrUtils.ocrImg(sellerB);
        sellerS = StringUtils.replaceAll(sellerS, "\\s*", "");
        sellerS = subString(sellerS);
        info.setSellerBankAndNo(sellerS);


        //购方信息

        sellerImg = img.getSubimage(172,348, 1270, 69);
        sellerB = OcrUtils.imageToBytes(sellerImg);
        sellerS = OcrUtils.ocrImg(sellerB);
        sellerS = StringUtils.replaceAll(sellerS, "\\s*", "");
        sellerS = subString(sellerS);
        info.setPurchaserName(sellerS);

        sellerImg = img.getSubimage(172,417, 1270, 69);
        sellerB = OcrUtils.imageToBytes(sellerImg);
        sellerS = OcrUtils.ocrImg(sellerB);
        sellerS = StringUtils.replaceAll(sellerS, "\\s*", "");
        sellerS = subString(sellerS);
        info.setPurchaserTaxNo(sellerS);

        sellerImg = img.getSubimage(172,486, 1270, 69);
        sellerB = OcrUtils.imageToBytes(sellerImg);
        sellerS = OcrUtils.ocrImg(sellerB);
        sellerS = StringUtils.replaceAll(sellerS, "\\s*", "");
        sellerS = subString(sellerS);
        info.setPurchaserAddrAndTel(sellerS);

        sellerImg = img.getSubimage(172,555, 1270, 69);
        sellerB = OcrUtils.imageToBytes(sellerImg);
        sellerS = OcrUtils.ocrImg(sellerB);
        sellerS = StringUtils.replaceAll(sellerS, "\\s*", "");
        sellerS = subString(sellerS);
        info.setPurchaserBankAndNo(sellerS);

        document.close();
        rbuffer.close();
        in.close();

    }


    private static String subString(String str){
        if (str.contains(":")){
            str = StringUtils.substringAfterLast(str, ":");
        }else if(str.contains("：")){
            str = StringUtils.substringAfterLast(str, "：");
        }

        return str;

    }


    /**
     * 长：系数0.2345
     * 宽：系数0.2388
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ScanPdfInvoiceUtils pdfInvoiceUtils = new ScanPdfInvoiceUtils();
        InvoiceInfo info = pdfInvoiceUtils.ocrInoivceArea("/Users/grant/Pictures/03300180011130339349.pdf");
        System.out.println(JSONUtil.toJsonStr(info));

//        pdfInvoiceUtils.saveAsPng("/Users/grant/Pictures/03300180011130339349.pdf");
    }
}
