package com.paipeng.pdfprint;

import org.apache.commons.cli.*;
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

public class App {
    public static void main(String[] args) throws Exception {
        Options options = new Options();

        Option input = new Option("i", "input", true, "input file path");
        input.setRequired(true);
        options.addOption(input);

        Option printer = new Option("p", "printer", true, "printer name");
        printer.setRequired(false);
        options.addOption(printer);

        Option output = new Option("o", "output", false, "output to file");
        output.setRequired(false);

        options.addOption(output);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;//not a good practice, it serves it purpose

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }

        String inputFilePath = cmd.getOptionValue("input");
        String printerName = cmd.getOptionValue("printer");


        System.out.println(inputFilePath);
        System.out.println(printerName);

        PDDocument document = PDDocument.load(new File(inputFilePath));
        PDFPrintable printable = new PDFPrintable(document);

        for(PrintService printService : PrintServiceLookup.lookupPrintServices(null, null)) {
            System.out.println("printer name: " + printService.getName());
            if(printService.getName().equals(printerName) || printerName == null) {
                DocPrintJob job = printService.createPrintJob();

                PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
                if (cmd.hasOption("output")) {
                    attr.add(new Destination(new URI("file:////" + inputFilePath + ".out")));
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
