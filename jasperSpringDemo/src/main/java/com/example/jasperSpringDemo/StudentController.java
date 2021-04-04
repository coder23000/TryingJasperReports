package com.example.jasperSpringDemo;

import com.example.pojo.Student;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.base.JRBaseTextField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @GetMapping("/report")
    public ResponseEntity<byte[]> getReport() {

        try {

            // Get the report location
            //ClassLoader classLoader = getClass().getClassLoader();
            //InputStream inputStream = classLoader.getResourceAsStream("FirstReport.jrxml");

            String inputStream = ResourceUtils.getFile("classpath:FirstReport.jrxml").getAbsolutePath();


            // Setting parameters
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("studentName", "hello");


            Student student1 = new Student(1l, "Raouf", "I5z", "Kouba", "Annaba");
            Student student2 = new Student(2l, "John", "Smith", "Elm Street", "NewYork");

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
            JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);

            // Export report to PDF file
            byte[] byteArray = JasperExportManager.exportReportToPdf(print);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("fileName","test.pdf");

            return new ResponseEntity<byte[]>(byteArray, headers, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
