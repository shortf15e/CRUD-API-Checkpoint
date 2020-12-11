package com.example.CRUDAPICheckpoint;

import org.assertj.core.condition.DoesNotHave;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.*;
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

        user.setEmail("john@example.com");
        user.setPassword("password1");
        repository.save(user);


        user.setEmail("eliza@example.com");
        user.setPassword("password2");
        repository.save(user);

        user.setId(22L);
        user.setEmail("angelica@example.com");
        user.setPassword("something-secret");
        repository.save(user);

        MockHttpServletRequestBuilder request = get("/users");

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
                .andExpect(jsonPath("$.id", is(8)))
                .andExpect(jsonPath("$.email", is("john@example.com")) );

    }

    @Test

    public void testGetUserById () throws Exception {
        MockHttpServletRequestBuilder request = get("/users/3");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.email", is("angelica@example.com")));
    }

    @Test

    public void testUpdateUserById () throws Exception {
        MockHttpServletRequestBuilder request = patch("/users/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"email\": \"john@example.com\",\n" +
                        "    \"password\": \"something-secret\"\n" +
                        "  }");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.email", is("john@example.com")));
    }

    @Test

    public void testDeleteUserById () throws Exception {
        MockHttpServletRequestBuilder request = delete("/users/2");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("count", is(4)));
    }

    @Test

    public void testAuthenticateTrue () throws Exception {
        MockHttpServletRequestBuilder request = post("/users/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"email\": \"john@example.com\",\n" +
                        "    \"password\": \"password1\"\n" +
                        "  }");
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authenticated", is(true)))
                .andExpect(jsonPath("$.user.id", is(1)))
                .andExpect(jsonPath("$.user.email", is("john@example.com")));
    }

    @Test

    public void testAuthenticateFalse () throws Exception {
        MockHttpServletRequestBuilder request = post("/users/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"email\": \"john@example.com\",\n" +
                        "    \"password\": \"something-secret\"\n" +
                        "  }");
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authenticated", is(false)));
    }
}
