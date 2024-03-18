package org.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.poi.xwpf.usermodel.*;

public class Main {

    public static void main(String[] args) {
        //TODO: Read from CSV from power school
        String[] students = {"John Doe", "Jane Smith", "Alice Johnson"};

        //TODO: add dynamic assignment name
        String templatePath = "template.docx";
        String outputPath = "output.docx";

        //  Search doc for custom string and replace with name
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

            //TODO add a call to windows API to print all the docs then delete them
            //TODO integrate with Google docs api call?
            //TODO figure out how to make work with pdfs
            //TODO figure out how to work on mac or on locked down machine
            //TODO
            //TODO setup as web app?

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