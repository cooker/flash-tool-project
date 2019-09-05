package org.grant;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.IOException;

/**
 * grant
 * 4/9/2019 10:30 AM
 * 描述：
 */
public class OcrUtilsTest {
    public static void main(String[] args) throws IOException, TesseractException {
//        RandomAccessBuffer accessBuffer = new RandomAccessBuffer(
//                new FileInputStream("/Users/grant/Pictures/03302180011148806706.pdf")
//        );
//        PDFParser pdfParser = new PDFParser(accessBuffer);
//        pdfParser.parse();
//        PDDocument pdDocument = pdfParser.getPDDocument();
//        PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
//        BufferedImage image = pdfRenderer.renderImageWithDPI(0, 300);
//        ImageIOUtil.writeImage(image, "/Users/grant/Pictures/12.png", 300);
        String file = "/Users/grant/Pictures/005.jpg";
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("/Volumes/GTM/ocr");
        tesseract.setLanguage("chi_sim");
        String txt = tesseract.doOCR(new File(file));
        System.out.println(txt);

    }
}
