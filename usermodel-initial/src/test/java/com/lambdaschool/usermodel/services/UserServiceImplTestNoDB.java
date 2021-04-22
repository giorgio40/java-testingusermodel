package com.lambdaschool.usermodel.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.usermodel.UserModelApplicationTesting;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.repository.RoleRepository;
import com.lambdaschool.usermodel.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplicationTesting.class,
        properties = {
                "command.line.runner.enabled=false"})
public class UserServiceImplTestNoDB {
  @Autowired
  private RoleService roleService;
  @Autowired
  private UserService userService;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private RoleRepository rolerepos;


  @MockBean
  private HelperFunctions helperFunctions;

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
    u3.setUserid(30);
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
    MockitoAnnotations.initMocks(this);

  }

  @After
  public void tearDown() throws Exception {
  }


  @Test
  public void findUserById() {
    Mockito.when(userRepository.findById(20L))
            .thenReturn(Optional.of(userList.get(1)));

    assertEquals("cinnamon",
            userService.findUserById(20)
                    .getUsername());


  }

  @Test
  public void findByNameContaining () {
    Mockito.when(userRepository.findByUsernameContainingIgnoreCase("barnbarn"))
            .thenReturn(userList);

    assertEquals(5, userService.findByNameContaining("barnbarn").size());

  }
  @Test
  public void findAll() {
    Mockito.when(userRepository.findAll())
            .thenReturn(userList);
    assertEquals(5,userService.findAll().size());

  }

  @Test
  public void delete() {
    Mockito.when(userRepository.findById(30L))
            .thenReturn(Optional.of(userList.get(0)));
    Mockito.doNothing()
            .when(userRepository)
            .deleteById(30L);
  }

  @Test
  public void findByName  () {
    Mockito.when(userRepository.findByUsername("admin"))
            .thenReturn(userList.get(0));
    assertEquals("admin",userService.findByName("admin").getUsername());
  }
  @Test
  public void save() {
    String userName3 = "marley";
    User user3 = new User(userName3,
            "cingaderosa",
            "marley@gmail.com");
    Role roleType1 = new Role("dog");
    roleType1.setRoleid(5L);

    user3.getRoles()
            .add(new UserRoles(user3, roleType1));
    Mockito.when(userRepository.save(any(User.class)))
            .thenReturn(user3);
    Mockito.when(rolerepos.findById(5L))
            .thenReturn(Optional.of(roleType1));

    assertEquals("marley", userService.save(user3)
            .getUsername());

  }

  @Test
  public void update() {

    Role r2 = new Role ("user");
    r2.setRoleid(2);
    User u2 = new User("cinnamon","password","cinnamon@school.lambda");
    u2.getRoles().add(new UserRoles(u2,r2));
    u2.getUseremails().add(new Useremail(u2,"cinnamon@mymail.thump"));
    u2.getUseremails().add(new Useremail(u2,"hops@mymail.thump"));
    u2.getUseremails().add(new Useremail(u2,"bunny@email.thump"));



    Mockito.when(rolerepos.findById(2L))
            .thenReturn(Optional.of(r2));
    Mockito.when(userRepository.findById(103L))
            .thenReturn(Optional.of(userList.get(2)));

    Mockito.when(userRepository.save(any(User.class)))
            .thenReturn(u2);
    Mockito.when(helperFunctions.isAuthorizedToMakeChange(anyString()))
            .thenReturn(true);
        assertEquals("bunny@email.thump",
                userService.update(u2,103L)
                        .getUseremails()
                        .get(2)
                        .getUseremail());
  }


  @Test
  public void deleteAll(){
  Mockito.doNothing()
          .when(userRepository)
          .deleteAll();
  userService.deleteAll();
  assertEquals(5,userList.size());





  }
}

















//  @Test
//  public void deleteAll() {
//    Mockito.when(userRepository.deleteAll())
//            .thenReturn();
//  }
//}












