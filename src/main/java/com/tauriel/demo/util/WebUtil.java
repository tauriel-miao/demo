package com.tauriel.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author cuiP
 * Created by JK on 2017/2/21.
 */
@Slf4j
public class WebUtil {

    /**
     * 没有权限
     */
    public static final int CODE_403 = 403;
    /**
     * 系统/参数错误
     */
    public static final int CODE_500 = 500;
    /**
     * 成功状态
     */
    public static final int CODE_200 = 200;

    /**
     * TODO 自定义CODE（从1001开始）
     * 提示
     */
    public static final int CODE_1001 = 1001;


    private WebUtil() {
    }

    /**
     * 判断是否为ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        boolean flag = false;
        String requestType = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equalsIgnoreCase(requestType)) {
            flag = true;
        }
        return flag;
    }


    /**
     *@description 判断字符串是否为空
     *@author lzy
     *@param
     *@return
     *@throws
     *@create 2018/7/4 14:31
     */
    public static boolean isNotEmpty(String target){
        return !(target == null || target.trim().length() == 0);
    }

    /**
     * 响应json数据
     *
     * @param response
     * @param obj
     * @param status
     */
    public static void writeJson(HttpServletResponse response, Object obj, int status) {
        try {
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(status);
            @Cleanup PrintWriter writer = response.getWriter();
            writer.print(JSONUtil.parseObj(obj));
        } catch (IOException e) {
            //log.error("响应json数据失败，失败信息:{}", e);
        }

    }

