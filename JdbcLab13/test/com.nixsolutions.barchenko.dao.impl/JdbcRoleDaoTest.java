package com.nixsolutions.barchenko.dao.impl;

import com.nixsolutions.barchenko.dao.RoleDao;
import com.nixsolutions.barchenko.entity.Role;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

import java.io.FileInputStream;

public class JdbcRoleDaoTest extends DbUnit {

    private RoleDao roleDao;

    private String ILLIGALARGUMENT = "IllegalArgumentException";
    private String EXCEPTION = "Exception";

    public JdbcRoleDaoTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
        roleDao = new JdbcRoleDao();
    }

    public void testCreateNull() {
        try {
            roleDao.create(null);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(ILLIGALARGUMENT);
        }

    }

    public void testUpdateNull() {
        try {
            roleDao.update(null);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(ILLIGALARGUMENT);
        }

    }

    public void testRemoveNull() {
        try {
            roleDao.remove(null);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(ILLIGALARGUMENT);
        }

    }

    public void testFindByNameNull() {
        try {
            roleDao.findByName(null);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(ILLIGALARGUMENT);
        }
    }

    public void testCreateCount() throws Exception {
        roleDao.create(new Role("CHIEF"));
        IDataSet expectedData = new FlatXmlDataSetBuilder()
                .build(new FileInputStream(
                        "data/role/roleDataSetCreate.xml"));
        ITable roleTable = expectedData.getTable("ROLE");
        ITable actualData = tester.getConnection().createTable(
                "ROLE");
        try {
            assertEquals(roleTable.getRowCount(), actualData.getRowCount());
        } catch (Exception e) {
            fail(EXCEPTION);
        }

    }

    public void testFindByName() throws Exception {
        Role user = roleDao.findByName("USER");
        IDataSet expectedData = new FlatXmlDataSetBuilder()
                .build(new FileInputStream(
                        "data/role/roleDataSetFindName.xml"));
        ITable roleTable = expectedData.getTable("ROLE");
        assertEquals(user.getName(), roleTable.getValue(0,
                "NAME"));
    }

    public void testRemove() throws Exception {
        roleDao.remove(roleDao.findByName("USER"));
        IDataSet expectedData = new FlatXmlDataSetBuilder()
                .build(new FileInputStream(
                        "data/role/roleDataSetRemove.xml"));
        ITable roleTable = expectedData.getTable("ROLE");
        ITable actualData = tester.getConnection().createTable(
                "ROLE");
        try {
            assertEquals(roleTable.getRowCount(), actualData.getRowCount());
        } catch (Exception e) {
            fail(EXCEPTION);
        }

    }

    public void testRemoveEquals() throws Exception {
        roleDao.remove(roleDao.findByName("USER"));
        IDataSet expectedData = new FlatXmlDataSetBuilder()
                .build(new FileInputStream(
                        "data/role/roleDataSetRemove.xml"));
        ITable roleTable = expectedData.getTable("ROLE");
        ITable actualData = tester.getConnection().createTable(
                "ROLE");
        try {
            String[] ignore = { "ROLEID" };
            Assertion.assertEqualsIgnoreCols(roleTable, actualData, ignore);
        } catch (Exception e) {
            fail(EXCEPTION);
        }
    }

    public void testUpdateCount() throws Exception {
        Role role = roleDao.findByName("ADMIN");
        role.setName("CHIEF");
        roleDao.update(role);

        IDataSet expectedData = new FlatXmlDataSetBuilder()
                .build(new FileInputStream(
                        "data/role/roleDataSetUpdate.xml"));
        ITable roleTableExpected = expectedData.getTable("ROLE");
        ITable actualData = tester.getConnection().createTable(
                "ROLE");
        try {
            assertEquals(roleTableExpected.getRowCount(),
                    actualData.getRowCount());
        } catch (Exception e) {
            fail(EXCEPTION);
        }
    }

    public void testUpdateEquals() throws Exception {
        Role role = roleDao.findByName("ADMIN");
        role.setName("CHIEF");
        roleDao.update(role);

        IDataSet expectedData = new FlatXmlDataSetBuilder()
                .build(new FileInputStream(
                        "data/role/roleDataSetUpdate.xml"));
        ITable roleTableExpected = expectedData.getTable("ROLE");
        ITable actualData = tester.getConnection().createTable(
                "ROLE");
        try {
            String[] ignore = { "ROLEID" };
            Assertion.assertEqualsIgnoreCols(roleTableExpected, actualData,
                    ignore);
        } catch (Exception e) {
            fail(EXCEPTION);
        }
    }
}
