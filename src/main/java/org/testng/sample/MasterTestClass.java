package org.testng.sample;

import org.testng.Assert;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MasterListener.class)
public class MasterTestClass implements IHookable, IRetryAnalyzer {

  @Override
  public void run(IHookCallBack iHookCallBack, ITestResult iTestResult) {}

  @Override
  public boolean retry(ITestResult iTestResult) {
    return false;
  }

  @BeforeGroups
  public void beforeGroups() {}

  @AfterGroups
  public void afterGroups() {}

  @BeforeSuite
  public void beforeSuite() {}

  @AfterSuite
  public void afterSuite() {}

  @BeforeTest
  public void beforeTest() {}

  @AfterTest
  public void afterTest() {}

  @BeforeClass
  public void beforeClass() {}

  @AfterClass
  public void afterClass() {}

  @BeforeMethod
  public void beforeMethod() {}

  @AfterMethod
  public void afterMethod() {}

  @Test
  public void testMethod() {
    Assert.assertTrue(true);
  }
}
