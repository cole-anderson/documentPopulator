package org.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.poi.xwpf.usermodel.*;

public class Main {

    public static void main(String[] args) {
        // List of students
        String[] students = {"John Doe", "Jane Smith", "Alice Johnson"};

        // Path to the template Word document
        String templatePath = "template.docx";

        // Path for the output document
        String outputPath = "output.docx";

        try {
            // Load the template Word document
            InputStream inputStream = new FileInputStream(templatePath);
            XWPFDocument doc = new XWPFDocument(inputStream);
            inputStream.close();

            // Replace placeholder in the document with student names
            for(String student: students) {
                replacePlaceholder(doc, "${namehere}", student);
                OutputStream outputStream = new FileOutputStream(student + outputPath);
                doc.write(outputStream);
                outputStream.close();
            }

            // Print the document
            System.out.println("Document printed successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void replacePlaceholder(XWPFDocument doc, String placeholder, String replacement) {
        for (XWPFParagraph p : doc.getParagraphs()) {
            for (XWPFRun r : p.getRuns()) {
                String text = r.getText(0);
                if (text != null && text.contains(placeholder)) {
                        text = text.replace(placeholder, replacement);
                    r.setText(text, 0);
                }
            }
        }
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun r : p.getRuns()) {
                            String text = r.getText(0);
                            if (text != null && text.contains(placeholder)) {
                                    text = text.replace(placeholder, replacement);
                                r.setText(text, 0);
                            }
                        }
                    }
                }
            }
        }
    }
}