package com.kavithas84.usermgmt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kavithas84.usermgmt.entity.UserAccount;
import com.kavithas84.usermgmt.repository.UserAccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = UserAccountManagement.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
//@WebMvcTest(UserAccountManagementTest.class)
public class UserAccountManagementTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserAccountRepository repository;

    Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();


    @Test
    public void testGetAllUsers()
            throws Exception {



        ResultActions resultActions = mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").exists());
//                        ?is("bob")));
    }


    @Test
    public void testGetInvalidUser()
            throws Exception {



        ResultActions resultActions = mvc.perform(get("/users/232")
                        .contentType(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk());


    }



    @Test
    public void testPostNewUser() throws Exception {
        String asJsonString = asJsonString(new UserAccount("Emily Gilmore", "grandmother"));
        mvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(asJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void testAutheticateUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/users/authenticate")
                        .content(asJsonString(new UserAccount("Emily Gilmore", "grandmother")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void matches() {
        String result = this.encoder.encode("password");
        String result2 = this.encoder.encode("password");
        assertEquals(result.equals("password"),false);
        assertFalse(result2.equals(result));
        assertTrue(this.encoder.matches("password", result));
    }
}