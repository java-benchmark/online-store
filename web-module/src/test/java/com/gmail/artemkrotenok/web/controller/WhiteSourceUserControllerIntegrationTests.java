package com.gmail.artemkrotenok.web.controller;

import com.gmail.artemkrotenok.repository.UserRepository;
import com.gmail.artemkrotenok.repository.model.User;
import com.gmail.artemkrotenok.web.ApplicationRun;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@WithMockUser(roles = "ADMINISTRATOR")
@SpringBootTest(classes = ApplicationRun.class)
class WhiteSourceUserControllerIntegrationTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    private MockMvc restMockMvc;

    @BeforeEach
    public void setup() {

    }

    @Test
    @Transactional
    void singleFileUpload() throws Exception {

        Path path = Paths.get("item.txt");
        String someString = "[{\"name\":\"item\",\"uniqueNumber\":\"hey\",\"price\":10,\"description\":\"hey desc\",\"deleted\":false}]";
        byte[] bytes = someString.getBytes();

        restMockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        MockMultipartFile firstFile = new MockMultipartFile(
                "file", "item.txt",
                MediaType.TEXT_PLAIN_VALUE,
                bytes);

        assertThat(firstFile).isNotNull();

        MvcResult result = restMockMvc.perform(MockMvcRequestBuilders.multipart("/items/upload").file(firstFile))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Transactional
    void createUser() throws Exception {
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        restMockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        // Create the User
        MvcResult result = restMockMvc.perform(post("/users")
                .contentType("multipart/form-data")
                .param("surname", "test")
                .param("name", "test")
                .param("middleName", "test")
                .param("email", getRandomEmail())
                .param("role", "ADMINISTRATOR"))
                .andExpect(status().isOk())
                .andReturn();

        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate + 1);
        User testUser = userList.get(userList.size() - 1);
        assertThat(testUser.getName()).isEqualTo("test");
    }

    @Test
    @Transactional
    void createUser2() throws Exception {
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        restMockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        // Create the User
        MvcResult result = restMockMvc.perform(post("/users/add")
                .contentType("multipart/form-data")
                .param("surname", "test")
                .param("name", "test")
                .param("middleName", "test")
                .param("email", getRandomEmail())
                .param("address", "test")
                .param("role", "ADMINISTRATOR"))
                .andExpect(status().isOk())
                .andReturn();

        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate + 1);
        User testUser = userList.get(userList.size() - 1);
        assertThat(testUser.getName()).isEqualTo("test");
    }

    @Test
    @Transactional
    void singleFileUploadIsSubDirectory() throws Exception {

        Path path = Paths.get("item.txt");
        String someString = "[{\"name\":\"item\",\"uniqueNumber\":\"hey\",\"price\":10,\"description\":\"hey desc\",\"deleted\":false}]";
        byte[] bytes = someString.getBytes();

        restMockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        MockMultipartFile firstFile = new MockMultipartFile(
                "file", "item.txt",
                MediaType.TEXT_PLAIN_VALUE,
                bytes);

        assertThat(firstFile).isNotNull();

        MvcResult result = restMockMvc.perform(MockMvcRequestBuilders.multipart("/items/uploadIsSubDirectory").file(firstFile))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Transactional
    void singleFileUploadSanitizer() throws Exception {

        Path path = Paths.get("item.txt");
        String someString = "[{\"name\":\"item\",\"uniqueNumber\":\"hey\",\"price\":10,\"description\":\"hey desc\",\"deleted\":false}]";
        byte[] bytes = someString.getBytes();

        restMockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        MockMultipartFile firstFile = new MockMultipartFile(
                "file", "item.txt",
                MediaType.TEXT_PLAIN_VALUE,
                bytes);

        assertThat(firstFile).isNotNull();

        MvcResult result = restMockMvc.perform(MockMvcRequestBuilders.multipart("/items/uploadSanitizer").file(firstFile))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Transactional
    void singleFileUploadCheckFileName() throws Exception {

        Path path = Paths.get("item.txt");
        String someString = "[{\"name\":\"item\",\"uniqueNumber\":\"hey\",\"price\":10,\"description\":\"hey desc\",\"deleted\":false}]";
        byte[] bytes = someString.getBytes();

        restMockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        MockMultipartFile firstFile = new MockMultipartFile(
                "file", "item.txt",
                MediaType.TEXT_PLAIN_VALUE,
                bytes);

        assertThat(firstFile).isNotNull();

        MvcResult result = restMockMvc.perform(MockMvcRequestBuilders.multipart("/items/uploadCheckFileName").file(firstFile))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Transactional
    void singleFileUploadCheckPattern() throws Exception {

        Path path = Paths.get("item.txt");
        String someString = "[{\"name\":\"item\",\"uniqueNumber\":\"hey\",\"price\":10,\"description\":\"hey desc\",\"deleted\":false}]";
        byte[] bytes = someString.getBytes();

        restMockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        MockMultipartFile firstFile = new MockMultipartFile(
                "file", "item.txt",
                MediaType.TEXT_PLAIN_VALUE,
                bytes);

        assertThat(firstFile).isNotNull();

        MvcResult result = restMockMvc.perform(MockMvcRequestBuilders.multipart("/items/uploadCheckPattern").file(firstFile))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Transactional
    void singleFileUploadCheckUnsafePattern() throws Exception {

        Path path = Paths.get("item.txt");
        String someString = "[{\"name\":\"item\",\"uniqueNumber\":\"hey\",\"price\":10,\"description\":\"hey desc\",\"deleted\":false}]";
        byte[] bytes = someString.getBytes();

        restMockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        MockMultipartFile firstFile = new MockMultipartFile(
                "file", "item.txt",
                MediaType.TEXT_PLAIN_VALUE,
                bytes);

        assertThat(firstFile).isNotNull();

        MvcResult result = restMockMvc.perform(MockMvcRequestBuilders.multipart("/items/uploadCheckUnsafePattern").file(firstFile))
                .andExpect(status().isOk())
                .andReturn();
    }

    private String getRandomEmail() {
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        sb.append( (char)(r.nextInt(26) + 'a') );
        sb.append( (char)(r.nextInt(26) + 'a') );
        sb.append( (char)(r.nextInt(26) + 'a') );
        sb.append( (char)(r.nextInt(26) + 'a') );
        sb.append( (char)(r.nextInt(26) + 'a') );

        return sb.toString() + "@test.com";
    }
}
