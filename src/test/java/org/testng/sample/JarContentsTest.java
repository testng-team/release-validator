package org.testng.sample;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import static org.assertj.core.api.Assertions.assertThat;
import org.testng.Reporter;
import org.testng.TestNG;
import org.testng.annotations.Test;

public class JarContentsTest {

  @Test
  public void listEntriesInJarTest() throws IOException {
    String location = TestNG.class.getProtectionDomain().getCodeSource().getLocation().getFile();
    File file = new File(location);
    JarFile jarFile = new JarFile(file);

    Enumeration<JarEntry> paths = jarFile.entries();
    assertThat(paths.hasMoreElements()).isTrue();
    Reporter.log("List of folders in [" + file.getAbsolutePath() + "] is as below", true);
    while (paths.hasMoreElements()) {
      JarEntry path = paths.nextElement();
      if (path.isDirectory()) {
        Reporter.log(path.getName(), true);
      }
    }
  }
}
