package org.testng.sample;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.description.TextDescription;
import org.testng.TestNG;
import org.testng.annotations.Test;

public class CompilerTest {

  @Test
  public void testCompiledVersion() throws IOException {
    String location = TestNG.class.getProtectionDomain().getCodeSource().getLocation().getFile();
    File file = new File(location);

    JarFile jarFile = new JarFile(file);
    ZipEntry testngEntry = jarFile.getEntry("org/testng/TestNG.class");

    //Below logic is borrowed from : https://stackoverflow.com/a/27123/679824
    try (InputStream in = jarFile.getInputStream(testngEntry);
        DataInputStream data = new DataInputStream(in)) {
      if (0xCAFEBABE != data.readInt()) {
        throw new IOException("invalid header");
      }
      data.readUnsignedShort(); //First comes the minor version value. Read and ignore it.
      int major = data.readUnsignedShort();
      assertThat(major).as(new TextDescription("Ensure JDK8 compatibility")).isEqualTo(52);
    }
  }
}
