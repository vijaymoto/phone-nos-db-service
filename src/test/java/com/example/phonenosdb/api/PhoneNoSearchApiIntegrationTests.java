package com.example.phonenosdb.api;

import com.example.phonenosdb.PhoneNosDbServiceApplication;
import com.example.phonenosdb.repositories.PhoneNoDataRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = PhoneNosDbServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class PhoneNoSearchApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PhoneNoDataRepository phoneNoDataRepository;

    private final String FINDALL_PHONENOS_URL_PREFIX = "/api/v1/phone_nos/all";

    @Test
    public void phoneNos_findAll_thenOk() throws Exception {
        // default request (page 0) should return 10
        mockMvc.perform(get(FINDALL_PHONENOS_URL_PREFIX))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(10)));

        // request for page 1 should return 5
        mockMvc.perform(get(FINDALL_PHONENOS_URL_PREFIX + "?page_no=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(5)));

        // request for page 2 should return 0
        mockMvc.perform(get(FINDALL_PHONENOS_URL_PREFIX + "?page_no=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));

        // request for page 2 should return 0
        mockMvc.perform(get(FINDALL_PHONENOS_URL_PREFIX + "?page_no=0&no_of_records=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(5)));

        // request for page 2 should return 0
        mockMvc.perform(get(FINDALL_PHONENOS_URL_PREFIX + "?page_no=1&no_of_records=8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(7)));

        // request for page 2 should return 0
        mockMvc.perform(get(FINDALL_PHONENOS_URL_PREFIX + "?page_no=3&no_of_records=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

}
