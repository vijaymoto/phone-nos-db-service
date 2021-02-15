package com.example.phonenosdb.api;

import com.example.phonenosdb.entities.PhoneNoData;
import com.example.phonenosdb.repositories.PhoneNoDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@Sql({"/data-integration-tests.sql"})
@Sql(scripts = "/data-cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PhoneNoActivationApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PhoneNoDataRepository phoneNoDataRepository;

    private final String PHONENOS_ACTIVATE_URL_PATH = "/api/v1/phone_nos/%s/activate";

    private final String PHONENOS_DEACTIVATE_URL_PATH = "/api/v1/phone_nos/%s/deactivate";

    @Test
    public void phoneNos_activate_thenOk() throws Exception {

        // activate a phone no. which was activated already
        mockMvc.perform(post(String.format(PHONENOS_ACTIVATE_URL_PATH, "0999911111")))
                .andExpect(status().isOk());
        Optional<PhoneNoData> phoneNoDataOpt1 = phoneNoDataRepository.findById("0999911111");
        assertEquals(phoneNoDataOpt1.get().getIsActive(), true);

        // activate a phone no. which was deactivated previously - test 1
        mockMvc.perform(post(String.format(PHONENOS_ACTIVATE_URL_PATH, "0999933333")))
                .andExpect(status().isOk());
        Optional<PhoneNoData> phoneNoDataOpt2 = phoneNoDataRepository.findById("0999933333");
        assertEquals(phoneNoDataOpt2.get().getIsActive(), true);

        // activate a phone no. which was deactivated previously - test 2
        mockMvc.perform(post(String.format(PHONENOS_ACTIVATE_URL_PATH, "0999912341")))
                .andExpect(status().isOk());
        Optional<PhoneNoData> phoneNoDataOpt3 = phoneNoDataRepository.findById("0999912341");
        assertEquals(phoneNoDataOpt3.get().getIsActive(), true);

        // activate a non-existing phone no.
        mockMvc.perform(post(String.format(PHONENOS_ACTIVATE_URL_PATH, "0888811111")))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void phoneNos_deactivate_thenOk() throws Exception {

        // deactivate a phone no. which is already deactivated - test 1
        mockMvc.perform(post(String.format(PHONENOS_DEACTIVATE_URL_PATH, "0999966666")))
                .andExpect(status().isOk());
        Optional<PhoneNoData> phoneNoDataOpt1 = phoneNoDataRepository.findById("0999966666");
        assertEquals(phoneNoDataOpt1.get().getIsActive(), false);


        // deactivate a phone no. which was previously activated - test 1
        mockMvc.perform(post(String.format(PHONENOS_DEACTIVATE_URL_PATH, "0999944444")))
                .andExpect(status().isOk());
        Optional<PhoneNoData> phoneNoDataOpt2 = phoneNoDataRepository.findById("0999944444");
        assertEquals(phoneNoDataOpt2.get().getIsActive(), false);

        // deactivate a phone no. which was previously previously - test 2
        mockMvc.perform(post(String.format(PHONENOS_DEACTIVATE_URL_PATH, "0999912343")))
                .andExpect(status().isOk());
        Optional<PhoneNoData> phoneNoDataOpt3 = phoneNoDataRepository.findById("0999912343");
        assertEquals(phoneNoDataOpt3.get().getIsActive(), false);


        // deactivate a non-existing phone no.
        mockMvc.perform(post(String.format(PHONENOS_DEACTIVATE_URL_PATH, "0888811111")))
                .andExpect(status().isBadRequest());
    }

}
