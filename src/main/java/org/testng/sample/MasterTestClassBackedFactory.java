package org.testng.sample;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class MasterTestClassBackedFactory {

  private String text;

  @Factory(dataProvider = "dp")
  public MasterTestClassBackedFactory(String text) {
    this.text = text;
  }

  @DataProvider(name = "dp")
  public static Object[][] getData() {
    return new Object[][] {{"a"}, {"b"}};
  }

  @Test
  public void testMethod() {
    Assert.assertNotNull(text);
  }
}
