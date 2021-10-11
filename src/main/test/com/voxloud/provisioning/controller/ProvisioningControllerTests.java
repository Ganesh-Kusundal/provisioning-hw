package com.voxloud.provisioning.controller;

import com.voxloud.provisioning.service.ProvisioningService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProvisioningControllerTests {
    private MockMvc mockMvc;
    @Mock
    private ProvisioningService service;
    @InjectMocks
    private ProvisioningController controller;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getProvisioning_macAddressPresent_shouldReturnCorrectFile() throws Exception {
        String expectedResult = "Expected result";
        when(this.service.getProvisioningFile("aa-bb-cc-dd-ee")).thenReturn(expectedResult);
        this.mockMvc.perform(get("/api/v1/provisioning/aa-bb-cc-dd-ee"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedResult)));
    }

    @Test
    public void getProvisioning_macAddressAbsent_shouldReturnErrorNotFound() throws Exception {
        when(this.service.getProvisioningFile("aa-bb-cc-dd-ee")).thenReturn(null);
        this.mockMvc.perform(get("/api/v1/provisioning/aa-bb-cc-dd-ee"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
