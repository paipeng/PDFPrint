package com.paipeng.pdfprint;

import org.apache.commons.cli.*;

public class App {
    private static String inputFilePath;
    private static String printerName;
    private static boolean hasOutput;
    private static boolean hasDialog;

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


        Option api = new Option("e", "engine", true, "pdf api: itext, pdfbox, spire");
        api.setRequired(false);
        options.addOption(api);


        Option dialog = new Option("d", "dialog", false, "show print dialog");
        dialog.setRequired(false);
        options.addOption(dialog);

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
        hasDialog = cmd.hasOption("dialog");

        System.out.println(inputFilePath);
        System.out.println(printerName);

        if (cmd.getOptionValue("engine") != null) {
            switch (cmd.getOptionValue("engine")) {
                case "itext":
                    if (hasDialog) {
                        IText7PDFUtil.printPDFWithDialog(inputFilePath, printerName, hasOutput);
                    } else {
                        IText7PDFUtil.printPDF(inputFilePath, printerName, hasOutput);
                    }
                    break;
                case "spire":
                    if (hasDialog) {
                        SpirePDFUtil.printPDFWithDialog(inputFilePath, printerName, hasOutput);
                    } else {
                        SpirePDFUtil.printPDF(inputFilePath, printerName, hasOutput);
                    }
                    break;
                default:
                    if (hasDialog) {
                        PDFBoxUtil.printPDFWithDialog(inputFilePath, printerName, hasOutput);
                    } else {
                        PDFBoxUtil.printPDF(inputFilePath, printerName, hasOutput);
                    }
                    break;
            }
        } else {
            PDFBoxUtil.printPDF(inputFilePath, printerName, hasOutput);
        }

    }

}
