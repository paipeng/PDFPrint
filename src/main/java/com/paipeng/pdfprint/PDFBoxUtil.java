package com.paipeng.pdfprint;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Destination;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Sides;
import java.io.File;
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

}
