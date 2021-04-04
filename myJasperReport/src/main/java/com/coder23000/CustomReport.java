package com.coder23000;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.base.JRBaseTextField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomReport {
    public static void main( String[] args )
    {
        try {

            Subject subject1 = new Subject("java",80);
            Subject subject2 = new Subject("MySQL",70);
            Subject subject3 = new Subject("PHP",50);
            Subject subject4 = new Subject("MongoDB",40);
            Subject subject5 = new Subject("C++",60);

            List<Subject> list = new ArrayList<Subject>();
            list.add(subject1);
            list.add(subject2);
            list.add(subject3);
            list.add(subject4);
            list.add(subject5);

//            for (int i = 0; i < 100; i++) {
//                list.add(subject1);
//            }

            // Get the report location
            ClassLoader classLoader = App.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("Student.jrxml");

            // Creating datasource from list
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

            // Setting parameters
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("studentName","Raouf");
            parameters.put("tableData",dataSource);

            // Create report from file
            JasperReport report = JasperCompileManager.compileReport(inputStream);

            // Export report to PDF file
            JasperPrint print = JasperFillManager.fillReport(report,parameters, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfFile(print,"C:\\Temp\\test2.pdf");

            System.out.println("report created successfully");
        } catch (Exception e) {
            System.out.println("Exception while creating report");
        }
    }
}
