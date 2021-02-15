package com.example.phonenosdb.api;

import com.example.phonenosdb.repositories.PhoneNoDataRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@Sql({"/data-integration-tests.sql"})
public class PhoneNoSearchApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PhoneNoDataRepository phoneNoDataRepository;

    private final String FINDALL_PHONENOS_URL_PATH = "/api/v1/phone_nos/all";

    @Test
    public void phoneNos_findAll_thenOk() throws Exception {
        // total 16 entries in test db
        // default request (page 0) should return 10
        mockMvc.perform(get(FINDALL_PHONENOS_URL_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(10)));

        // request for page 1 should return 6
        mockMvc.perform(get(FINDALL_PHONENOS_URL_PATH + "?page_no=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(6)));

        // request for page 2 should return 0
        mockMvc.perform(get(FINDALL_PHONENOS_URL_PATH + "?page_no=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));

        // request for no_of_records 7, page 0, should return 7
        mockMvc.perform(get(FINDALL_PHONENOS_URL_PATH + "?page_no=0&no_of_records=7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(7)));

        // request for no_of_records 9 page 1 should return 7 (16-9=7)
        mockMvc.perform(get(FINDALL_PHONENOS_URL_PATH + "?page_no=1&no_of_records=9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(7)));

        // request for no_of_records = 5, page 3 should return 1
        mockMvc.perform(get(FINDALL_PHONENOS_URL_PATH + "?page_no=3&no_of_records=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

}
