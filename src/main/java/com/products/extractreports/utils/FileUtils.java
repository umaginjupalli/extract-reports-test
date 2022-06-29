package com.products.extractreports.utils;

import com.products.extractreports.model.ReportOutput;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileUtils {

    private static final String FILE_NAME = "Input.txt";

    private static String[] HEADERS = { "Client_Information", "Product_Information",
            "Total_Transaction_Amount"};

    public static ByteArrayOutputStream writeCSV() throws Exception {
        List<ReportOutput> reportOutputList = getLines();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter printer = new CSVPrinter(new OutputStreamWriter(out),
                CSVFormat.DEFAULT.withHeader(HEADERS));
        reportOutputList.forEach(reportOutput -> {
            try {
                printer.printRecord(reportOutput.getClientInformation(), reportOutput.getProductInformation(),
                        reportOutput.getTotalTransactionAmount());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        printer.close();
        return out;
    }

    public static List<ReportOutput> getLines() throws Exception {
        List<ReportOutput> reportOutputList = new ArrayList<ReportOutput>();
        try (Stream<String> stream = Files.lines(Paths.
                get(ClassLoader.getSystemResource(FILE_NAME).toURI()))) {
            stream.forEach(input -> {
                reportOutputList.add(constructReportOutput(input));
            });
        }
        return reportOutputList;
    }

    public static ReportOutput constructReportOutput(String line) {
        ReportOutput reportOutput = new ReportOutput();
        reportOutput.setClientInformation(constructClientInformation(line));
        reportOutput.setProductInformation(constructProductInformation(line));
        reportOutput.setTotalTransactionAmount(constructTotalTransactionAmount(line));
        return reportOutput;
    }

    private static String constructClientInformation(final String line) {
        //Combination of CLIENT TYPE, CLIENT NUMBER, ACCOUNT NUMBER, SUBACCOUNT NUMBER
        final StringBuffer clientInformation = new StringBuffer(line);
        final String clientType = clientInformation.substring(3, 7);
        final String clientNumber = clientInformation.substring(7, 11);
        final String accountNumber = clientInformation.substring(11, 15);
        final String subAccountNumber = clientInformation.substring(15, 19);
        final StringBuffer clientInformationResult = new StringBuffer();
        clientInformationResult.append(clientType).append(clientNumber).
                append(accountNumber).append(subAccountNumber);
        return clientInformationResult.toString();
    }

    private static String constructProductInformation(final String line) {
        //Combination of EXCHANGE CODE, PRODUCT GROUP CODE, SYMBOL, EXPIRATION DATE
        final StringBuffer productInformation = new StringBuffer(line);
        final String exchangeCode = productInformation.substring(27, 31);
        final String productGroupCode = productInformation.substring(25, 27);
        final String symbol = productInformation.substring(31, 37);
        final String expirationDate = productInformation.substring(37, 45);
        final StringBuffer productInformationResult = new StringBuffer();
        productInformationResult.append(exchangeCode).append(productGroupCode).
                append(symbol).append(expirationDate);
        return productInformationResult.toString();
    }

    private static BigDecimal constructTotalTransactionAmount(final String line) {
        // Net Total of the (QUANTITY LONG - QUANTITY SHORT)
        final StringBuffer amountInformation = new StringBuffer(line);
        final String quantityLong = amountInformation.substring(52, 62);
        final String quantityShort = amountInformation.substring(63, 73);
        return new BigDecimal(quantityLong).subtract(new BigDecimal(quantityShort));
    }
}
