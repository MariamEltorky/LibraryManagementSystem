package com.maids.cc.LibraryManagementSystem;
import com.maids.cc.LibraryManagementSystem.controller.CsrfController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(CsrfController.class)
public class CsrfControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private CsrfToken csrfToken;

    @InjectMocks
    private CsrfController csrfController;

    @Test
    public void testGetCsrfToken() throws Exception {
        when(csrfToken.getToken()).thenReturn("mocked-csrf-token");

        mockMvc.perform(MockMvcRequestBuilders.get("/csrf"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.headerName").value("X-XSRF-TOKEN"));
    }
}
