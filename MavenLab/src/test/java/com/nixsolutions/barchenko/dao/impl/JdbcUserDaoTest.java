package com.nixsolutions.barchenko.dao.impl;

import com.nixsolutions.barchenko.dao.RoleDao;
import com.nixsolutions.barchenko.dao.UserDao;
import com.nixsolutions.barchenko.entity.User;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

import java.io.FileInputStream;
import java.sql.Date;
import java.util.List;

public class JdbcUserDaoTest extends DbUnit {

    private UserDao userDao;
    private RoleDao roleDao;

    private String ILLIGALARGUMENT = "IllegalArgumentException";
    private String EXCEPTION = "Exception";

    public JdbcUserDaoTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
        userDao = new JdbcUserDao();
        roleDao = new JdbcRoleDao();
    }

    public void testCreateNull() {
        try {
            userDao.create(null);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(ILLIGALARGUMENT);
        }
    }

    public void testUpdateNull() {
        try {
            userDao.update(null);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(ILLIGALARGUMENT);
        }
    }

    public void testRemoveNull() {
        try {
            userDao.remove(null);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(ILLIGALARGUMENT);
        }

    }

    public void testFindByLoginNull() {
        try {
            userDao.findByLogin(null);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(ILLIGALARGUMENT);
        }
    }

    public void testFindByEmailNull() {
        try {
            userDao.findByEmail(null);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(ILLIGALARGUMENT);
        }
    }

    public void testUpdateCount() throws Exception {
        User user = userDao.findByLogin("Artas");
        user.setLogin("Lich King");
        userDao.update(user);
        ITable userTableUpdate = createTable(
                "data/user/userDataSetUpdate.xml",
                "USER");
        ITable actualData = tester.getConnection().createTable(
                "USER");
        try {
            assertEquals(userTableUpdate.getRowCount(),
                    actualData.getRowCount());
        } catch (Exception e) {
            fail(EXCEPTION);
        }
    }

    public void testUpdateEquals() throws Exception {
        User user = userDao.findByLogin("Artas");
        user.setLogin("Lich King");
        userDao.update(user);
        ITable userTableUpdate = createTable(
                "data/user/userDataSetUpdate.xml",
                "USER");
        ITable actualData = tester.getConnection().createTable(
                "USER");
        try {
            Assertion.assertEquals(userTableUpdate, actualData);
        } catch (Exception e) {
            fail(EXCEPTION);
        }
    }

    public void testRemove() throws Exception {
        User user = userDao.findByLogin("Artas");
        userDao.remove(user);
        ITable userTable = createTable("data/user/userDataSetRemove.xml",
                "USER");
        ITable actualData = tester.getConnection().createTable(
                "USER");
        try {
            assertEquals(userTable.getRowCount(), actualData.getRowCount());
        } catch (Exception e) {
            fail(EXCEPTION);
        }
    }

    public void testRemoveEquals() throws Exception {
        User user = userDao.findByLogin("Artas");
        userDao.remove(user);
        ITable userTable = createTable("data/user/userDataSetRemove.xml",
                "USER");
        ITable actualData = tester.getConnection().createTable(
                "USER");
        try {
            String[] ignore = {"USERID"};
            Assertion.assertEqualsIgnoreCols(userTable, actualData, ignore);
        } catch (Exception e) {
            fail(EXCEPTION);
        }
    }

    public void testCreateCount() throws Exception {
        RoleDao roleDao = new JdbcRoleDao();
        User user = new User("pasha", "pasha",
                "pasha@mail.ru", "Pasha",
                "Barchenko", Date.valueOf("2017-05-11"),
                roleDao.findByName("USER"));
        userDao.create(user);
        ITable userTable = createTable("data/user/userDataSetCreate.xml",
                "USER");
        ITable actualData = tester.getConnection().createTable(
                "USER");
        try {
            assertEquals(userTable.getRowCount(), actualData.getRowCount());
        } catch (Exception e) {
            fail(ILLIGALARGUMENT);
        }
    }

    public void testCreateEquals() throws Exception {
        User user = new User("pasha", "pasha",
                "pasha@mail.ru", "Pasha","Barchenko",
                Date.valueOf("2017-05-11"), roleDao.findByName("USER"));

        userDao.create(user);

        ITable userTable = createTable("data/user/userDataSetCreate.xml",
                "USER");
        ITable actualData = tester.getConnection().createTable(
                "USER");
        try {
            String[] ignore = {"USERID"};
            Assertion.assertEqualsIgnoreCols(userTable, actualData, ignore);
        } catch (Exception e) {
            fail(ILLIGALARGUMENT);
        }
    }

    public void testFindAll() throws Exception {
        List<User> userListActual = userDao.findAll();
        ITable expectedData = createTable(
                "data/user/userDataSetFindAll.xml", "USER");
        assertEquals(userListActual.size(), expectedData.getRowCount());
    }

    public void testFindByLogin() throws Exception {
        User user = userDao.findByLogin("Artas");
        ITable userTable = createTable(
                "data/user/userDataSetFindLogin.xml", "USER");
        assertEquals(user.getLogin(), userTable.getValue(
                0, "LOGIN"));

    }

    public void testFindByEmail() throws Exception {
        User user = userDao.findByEmail("lorderon@mail.ua");
        ITable userTable = createTable(
                "data/user/userDataSetFindEmail.xml", "USER");
        assertEquals(user.getEmail(), userTable.getValue(
                0, "EMAIL"));
    }

    private ITable createTable (String path, String table) throws Exception {
        IDataSet expectedData = new FlatXmlDataSetBuilder()
                .build(new FileInputStream(path));
        return expectedData.getTable(table);
    }
}