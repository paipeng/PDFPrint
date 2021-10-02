package com.paipeng.pdfprint;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.io.InputStream;

public class PDFPrintPage implements Printable {
    private PDFFile file;
    private BufferedImage bufferedImage;

    PDFPrintPage(PDFFile file) {
        this.file = file;
        try {
            bufferedImage = readBufferedImageFromResources("images/test.jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage readBufferedImageFromResources(String path) throws IOException {
        ClassLoader cl = PDFPrintPage.class.getClassLoader();
        if (path != null) {
            InputStream in = cl.getResourceAsStream(path);
            return ImageIO.read(in);
        } else {
            return null;
        }
    }

    @Override
    public int print(Graphics g, PageFormat pageFormat, int index)
            throws PrinterException {
        int pagenum = index + 1;

        // don't bother if the page number is out of range.
        if ((pagenum >= 1) && (pagenum <= file.getNumPages())) {
            // fit the PDFPage into the printing area
            Graphics2D g2 = (Graphics2D) g;
            PDFPage page = file.getPage(pagenum);
            double pwidth = pageFormat.getImageableWidth();
            double pheight = pageFormat.getImageableHeight();

            double aspect = page.getAspectRatio();
            double paperaspect = pwidth / pheight;

            Rectangle imgbounds;

            if (aspect > paperaspect) {
                // paper is too tall / pdfpage is too wide
                int height = (int) (pwidth / aspect);
                imgbounds = new Rectangle(
                        (int) pageFormat.getImageableX(),
                        (int) (pageFormat.getImageableY() + ((pheight - height) / 2)),
                        (int) pwidth, height);
            } else {
                // paper is too wide / pdfpage is too tall
                int width = (int) (pheight * aspect);
                imgbounds = new Rectangle(
                        (int) (pageFormat.getImageableX() + ((pwidth - width) / 2)),
                        (int) pageFormat.getImageableY(), width, (int) pheight);
            }

            // render the page
            PDFRenderer pgs = new PDFRenderer(page, g2, imgbounds, null,
                    null);
            try {
                page.waitForFinish();
                pgs.run();
            } catch (InterruptedException ie) {
            }


            g2.setColor(Color.BLACK);
            g2.drawString("This is a test!!!!", 20, 10);

            g2.drawLine(10, 10, 100, 100);

            //g2.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
            if (bufferedImage != null) {
                g2.drawImage(bufferedImage, 0, bufferedImage.getHeight(),
                        (int) bufferedImage.getWidth(),
                        (int) -bufferedImage.getHeight(), null);
            }
            return PAGE_EXISTS;
        } else {
            return NO_SUCH_PAGE;
        }
    }

}
