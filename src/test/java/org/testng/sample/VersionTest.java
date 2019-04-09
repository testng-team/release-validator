package org.testng.sample;

import static org.assertj.core.api.Assertions.assertThat;
import org.testng.annotations.Test;
import org.testng.internal.Version;

public class VersionTest {

  @Test
  public void testVersionCorrectness() {
    assertThat(Version.getVersionString()).isEqualTo(System.getProperty("current"));
  }
}
