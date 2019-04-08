package org.testng.sample;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.maven.model.Model;
import org.assertj.core.api.Assertions;
import org.assertj.core.description.TextDescription;
import org.fuin.utils4maven.MavenPomReader;
import org.testng.annotations.Test;

public class MavenDependencyTest {

  private static final String TESTNG_CANONICALFORM_PREFIX = "org.testng:testng:";
  private static final String COMPILE = "compile";
  private static final String PROVIDED = "provided";
  private static final String MSG_TEMPLATE = "<%s> scoped dependency comparison [%s] against [%s]";

  @Test
  public void runDependencyTests() {
    String currentVersion = System.getProperty("current");
    Model currentModel = MavenPomReader.readModel(TESTNG_CANONICALFORM_PREFIX + currentVersion);
    String previousVersion = System.getProperty("previous");
    Model previousModel = MavenPomReader.readModel(TESTNG_CANONICALFORM_PREFIX + previousVersion);

    List<DependencyInformation> currentCompileTime = extractDependencies(currentModel, COMPILE);
    List<DependencyInformation> previousCompileTime = extractDependencies(previousModel, COMPILE);
    Assertions.assertThat(currentCompileTime)
        .as(new TextDescription(MSG_TEMPLATE, COMPILE, currentVersion, previousVersion))
        .containsExactlyInAnyOrder(previousCompileTime.toArray(new DependencyInformation[0]));

    List<DependencyInformation> currentProvided = extractDependencies(currentModel, PROVIDED);
    List<DependencyInformation> previousProvided = extractDependencies(previousModel, PROVIDED);
    Assertions.assertThat(currentProvided)
        .as(new TextDescription(MSG_TEMPLATE, PROVIDED, currentVersion, previousVersion))
        .containsAll(previousProvided);
  }

  private static List<DependencyInformation> extractDependencies(Model model, String scope) {
    return model.getDependencies().stream()
        .filter(dependency -> dependency.getScope().equalsIgnoreCase(scope))
        .map(
            dependency ->
                new DependencyInformation(dependency.getGroupId(), dependency.getArtifactId()))
        .collect(Collectors.toList());
  }

  static class DependencyInformation {
    private String groupId;
    private String artifactId;

    DependencyInformation(String groupId, String artifactId) {
      this.groupId = groupId;
      this.artifactId = artifactId;
    }

    @Override
    public String toString() {
      return "{groupId='" + groupId + '\'' + ", artifactId='" + artifactId + "\'}";
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      DependencyInformation that = (DependencyInformation) o;
      return groupId.equals(that.groupId) && artifactId.equals(that.artifactId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(groupId, artifactId);
    }
  }
}
