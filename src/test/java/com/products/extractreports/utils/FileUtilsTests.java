package com.products.extractreports.utils;

import com.products.extractreports.model.ReportOutput;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FileUtilsTests {

    @Test
    public void testGetLines() throws Exception {
        List<ReportOutput> reportOutputList = FileUtils.getLines();
        AtomicInteger count=new AtomicInteger(0);
        reportOutputList.forEach(input -> {
            count.getAndIncrement();
            System.out.println(input.getClientInformation() + "--" +
                    input.getProductInformation() + "--"+ input.getTotalTransactionAmount());
        });
        System.out.println("Count = "+count.get());
        Assert.isTrue(count.get() == 717, "Count equals total records");
    }

    @Test
    public void testWriteCSV() throws Exception {
        ByteArrayOutputStream output = FileUtils.writeCSV();
        System.out.println(output.toByteArray().length);
        Assert.isTrue(output.toByteArray().length == 29890, "Size of the file matches");
    }

}
