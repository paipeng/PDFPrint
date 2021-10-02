package com.paipeng.pdfprint;

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
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.URI;

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
}
