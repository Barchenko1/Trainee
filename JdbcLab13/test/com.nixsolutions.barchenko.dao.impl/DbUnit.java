package com.nixsolutions.barchenko.dao.impl;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.tools.RunScript;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class DbUnit extends DBTestCase {

      protected IDatabaseTester tester;
      private static Properties prop;
      protected IDataSet beforeData;

      static {
          try {
              prop = new Properties();
              URL input = DbUnit.class.getResource("/h2.properties");
              InputStream inputStream = input.openStream();
              prop.load(inputStream);
              inputStream.close();
          } catch (IOException e) {
              e.printStackTrace();
          }
      }

    public DbUnit(String name) {
        super(name);
        System.setProperty(
                PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,
                prop.getProperty("jdbc.driver"));
        System.setProperty(
                PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
                prop.getProperty("jdbc.url"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,
                prop.getProperty("jdbc.username"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,
                prop.getProperty("jdbc.password"));
    }

    public void setUp() throws Exception {
        tester = new JdbcDatabaseTester(prop.getProperty("jdbc.driver"),
                prop.getProperty("jdbc.url"), prop.getProperty("jdbc.username"),
                prop.getProperty("jdbc.password"));
        RunScript.execute(prop.getProperty("jdbc.url"),
                prop.getProperty("jdbc.username"),
                prop.getProperty("jdbc.password"),
                "classpath:createDB.sql",
                null, false);
        beforeData = new FlatXmlDataSetBuilder()
                .build(Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("initTableTest.xml"));
        tester.setDataSet(beforeData);
        tester.onSetup();
    }

    @Override
    protected IDataSet getDataSet() throws Exception { return beforeData; }

    @Override
    protected DatabaseOperation getTearDownOperation()
            throws Exception { return DatabaseOperation.CLEAN_INSERT; }


}