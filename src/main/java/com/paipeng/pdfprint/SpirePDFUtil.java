package com.paipeng.pdfprint;

import com.spire.pdf.PdfDocument;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class SpirePDFUtil {
    public static void printPDF(String fileName, String printerName, boolean hasOutput) throws Exception {
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromFile(fileName);

        PrinterJob loPrinterJob = PrinterJob.getPrinterJob();
        PageFormat loPageFormat  = loPrinterJob.defaultPage();
        Paper loPaper = loPageFormat.getPaper();

        //remove the default printing margins
        loPaper.setImageableArea(0,0,loPageFormat.getWidth(),loPageFormat.getHeight());

        //set the number of copies
        loPrinterJob.setCopies(1);

        loPageFormat.setPaper(loPaper);
        loPrinterJob.setPrintable(pdf,loPageFormat);
        try {
            loPrinterJob.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }
}
