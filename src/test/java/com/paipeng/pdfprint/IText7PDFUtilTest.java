package com.paipeng.pdfprint;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class IText7PDFUtilTest {
    @Test
    void printPDF() throws Exception {
        String output = "/Users/paipeng/Downloads/2022-4.pdf";
        IText7PDFUtil.printPDF(output, "PDF Printer", false);
    }

    @Test
    public void createPDF() throws FileNotFoundException {
        String output = "/Users/paipeng/Downloads/itext7.pdf";
        IText7PDFUtil.createPDF(output);
    }

    @Test
    void printPDFWithDialog() throws IOException {
        String output = "/Users/paipeng/Downloads/2022-4.pdf";
        IText7PDFUtil.printPDFWithDialog(output, null, false);
    }
}
