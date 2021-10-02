package com.paipeng.pdfprint;

import org.junit.jupiter.api.Test;

import java.awt.print.PrinterException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PDFBoxUtilTest {

    @Test
    void printPDF() {
    }

    @Test
    void printPDFWithDialog() throws IOException, PrinterException {
        String output = "/Users/paipeng/Downloads/2022-4.pdf";
        PDFBoxUtil.printPDFWithDialog(output, null, false);
    }

    @Test
    void printPDFWithDialog2() throws IOException {
        String output = "/Users/paipeng/Downloads/2022-4.pdf";
        PDFBoxUtil.printPDFWithDialog2(output, null, false);
    }
}