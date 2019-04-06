package org.testng.sample;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.TestNG;
import org.testng.annotations.Test;

public class MetaInfReadTest {

  @Test
  public void metaInfReadTest() throws IOException {
    String location = TestNG.class.getProtectionDomain().getCodeSource().getLocation().getFile();
    File file = new File(location);
    JarFile jar = new JarFile(file);
    Manifest manifest = jar.getManifest();
    Attributes attributes = manifest.getMainAttributes();
    Assert.assertEquals(attributes.getValue("Automatic-Module-Name"), "org.testng");
    for (Map.Entry<Object, Object> each : attributes.entrySet()) {
      Reporter.log(String.format("(%s, %s)", each.getKey(), each.getValue()), true);
    }
  }
}
