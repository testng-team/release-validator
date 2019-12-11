package org.testng.sample;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.jar.JarFile;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.description.TextDescription;
import org.testng.TestNG;
import org.testng.annotations.Test;

public class JavadocsTest {
  private Function<Double, Double> kb = (size) -> size / 1024.0;
  private Function<Double, Double> mb = (kb) -> kb / 1014.0;

  @Test
  public void testJavadocsAvailability() throws IOException {
    runTest("javadoc", "org/testng/TestNG.html", mb, 1);
  }

  @Test
  public void testSourcesAvailability() throws IOException {
    runTest("sources", "org/testng/TestNG.java", kb, 400);
  }

  private void runTest(String type, String toTest, Function<Double, Double> calculator, int expected)
      throws IOException {
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
    double size = calculator.apply((double) imageFileChannel.size());
    assertThat(size)
        .as(new TextDescription("File [" + jarFile + "] size should have been atleast 400 KB"))
        .isGreaterThanOrEqualTo(expected);
  }
}
