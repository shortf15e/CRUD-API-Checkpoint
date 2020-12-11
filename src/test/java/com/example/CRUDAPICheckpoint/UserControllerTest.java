package com.example.CRUDAPICheckpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository repository;

    @BeforeEach
    public void init (){
        User user = new User();
        user.setId(1L);
        user.setEmail("john@example.com");
        user.setPassword("password1");
        repository.save(user);

        user.setId(2L);
        user.setEmail("eliza@example.com");
        user.setPassword("password2");
        repository.save(user);

        user.setId(3L);
        user.setEmail("angelica@example.com");
        user.setPassword("something-secret");
        repository.save(user);
    }



    @Test
    public void testGetAllUsers() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("john@example.com");
        user.setPassword("password1");
        repository.save(user);

        user.setId(2L);
        user.setEmail("eliza@example.com");
        user.setPassword("password2");
        repository.save(user);

        user.setId(3L);
        user.setEmail("angelica@example.com");
        user.setPassword("something-secret");
        repository.save(user);

        MockHttpServletRequestBuilder request = get("/users");
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\n" +
//                        "    \"id\": 3,\n" +
//                        "    \"title\": \"Bellman Ford\",\n" +
//                        "    \"deliveredOn\": \"2017-05-02\"\n" +
//                        "}");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[2].email", is("angelica@example.com")));

    }

    @Test
    public void testPostNewUser () throws Exception {
        MockHttpServletRequestBuilder request = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"email\": \"john@example.com\",\n" +
                        "    \"password\": \"something-secret\"\n" +
                        "  }");
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.email", is("john@example.com")) );
//        String actual = repository.findById(3L).get().getTitle();
//        String expected = "Who is you're daddy?";
//        assertEquals(expected, actual);
    }

}
