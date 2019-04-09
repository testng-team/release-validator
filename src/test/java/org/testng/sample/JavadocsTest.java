package org.testng.sample;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.JarFile;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.description.TextDescription;
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
    assertThat(jarFiles).isNotNull();
    assertThat(jarFiles.length)
        .as(new TextDescription(type + " Jar should have been found"))
        .isNotEqualTo(0);
    String file = jarFiles[0];
    Path jarFileLocation = Paths.get(directory.getAbsolutePath() + File.separator + file);
    JarFile jarFile = new JarFile(jarFileLocation.toFile());
    assertThat(jarFile.getEntry(toTest)).isNotNull();
    FileChannel imageFileChannel = FileChannel.open(jarFileLocation);
    float kb = imageFileChannel.size() / 1024;
    assertThat(kb)
        .as(new TextDescription("File [" + jarFile + "] size should have been atleast 400 KB"))
        .isGreaterThanOrEqualTo(500);
  }
}
