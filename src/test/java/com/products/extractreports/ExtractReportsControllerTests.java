package com.products.extractreports;

import com.products.extractreports.controller.ExtractReportsController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class ExtractReportsControllerTests {

    @InjectMocks
    private ExtractReportsController extractReportsController = new ExtractReportsController();

    @Test
    public void shouldSuccessJSONExtractReports() throws Exception {
        MockMvcBuilders.standaloneSetup(extractReportsController).build()
                .perform(MockMvcRequestBuilders.get("/extract/json")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void shouldSuccessCSVExtractReports() throws Exception {
        MockMvcBuilders.standaloneSetup(extractReportsController).build()
                .perform(MockMvcRequestBuilders.get("/extract/csv")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

}
