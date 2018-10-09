package com.code.resumemanagement.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

public class FileUtil {
    private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);
    public static byte[] InputStream2ByteArray(String filePath) throws IOException {
        InputStream in = new FileInputStream(filePath);
        byte[] data = toByteArray(in);
        in.close();

        return data;
    }

    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    public static String readdocx(String filePath) throws IOException {
        File file = new File(filePath);
        String str = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument xdoc = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
            str = extractor.getText();

            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String readdocx(File file) throws IOException {
        String str = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument xdoc = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
            str = extractor.getText();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String readdoc(File file) throws IOException {
        String str = "";
        try {
            FileInputStream is = new FileInputStream(file);
            WordExtractor ex = new WordExtractor(is);
            str = ex.getText();
            is.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String readpdf(File file) throws IOException {
        String str = "";
        PDDocument document = null;
        try {
            document = PDDocument.load(file);
            // 读文本内容
            PDFTextStripper stripper = new PDFTextStripper();
            // 设置按顺序输出
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(1);
            str = stripper.getText(document);

        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return str;
    }

    /**
     * 根据http响应下载文件.
     *
     * @param response http响应
     * @param session  缓存
     * @param file     下载的文件
     * @throws IOException 读写异常
     */
    public static void downLoad(HttpServletResponse response, HttpSession session, File file)
            throws IOException {
        String fileType = session.getServletContext().getMimeType(file.getName());
        response.setContentType(fileType);
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        //------------------------------
        InputStream is = new FileInputStream(file);
        OutputStream os = response.getOutputStream();
        int len = 0;
        byte[] b0 = new byte[1024];
        while ((len = is.read(b0)) != -1) {
            os.write(b0, 0, len);
        }
        is.close();
    }

    /**
     * 将图片信息写入输出流.
     *
     * @param imgByte 图片信息
     * @param o       输出流
     * @throws IOException 读写异常
     */
    public static void downLoadLocalByByte(byte[] imgByte, OutputStream o) throws IOException {
        InputStream in = new ByteArrayInputStream(imgByte);
        byte[] b = new byte[1024];
        int nRead = 0;
        while ((nRead = in.read(b)) != -1) {
            o.write(b, 0, nRead);
        }
        o.flush();
        o.close();
        in.close();
    }


}
