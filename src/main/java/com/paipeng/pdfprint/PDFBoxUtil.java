package com.paipeng.pdfprint;

import com.sun.pdfview.PDFFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Destination;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Sides;
import javax.swing.*;
import java.awt.print.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class PDFBoxUtil {

    public static void printPDF(String fileName, String printerName, boolean hasOutput) throws Exception {
        PDDocument document = PDDocument.load(new File(fileName));
        PDFPrintable printable = new PDFPrintable(document);

        for(PrintService printService : PrintServiceLookup.lookupPrintServices(null, null)) {
            System.out.println("printer name: " + printService.getName());
            if(printService.getName().equals(printerName) || printerName == null) {
                DocPrintJob job = printService.createPrintJob();

                PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
                if (hasOutput) {
                    attr.add(new Destination(new URI("file:////" + fileName + ".out")));
                }
                attr.add(MediaSizeName.ISO_A4);
                attr.add(Sides.ONE_SIDED);

                Doc doc = new SimpleDoc(printable, DocFlavor.SERVICE_FORMATTED.PRINTABLE, null);
                job.print(doc, attr);
                break;
            }
        }
    }

    public static void printPDFWithDialog(String fileName, String printerName, boolean hasOutput) throws IOException, PrinterException {
        PDDocument document = PDDocument.load(new File(fileName));
        PrintRequestAttributeSet attr_set = new HashPrintRequestAttributeSet();
        attr_set.add(MediaSizeName.ISO_A4);
        attr_set.add(Sides.ONE_SIDED);
        PDFPageable p = new PDFPageable(document);
        //PDFPrintable printable = new PDFPrintable(pdDocument, Scaling.ACTUAL_SIZE);

        PrinterJob job = PrinterJob.getPrinterJob();
        //job.setJobName();
        job.setPageable(p);
        //job.setPrintable(printable);
        if (job.printDialog()) {
            //job.print();
            job.print(attr_set);
        } else {
            throw new PrinterException("print canceled");
        }
    }


    public static void printPDFWithDialog2(String fileName, String printerName, boolean hasOutput) throws IOException {
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
}
