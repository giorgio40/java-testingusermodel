package com.lambdaschool.usermodel.controllers;

import com.lambdaschool.usermodel.UserModelApplicationTesting;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.repository.UserRepository;
import com.lambdaschool.usermodel.services.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WithMockUser(username = "admin",
        roles = {"USER", "ADMIN"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = UserModelApplicationTesting.class,
        properties = {
                "command.line.runner.enabled=false"})
@AutoConfigureMockMvc
public class UserControllerTestNoDB {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    private List<User> userList;


    @Before
    public void setUp() throws Exception {
        userList = new ArrayList<>();

        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        r1.setRoleid(1);;
        r2.setRoleid(2);
        r3.setRoleid(3);

        User u1 = new User("admin","password",
                "admin@lambdaschool.com");
        u1.setUserid(10);
        u1.getRoles()
                .add(new UserRoles(u1,
                        r1));
        u1.getRoles()
                .add(new UserRoles(u1,
                        r2));
        u1.getRoles()
                .add(new UserRoles(u1,
                        r3));
        u1.getUseremails()
                .add(new Useremail(u1,
                        "admin@email.local"));


        userList.add(u1);

        // data, user
        User u2 = new User("cinnamon",
                "1234567",
                "cinnamon@lambdaschool.local");
        u2.setUserid(20);
        u2.getRoles()
                .add(new UserRoles(u2,
                        r2));
        u2.getRoles()
                .add(new UserRoles(u2,
                        r3));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "cinnamon@mymail.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "hops@mymail.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "bunny@email.local"));

        userList.add(u2);


        // user
        User u3 = new User("barnbarn",
                "ILuvM4th!",
                "barnbarn@lambdaschool.local");

        u3.getRoles()
                .add(new UserRoles(u3,
                        r2));
        u3.getUseremails()
                .add(new Useremail(u3,
                        "barnbarn@email.local"));

        userList.add(u3);

        User u4 = new User("puttat",
                "password",
                "puttat@school.lambda");
        u4.setUserid(40);
        u4.getRoles()
                .add(new UserRoles(u4,
                        r2));
        userList.add(u4);


        User u5 = new User("misskitty",
                "password",
                "misskitty@school.lambda");
        u5.setUserid(50);
        u5.getRoles()
                .add(new UserRoles(u5,
                        r2));
        userList.add(u5);

        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void listAllUsers() throws Exception {
        String ApiUrl = "/users/users";
        Mockito.when(userService.findAll())
                .thenReturn(userList);
        RequestBuilder rb = MockMvcRequestBuilders.get(ApiUrl)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
                .andReturn();
        String tr = r.getResponse()
                .getContentAsString();

       ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList);
        System.out.println(er);
//        assertEquals("er",tr);

    }

    @Test
    public void getUserById() {
    }

    @Test
    public void getUserByName() {
    }

    @Test
    public void getUserLikeName() {
    }

    @Test
    public void addNewUser() {
    }

    @Test
    public void updateFullUser() {
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void deleteUserById() {
    }

    @Test
    public void getCurrentUserInfo() {
    }
}