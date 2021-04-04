package com.coder23000;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.base.JRBaseTextField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
//            String filePath = "C:\\Reports\\FirstReport.jrxml";
//            ClassLoader classLoader = getClass().getClassLoader();

            // Get the report location
            ClassLoader classLoader = App.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("FirstReport.jrxml");

            // Setting parameters
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("studentName","hello");


            Student student1 = new Student(1l,"Raouf","I5z","Kouba","Annaba");
            Student student2 = new Student(2l,"John","Smith","Elm Street","NewYork");

            List<Student> list = new ArrayList<Student>();
            list.add(student1);
            list.add(student2);

            // Creating datasource from list
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

            // Create report from file
            JasperReport report = JasperCompileManager.compileReport(inputStream);

            // Accessing a field by key and change his properties from code
            JRBaseTextField textField = (JRBaseTextField) report.getTitle().getElementByKey("param");
            textField.setForecolor(Color.GREEN);

            // Create JasperPrint
            JasperPrint print = JasperFillManager.fillReport(report,parameters,dataSource);

            // Export report to PDF file
            JasperExportManager.exportReportToPdfFile(print,"C:\\Temp\\test.pdf");

            // Export report to HTML file
            JasperExportManager.exportReportToHtmlFile(print,"C:\\Temp\\test.html");

            // Export report to Excel file
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(print));
            exporter.setExporterOutput(
                    new SimpleOutputStreamExporterOutput(new FileOutputStream(new File(
                            "C:\\Temp\\test.xlsx"
                    ))));
            exporter.exportReport();

            System.out.println("report created successfully");
        } catch (Exception e) {
            System.out.println("Exception while creating report");
        }
    }
}
