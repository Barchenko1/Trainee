package com.nixsolutions.barchenko.webservice.soap;

import com.nixsolutions.barchenko.webservice.Role;
import com.nixsolutions.barchenko.webservice.User;;
import com.sun.xml.internal.ws.fault.ServerSOAPFaultException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceSoapImplTest {

    private User testUser;
    private Role role;
    private MyWebService myWebService;

    @Before
    public void setUp() throws DatatypeConfigurationException {
        myWebService = new UserWebServiceSoapImpl().getUserWebServiceSoapImplPort();
        testUser = initUser();
    }

    private User initUser() throws DatatypeConfigurationException {
        XMLGregorianCalendar calendar = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar("2010-01-01T00:00:00+02:00");
        testUser = new User();
        testUser.setLogin("soapTest");
        testUser.setPassword("1");
        testUser.setEmail("soapTest@gmail.com");
        testUser.setFirstName("soapTest");
        testUser.setLastName("soapTest");
        testUser.setBirthday(calendar);
        role = new Role();
        role.setRoleId(2L);
        role.setName("USER");
        testUser.setRole(role);
        return testUser;
    }

    @After
    public void tearDown() {
        myWebService.remove(testUser);
        testUser = new User();
    }

    @Test
    public void getUsers() {
        List<User> users = myWebService.findAll();
        assertTrue(!users.isEmpty());
    }

    @Test
    public void getUser() {
        User user = myWebService.findByLogin(testUser.getLogin());
        assertEquals(user.getLogin(), testUser.getLogin());
        assertEquals(user.getPassword(), testUser.getPassword());
        assertEquals(user.getEmail(), testUser.getEmail());
        assertEquals(user.getFirstName(), testUser.getFirstName());
        assertEquals(user.getLastName(), testUser.getLastName());
        assertEquals(user.getBirthday(), testUser.getBirthday());
        assertEquals(user.getRole().getRoleId(), testUser.getRole().getRoleId());
        assertEquals(user.getRole().getName(), testUser.getRole().getName());
    }

    @Test
    public void getUserIsFail() {
        User user = myWebService.findByLogin("wrong login");
        assertNull(user);
    }

    @Test
    public void create() throws DatatypeConfigurationException {
        User user = new User();
        user.setLogin("soapTestExample");
        user.setPassword("1");
        user.setEmail("soapTest@gmail.com");
        user.setFirstName("soapTest");
        user.setLastName("soapTest");
        XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar("2010-01-01T00:00:00+02:00");
        user.setBirthday(calendar);
        role = new Role();
        role.setRoleId(2L);
        role.setName("USER");
        user.setRole(role);
        myWebService.create(user);
        assertNotNull(user);
    }

    @Test(expected = ServerSOAPFaultException.class)
    public void createIsFail() {
        User user = new User();
        user.setLogin("wrong login");
        myWebService.create(user);
    }

    @Test
    public void remove() {
        User user = myWebService.findByLogin("soapTestExample");
        myWebService.remove(user);
        User userToDelete = myWebService.findByLogin(user.getLogin());
        assertNull(userToDelete);

    }

    @Test(expected = ServerSOAPFaultException.class)
    public void removeIsFail() {
        User user = new User();
        user.setLogin("wrong login");
        myWebService.remove(user);
    }

    @Test
    public void update() {
        User user = myWebService.findByLogin("soapTestForEdit");
        user.setPassword("2");
        myWebService.update(user);
        User userToEdit = myWebService.findByLogin(user.getLogin());
        assertEquals("2", userToEdit.getPassword());
    }

    @Test(expected = ServerSOAPFaultException.class)
    public void updateIsFail() {
        User user = new User();
        user.setLogin("wrong login");
        user.setPassword("2");
        myWebService.update(user);
    }

}