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
    private static String inputFilePath;
    private static String printerName;
    private static boolean hasOutput;
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

        inputFilePath = cmd.getOptionValue("input");
        printerName = cmd.getOptionValue("printer");
        hasOutput = cmd.hasOption("output");

        System.out.println(inputFilePath);
        System.out.println(printerName);

        PDFBoxUtil.printPDF(inputFilePath, printerName, hasOutput);
    }

}
