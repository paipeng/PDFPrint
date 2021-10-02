package com.paipeng.pdfprint;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.printing.PDFPrintable;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Destination;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Sides;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URI;

public class IText7PDFUtil {
    public static void printPDF(String fileName, String printerName, boolean hasOutput) throws Exception {
        FileInputStream fio = new FileInputStream(fileName);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(new PdfReader(fileName), pdfWriter);
        Document document = new Document(pdf);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        for(PrintService printService : PrintServiceLookup.lookupPrintServices(null, null)) {
            System.out.println("printer name: " + printService.getName());
            if(printService.getName().equals(printerName) || printerName == null) {
                System.out.println("crete doc print job...");
                DocPrintJob job = printService.createPrintJob();

                PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
                if (hasOutput) {
                    attr.add(new Destination(new URI("file:////" + fileName + ".out")));
                }
                attr.add(MediaSizeName.ISO_A4);
                attr.add(Sides.ONE_SIDED);

                // prepare the flvaor you are intended to print
                DocFlavor docFlavor = DocFlavor.BYTE_ARRAY.PDF;
                System.out.println("print data byte length: " + bytes.length);
                Doc doc = new SimpleDoc(IOUtils.toByteArray(fio), docFlavor, null);
                job.print(doc, attr);
                break;
            }
        }
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
