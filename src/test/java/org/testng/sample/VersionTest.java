package org.testng.sample;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import org.testng.internal.Version;

public class VersionTest {

  @Test
  public void testVersionCorrectness() {
    Assertions.assertThat(Version.getVersionString()).isEqualTo(System.getProperty("current"));
  }
}
