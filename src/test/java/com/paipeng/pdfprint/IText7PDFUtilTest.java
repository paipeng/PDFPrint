package com.paipeng.pdfprint;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

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

}
