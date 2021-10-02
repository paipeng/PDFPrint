package com.paipeng.pdfprint;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.sun.pdfview.PDFFile;
import org.apache.pdfbox.io.IOUtils;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Destination;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Sides;
import javax.swing.*;
import java.awt.print.*;
import java.io.*;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class IText7PDFUtil {
    public static void printPDF(String fileName, String printerName, boolean hasOutput) throws Exception {
        FileInputStream fio = new FileInputStream(fileName);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(new PdfReader(fileName), pdfWriter);
        Document document = new Document(pdf);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        for (PrintService printService : PrintServiceLookup.lookupPrintServices(null, null)) {
            System.out.println("printer name: " + printService.getName());
            if (printService.getName().equals(printerName) || printerName == null) {
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

    public static void printPDFWithDialog(String fileName, String printerName, boolean hasOutput) throws IOException {
        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            FileChannel fc = fis.getChannel();
            ByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            PDFFile pdfFile = new PDFFile(bb); // Create PDF Print Page
            PDFPrintPage pages = new PDFPrintPage(pdfFile);

            // Create Print Job
            PrinterJob pjob = PrinterJob.getPrinterJob();
            PageFormat pf = PrinterJob.getPrinterJob().defaultPage();
            Paper a4paper = new Paper();
            double paperWidth = 8.26;
            double paperHeight = 11.69;
            a4paper.setSize(paperWidth * 72.0, paperHeight * 72.0);

            /*
             * set the margins respectively the imageable area
             */
            double leftMargin = 0.3;
            double rightMargin = 0.3;
            double topMargin = 0.5;
            double bottomMargin = 0.5;

            a4paper.setImageableArea(leftMargin * 72.0, topMargin * 72.0,
                    (paperWidth - leftMargin - rightMargin) * 72.0,
                    (paperHeight - topMargin - bottomMargin) * 72.0);
            pf.setPaper(a4paper);

            pjob.setJobName(file.getName());
            Book book = new Book();
            book.append(pages, pf, pdfFile.getNumPages());
            pjob.setPageable(book);

            // Send print job to default printer
            if (pjob.printDialog()) {
                pjob.print();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(null, "Printing Error: "
                            + e.getMessage(), "Print Aborted",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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
