package org.testng.sample;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.JarFile;
import org.assertj.core.api.Assertions;
import org.testng.Assert;
import org.testng.TestNG;
import org.testng.annotations.Test;

public class JavadocsTest {

  @Test
  public void testJavadocsAvailability() throws IOException {
    runTest("javadoc", "org/testng/TestNG.html");
  }

  @Test
  public void testSourcesAvailability() throws IOException {
    runTest("sources", "org/testng/TestNG.java");
  }

  private void runTest(String type, String toTest) throws IOException {
    String location = TestNG.class.getProtectionDomain().getCodeSource().getLocation().getFile();
    File directory = new File(location).getParentFile();
    String[] jarFiles = directory.list((dir, name) -> name.endsWith(type + ".jar"));
    Assert.assertNotNull(jarFiles);
    Assert.assertTrue(jarFiles.length != 0, type + " Jar should have been found");
    String file = jarFiles[0];
    Path jarFileLocation = Paths.get(directory.getAbsolutePath() + File.separator + file);
    JarFile jarFile = new JarFile(jarFileLocation.toFile());
    Assertions.assertThat(jarFile.getEntry(toTest)).isNotNull();
    FileChannel imageFileChannel = FileChannel.open(jarFileLocation);
    float kb = imageFileChannel.size() / 1024;
    Assert.assertTrue(kb >= 500, "File [" + jarFile + "] size should have been atleast 400 KB");
  }
}
