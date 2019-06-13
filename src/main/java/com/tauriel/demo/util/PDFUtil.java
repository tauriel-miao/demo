package com.tauriel.demo.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.Cleanup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class PDFUtil {

    public static byte[] generatePDF(Map<String, String> dataMap) {
        //iText库中最常用的类，它代表了一个pdf实例
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] bytes = null;
        try {
            PdfWriter writer = PdfWriter.getInstance(document, bos);
            //不打开只能添加Meta信息
            document.open();
            //加载字体，默认字体无法显示中文  字体可以下载或者从C:\Windows\Fonts中选取
            BaseFont title = BaseFont.createFont("/simhei.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            BaseFont base = BaseFont.createFont("/lantin.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //自定义颜色
//            BaseColor baseColor = new BaseColor(147, 158, 171);
            BaseColor twoLevelTitleColor = new BaseColor(107, 117, 129);

            //一级标题
            Font firstLevelTiltleFont = new Font(title, 16, BaseFont.FONT_TYPE_T1, BaseColor.DARK_GRAY);
            //二级标题
            Font twoLevelTitleFont = new Font(title, 14, BaseFont.FONT_TYPE_T1, twoLevelTitleColor);
            //基本字体
            Font baseFont = new Font(base, 12);
            //链接的字体样式（蓝色，下划线）
            Font linkFont = linkFont = FontFactory.getFont(BaseFont.IDENTITY_H, 12, Font.UNDERLINE, BaseColor.BLUE);
            // Font linkFont = new Font(lable, 16, BaseFont.FONT_WEIGHT, BaseColor.BLUE);

            /**
             * 一、获取token
             */
            //建立锚点
            Paragraph tokenPara = new Paragraph();
            Anchor tokenAnchor = new Anchor("1.获取token", firstLevelTiltleFont);
            // 设置锚点的名字
            tokenAnchor.setName("token");
            tokenPara.add(tokenAnchor);
            document.add(tokenPara);
            document.add(new Paragraph("请求地址：", twoLevelTitleFont));
            document.add(new Paragraph("http://ip:port/oauth/token", baseFont));
            document.add(new Paragraph("请求类型：", twoLevelTitleFont));
            document.add(new Paragraph("POST", baseFont));
            document.add(new Paragraph("请求参数：", twoLevelTitleFont));
            document.add(new Paragraph("{", baseFont));
            document.add(new Paragraph("\"grant_type\":\"authorization_code\",\n", baseFont));
            document.add(new Paragraph("\"client_id\":\"用户名\",\n", baseFont));
            document.add(new Paragraph("\"client_secret\":\"密码\"\n", baseFont));
            document.add(new Paragraph("}", baseFont));
            document.add(new Paragraph("响应参数：", twoLevelTitleFont));
            document.add(new Paragraph("{", baseFont));
            document.add(new Paragraph("\"token\":\"秘钥\",\n", baseFont));
            document.add(new Paragraph("}", baseFont));
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            /**
             * 二、单条进件
             */
            document.add(new Paragraph("2.单条进件", firstLevelTiltleFont));
            //表示一个缩进的文本段落，在段落中，你可以设置对齐方式，缩进，段落前后间隔等
            document.add(new Paragraph("请求地址：", twoLevelTitleFont));
            //document.add(new Paragraph(dataMap.get(Constant.PDF_URL), baseFont));
            document.add(new Paragraph("请求类型：", twoLevelTitleFont));
            document.add(new Paragraph("POST", baseFont));
            document.add(new Paragraph("请求参数：", twoLevelTitleFont));
            document.add(new Paragraph("{", baseFont));
            document.add(new Paragraph("\"productCode\":\"业务条线编号\",\n", baseFont));
            document.add(new Paragraph("\"requestParam\":", baseFont));
            //document.add(new Paragraph(dataMap.get(Constant.PDF_REQUEST), baseFont));
            document.add(new Chunk("\"token\":", baseFont));
            //链接到刚刚设置的锚点
            Anchor linkTokenAnchor = new Anchor(new Chunk("${chick here to get token}", linkFont));
            // 取到锚点
            linkTokenAnchor.setReference("#token");
            document.add(linkTokenAnchor);
            document.add(new Paragraph("}", baseFont));
            document.add(new Paragraph("响应参数：", twoLevelTitleFont));
            //document.add(new Paragraph(dataMap.get(Constant.PDF_RESPONSE), baseFont));

            /**
             * 三、异步进件
             */
            document.add(new Paragraph("3.异步进件", firstLevelTiltleFont));
            document.add(new Paragraph("请求地址：", twoLevelTitleFont));
            document.add(new Paragraph("http://ip:port/decision/async/send", baseFont));
            document.add(new Paragraph("请求类型：", twoLevelTitleFont));
            document.add(new Paragraph("POST", baseFont));
            document.add(new Paragraph("请求参数：", twoLevelTitleFont));
            document.add(new Paragraph("{", baseFont));
            document.add(new Paragraph("\"productCode\":\"业务条线编号\",\n", baseFont));
            document.add(new Paragraph("\"requestParam\":", baseFont));
            //document.add(new Paragraph(dataMap.get(Constant.PDF_REQUEST)+",", baseFont));
            document.add(new Chunk("\"token\":", baseFont));
            //链接到刚刚设置的锚点
            Anchor linkTokenAnchor1 = new Anchor(new Chunk("${chick here to get token}", linkFont));
            // 取到锚点
            linkTokenAnchor.setReference("#token");
            document.add(linkTokenAnchor1);
            document.add(new Chunk("\n\"serialNumber\":\"请求流水号\",\n", baseFont));
            document.add(new Paragraph("}", baseFont));

            document.add(new Paragraph("响应参数：", twoLevelTitleFont));
            document.add(new Paragraph("{", baseFont));
            document.add(new Paragraph("\"stateCode\":\"00000\",\n", baseFont));
            document.add(new Paragraph("\"stateDesc\":\"响应成功\",\n", baseFont));
            document.add(new Paragraph("}", baseFont));
            /**
             * 四、获取异步结果
             */
            document.add(new Paragraph("4.获取异步结果", firstLevelTiltleFont));
            document.add(new Paragraph("请求地址：", twoLevelTitleFont));
            document.add(new Paragraph("http://ip:port/decision/async/getDecisionResult?serialNumber=流水号", baseFont));
            document.add(new Paragraph("响应参数：", twoLevelTitleFont));
            document.add(new Paragraph("{", baseFont));
            document.add(new Paragraph("\n\"msg\": \"处理成功!\"",baseFont));
            document.add(new Paragraph("\n\"code\": \"2\"",baseFont));
            document.add(new Paragraph("\n\"serialNumber\": \"1231232\"",baseFont));
            //document.add(new Paragraph(dataMap.get(Constant.PDF_RESPONSE), baseFont));
            document.add(new Paragraph("\n", baseFont));
            document.add(new Paragraph("}", baseFont));
            document.close();
            //添加水印
            waterMark(bos);
            bytes = bos.toByteArray();
            bos.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //不关闭则会出现异常
        document.close();
        return bytes;
    }


    /**
     * 功能描述: pdf添加水印文字
     * 创建时间: 2018/7/4 14:41
     *
     * @param bos
     * @return void
     * @throws
     * @author whl
     */
    public static void waterMark(ByteArrayOutputStream bos) {
        try {
            @Cleanup PdfReader reader = new PdfReader(bos.toByteArray());
            @Cleanup PdfStamper stamper = new PdfStamper(reader, bos);
            //这里的字体设置比较关键，这个设置是支持中文的写法
            BaseFont base = BaseFont.createFont("/simhei.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            int total = reader.getNumberOfPages() + 1;

            PdfContentByte under;
            Rectangle pageRect = null;
            for (int i = 1; i < total; i++) {
                pageRect = stamper.getReader().getPageSizeWithRotation(i);
                // 获得PDF下层
                under = stamper.getUnderContent(i);
                under.saveState();

                // 插入图片水印
                InputStream logoIns = PDFUtil.class.getClassLoader().getResourceAsStream("logo.png");
                ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
                byte[] buff = new byte[1024];
                int rc = 0;
                while ((rc = logoIns.read(buff, 0, 1024)) > 0) {
                    swapStream.write(buff, 0, rc);
                }
                Image img = Image.getInstance(swapStream.toByteArray());
                img.setAbsolutePosition(0, pageRect.getHeight() - 50);
                under.addImage(img);

                // 添加水印文字
                under.beginText();
                under.setFontAndSize(base, 10);
                under.setColorFill(BaseColor.LIGHT_GRAY);
                // 水印文字成45度角倾斜
                under.showTextAligned(Element.ALIGN_CENTER, "百融金服", pageRect.getWidth() - 40, 40, 45);
                under.endText();
                under.setLineWidth(1f);
                under.stroke();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
