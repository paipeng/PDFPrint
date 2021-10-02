package com.paipeng.pdfprint;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class IText7PDFUtilTest {
    @Test
    public void createPDF() throws FileNotFoundException {
        String output = "/Users/paipeng/Downloads/itext7.pdf";
        IText7PDFUtil.createPDF(output);
    }

}
