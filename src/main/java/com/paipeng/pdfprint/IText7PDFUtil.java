package com.paipeng.pdfprint;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;

public class IText7PDFUtil {
    public static void printPDF(String fileName, String printerName, boolean hasOutput) throws Exception {
        //PdfDocument document = new PdfDocument();
        PdfReader reader = new PdfReader(fileName);
        //PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName + "_out.pdf"));
    }

    public static void createPDF(String fileName) throws FileNotFoundException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(fileName));
        Document document = new Document(pdf);
        String line = "Hello! Welcome to iTextPdf";
        document.add(new Paragraph(line));
        document.close();

        System.out.println("Awesome PDF just got created.");
    }
}