    /**
     * 响应text数据
     *
     * @param response
     * @param obj
     * @param status
     */
    public static void writeText(HttpServletResponse response, Object obj, int status) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(status);
            @Cleanup PrintWriter writer = response.getWriter();
            writer.print(JSONUtil.toJsonStr(obj));
        } catch (IOException e) {
            //log.error("响应text数据失败，失败信息:{}", e);
        }
    }

    /**
     * @return java.lang.String
     * @throws
     * @description 自定义：将返回结果封装JSON返回
     * @author whl
     * @Param [code, msg, result]
     * @create 2018/4/24 11:24
     */
    public static String writeResult(int code, String msg, T result) {
        //return JSONObject.toJSONString(new DataResult<T>(code, msg, result));
        return null;
    }

    /**
     * @return java.lang.String
     * @throws
     * @description 返回成功CODE、信息及结果JSON
     * @author whl
     * @Param [msg, result]
     * @create 2018/4/24 11:25
     */
    public static String returnOk(String msg, Object result) {
        //return JSONObject.toJSONString(new DataResult<Object>(CODE_200, msg, result));
        return null;
    }

    /**
     * @return java.lang.String
     * @throws
     * @description 返回成功信息
     * @author whl
     * @Param [msg]
     * @create 2018/5/16 10:18
     */
    public static String returnOk(String msg) {
        //return JSONObject.toJSONString(new DataResult<Object>(CODE_200, msg));
        return null;
    }

    /**
     * @return java.lang.String
     * @throws
     * @description 返回无权限CODE、信息及结果JSON
     * @author whl
     * @Param [msg, result]
     * @create 2018/4/24 11:25
     */
    public static String return403(String msg) {
        //return JSONObject.toJSONString(new DataResult<Object>(CODE_403, msg));
        return null;
    }

    /**
     * @return java.lang.String
     * @throws
     * @description 返回500CODE、信息及结果JSON
     * @author whl
     * @Param [msg, result]
     * @create 2018/4/24 11:26
     */
    public static String returnError(String msg) {
        //return JSONObject.toJSONString(new DataResult<Object>(CODE_500, msg));
        return null;
    }

    /**
     * @return java.lang.String
     * @throws
     * @description 返回1001CODE(自定义提示)、信息及结果JSON
     * @author whl
     * @Param [msg, result]
     * @create 2018/4/24 11:27
     */
    public static String returnTips(String msg) {
        //return JSONObject.toJSONString(new DataResult<Object>(CODE_1001, msg));
        return null;
    }


    /**
     * @param
     * @return
     * @throws
     * @description 获取唯一值作为数据库id主键
     * @author lzy
     * @create 2018/5/9 16:45
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * @return void
     * @throws
     * @description linux中获取文件路径有问题，建议用此种方法进行下载
     * @author whl
     * @Param [response, fileName]
     * @create 2018/5/18 0:43
     */
    public static void downloadFile(HttpServletResponse response, String fileName, String filePath) throws IOException {
        if (StringUtils.isNotBlank(fileName)) {
            InputStream stream = WebUtil.class.getClassLoader().getResourceAsStream(filePath + fileName);
            @Cleanup BufferedInputStream bis = new BufferedInputStream(stream);
            writeResponse(response, fileName, bis);
        }
    }

    /**
     * @return void
     * @throws
     * @description 向response中写入文件
     * @author whl
     * @Param [response, fileName, bis]
     * @create 2018/5/18 0:43
     */
    private static void writeResponse(HttpServletResponse response, String fileName, BufferedInputStream bis) throws IOException {
        response.setCharacterEncoding("utf-8");
        // 设置强制下载不打开application/force-download
        response.setContentType("application/octet-stream");
        //设置文件名称
        response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "utf-8"));
        OutputStream os = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int i = bis.read(buffer);
        while (i != -1) {
            os.write(buffer, 0, i);
            i = bis.read(buffer);
        }
    }

    /**
     * @return java.util.List
     * @throws
     * @description 读取excel文件，读取全部sheet,返回用sheetIndex做key的map
     * @author whl
     * @Param [inputStream, isExcel2003]
     * @create 2018/5/15 17:08
     */
    public static Map<Integer, List<List<String>>> readExcel(InputStream inputStream, boolean isExcel2003) throws IOException {
        Map<Integer, List<List<String>>> result = new TreeMap<>();

        /** 根据版本选择创建Workbook的方式 */
        @Cleanup Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(inputStream);
        } else {
            wb = new XSSFWorkbook(inputStream);
        }
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            result.put(i, read(wb, i));
        }
        return result;
    }

    /**
     * @return java.util.List
     * @throws
     * @description 读取excel文件，读取指定sheet
     * @author whl
     * @Param [inputStream, isExcel2003, sheetIndex]
     * @create 2018/5/18 11:25
     */
    public static List<List<String>> readExcel(InputStream inputStream, boolean isExcel2003, int sheetIndex) throws IOException {
        /** 根据版本选择创建Workbook的方式 */
        @Cleanup Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(inputStream);
        } else {
            wb = new XSSFWorkbook(inputStream);
        }
        if (sheetIndex < wb.getNumberOfSheets()) {
            return read(wb, sheetIndex);
        } else {
            return null;
        }
    }

    /**
     * @return java.util.List
     * @throws
     * @description 读取workbook
     * @author whl
     * @Param [wb]
     * @create 2018/5/15 17:09
     */
    private static List<List<String>> read(Workbook wb, int sheetIndex) {
        List<List<String>> dataLst = new ArrayList<List<String>>();

        /** 根据sheetIndex确定读取哪个工作表*/
        Sheet sheet = wb.getSheetAt(sheetIndex);
        /** 得到Excel的行数 */
        int totalRows = sheet.getLastRowNum();
        int totalCells = 0;
        /** 得到Excel的列数 */
        if (totalRows >= 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getLastCellNum();
        }
        /** 循环Excel的行 */

        for (int r = 0; r <= totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            List<String> rowLst = new ArrayList<String>();
            /** 循环Excel的列 */
            for (int c = 0; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (null != cell) {
                    // 以下是判断数据的类型
                    switch (cell.getCellTypeEnum()) {
                        // 数字
                        case NUMERIC:
                            cellValue = cell.getNumericCellValue() + "";
                            break;
                        // 字符串
                        case STRING:
                            cellValue = cell.getStringCellValue();
                            break;
                        // Boolean
                        case BOOLEAN:
                            cellValue = cell.getBooleanCellValue() + "";
                            break;
                        // 公式
                        case FORMULA:
                            cellValue = cell.getCellFormula() + "";
                            break;
                        // 空值
                        case BLANK:
                            cellValue = "";
                            break;
                        // 故障
                        case ERROR:
                            cellValue = "非法字符";
                            break;
                        default:
                            cellValue = "未知类型";
                            break;
                    }
                }
                rowLst.add(cellValue);
            }
            /** 保存第r行的第c列 */
            dataLst.add(rowLst);
        }
        return dataLst;
    }

}
