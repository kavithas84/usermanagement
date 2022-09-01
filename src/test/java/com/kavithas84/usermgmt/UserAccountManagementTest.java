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

import static org.hamcrest.Matchers.is;
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
    public void testGetAllUsers_NoUser()
            throws Exception {



        ResultActions resultActions = mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testGetAllUsers()
            throws Exception {

        createNewUser("EmilyGilmore", "grandmother");
        createNewUser("RoryGilmore", "grandmother");


        ResultActions resultActions = mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect((jsonPath("$[1].name",is("RoryGilmore"))))
                .andExpect((jsonPath("$[0].name",is("EmilyGilmore"))));
    }


    @Test
    public void testGetInvalidUser()
            throws Exception {




        ResultActions resultActions = mvc.perform(get("/users/Rory")
                        .contentType(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isNotFound());


    }



    @Test
    public void testPostNewUser_Valid() throws Exception {
        ResultActions resultActions = createNewUser("EmilyGilmore", "grandmother");
        resultActions
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",is("EmilyGilmore")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());;
    }
    @Test
    public void testPostNewUser_DuplicateUser() throws Exception {
       createNewUser("EmilyGilmore", "grandmother");
        ResultActions resultActions = createNewUser("EmilyGilmore", "test23432");
        resultActions
                .andExpect(status().isImUsed());
    }
    @Test
    public void testPostNewUser_MissingNamePassword() throws Exception {
        ResultActions resultActions = createNewUser("", "");
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("password", is("Password is mandatory")))
                .andExpect(MockMvcResultMatchers.jsonPath("name", is("Name is mandatory")));
    }
    @Test
    public void testPostNewUser_InvalidLengthUsernamePassword() throws Exception {
        ResultActions resultActions = createNewUser("t", "3");
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("name", is("Name must be between 2 and 32 characters long")))
                .andExpect(MockMvcResultMatchers.jsonPath("password", is("Password must be atleast 6 characters long")));
    }

    private ResultActions createNewUser(String name,String password) throws Exception {
        String asJsonString = asJsonString(new UserAccount(name,password));
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                .post("/users")
                .content(asJsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        return resultActions;
    }

    @Test
    public void testAutheticateUser_noUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/users/authenticate")
                        .content(asJsonString(new UserAccount("Emily Gilmore", "grandmother")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void testAutheticateUser_validUserCred() throws Exception {
        createNewUser("Emily Gilmore", "grandmother");
        mvc.perform(MockMvcRequestBuilders
                        .post("/users/authenticate")
                        .content(asJsonString(new UserAccount("Emily Gilmore", "grandmother")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void testAutheticateUser_invalidPassword() throws Exception {
        createNewUser("Emily Gilmore", "grandmother");
        mvc.perform(MockMvcRequestBuilders
                        .post("/users/authenticate")
                        .content(asJsonString(new UserAccount("Emily Gilmore", "wrong")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
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