package com.leon.test;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @PROJECT_NAME: hello-jasper-report
 * @CLASS_NAME: HelloJasperReportTest
 * @AUTHOR: OceanLeonAI
 * @CREATED_DATE: 2022/10/11 0011 21:35
 * @Version 1.0
 * @DESCRIPTION: HelloJasperReportTest
 **/
public class HelloJasperReportTest {

    public static void main(String[] args) throws Exception {

        // String templatePathJrxml = "D:\\DevelopWorkspace\\WorkspaceForGit\\hello-jasper-report\\src\\main\\resources\\templates\\hello-jasper-report.jrxml";
        String templatePathJrxml = HelloJasperReportTest.class.getClassLoader().getResource("").getPath() + "templates/hello-jasper-report.jrxml";

        // String templatePathJasper = "D:\\DevelopWorkspace\\WorkspaceForGit\\hello-jasper-report\\src\\main\\resources\\templates\\hello-jasper-report.jasper";
        String templatePathJasper = HelloJasperReportTest.class.getClassLoader().getResource("").getPath() + "templates/hello-jasper-report.jasper";

        // String outputPath = "D:\\DevelopWorkspace\\WorkspaceForGit\\hello-jasper-report\\src\\main\\resources\\output\\test.pdf";
        String outputPath = HelloJasperReportTest.class.getClassLoader().getResource("").getPath() + "/export/";


        // 找到模板
        // 模板位置 jrxml
        String jrxmlPath = templatePathJrxml;

        // 编译后模板位置jasper
        String jasperPath = templatePathJasper;

        // 编译文件 JasperCompileManager.compileReportToFile(jrxmlPath,jasperPath);

        JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);

        //定义map类型数据 用来给parameters组件传递数据
        Map<String, Object> map = getMockData();

        //填充数据 是用javaBean数据源传递数据 需要将list放入 JRBeanCollectionDataSource中
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> m1 = new HashMap<>();
        m1.put("Field_1", "字段1");
        m1.put("Field_2", "字段2");
        Map<String, Object> m2 = new HashMap<>();
        m2.put("Field_1", "字段3");
        m2.put("Field_2", "字段4");
        list.add(m1);
        list.add(m2);

        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list);

        // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath, map, new JREmptyDataSource()); // 只包含 Map 对象

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath, map, jrBeanCollectionDataSource);

        String fileOutputPath = generateFileName(outputPath);
        // 生成PDf文件
        JasperExportManager.exportReportToPdfFile(jasperPrint, fileOutputPath);

    }

    private static Map<String, Object> getMockData() {

        Map<String, Object> map = new HashMap<>();
        map.put("reportName", "报表名字");
        map.put("$P{reportName}", "报表名字-表达式");
        map.put("Field_1", "字段1");
        map.put("Field_2", "字段2");

        return map;
    }

    /**
     * 生成文件路径
     *
     * @param outputPath
     * @return
     */
    public static String generateFileName(String outputPath) {
        String uuid = UUID.randomUUID().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(new Date());
        String fileName = File.separator + format + "---" + uuid + ".pdf";
        String pdfExportFilePath = outputPath + fileName;
        File file = new File(outputPath); // 建文件夹
        if (!file.exists()) {
            file.mkdirs();
        }
        return pdfExportFilePath;
    }


}
