package org.testng.sample;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.testng.Assert;
import org.testng.TestNG;
import org.testng.annotations.Test;

public class JavadocsTest {

  @Test
  public void testJavadocsAvailability() throws IOException {
    String location = TestNG.class.getProtectionDomain().getCodeSource().getLocation().getFile();
    File file = new File(location).getParentFile();
    String[] files = file.list((dir, name) -> name.endsWith("javadoc.jar"));
    Assert.assertNotNull(files);
    Assert.assertTrue(files.length != 0, "Javadocs should have been found");
    for (String each : files) {
      Path imageFilePath = Paths.get(file.getAbsolutePath() + File.separator + each);
      FileChannel imageFileChannel = FileChannel.open(imageFilePath);
      float mb = imageFileChannel.size() / (1024 * 1024);
      Assert.assertTrue(mb >= 0.9, "File [" + each + "] size should have been atleast 900 KB");
    }
  }
}
