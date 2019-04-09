package org.testng.sample;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import org.assertj.core.api.Assertions;
import org.assertj.core.description.TextDescription;
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
    Assertions.assertThat(attributes.getValue("Automatic-Module-Name")).isEqualTo("org.testng");
    String value =  attributes.getValue("Import-Package");
    Assertions.assertThat(value)
        .as(new TextDescription("OSGI Header Check [GITHUB-1678]")).isNotEmpty();
    for (Map.Entry<Object, Object> each : attributes.entrySet()) {
      Reporter.log(String.format("(%s, %s)", each.getKey(), each.getValue()), true);
    }
  }
}
