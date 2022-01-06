package org.testng.sample;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.maven.model.Model;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.assertj.core.description.TextDescription;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.jboss.shrinkwrap.resolver.api.maven.MavenStrategyStage;
import org.testng.annotations.Test;

public class MavenDependencyTest {

  private static final String TESTNG_CANONICALFORM_PREFIX = "org.testng:testng:";
  private static final String COMPILE = "compile";
  private static final String PROVIDED = "provided";
  private static final String MSG_TEMPLATE = "<%s> scoped dependency comparison [%s] against [%s]";

  private static Model readModel(String canonicalForm) throws IOException, XmlPullParserException {
    MavenResolverSystem resolver = Maven.resolver();
    MavenStrategyStage stage = resolver.resolve(canonicalForm);
    File testngDir = stage.withoutTransitivity().asSingleFile().getParentFile();
    String[] files = testngDir.list((dir, name) -> name.endsWith("pom"));
    if (files == null || files.length == 0) {
      throw new RuntimeException("Could not find any pom file in " + testngDir.getAbsolutePath());
    }
    FileReader fileReader = new FileReader(testngDir.getAbsolutePath() + File.separator + files[0]);
    MavenXpp3Reader reader = new MavenXpp3Reader();
    return reader.read(fileReader);
  }

  @Test
  public void runDependencyTests() throws XmlPullParserException, IOException {
    String currentVersion = System.getProperty("current", "7.5");
    Model currentModel = readModel(TESTNG_CANONICALFORM_PREFIX + currentVersion);
    String previousVersion = System.getProperty("previous", "7.4.0");
    Model previousModel = readModel(TESTNG_CANONICALFORM_PREFIX + previousVersion);

    List<DependencyInformation> currentCompileTime = extractDependencies(currentModel, COMPILE);
    List<DependencyInformation> previousCompileTime = extractDependencies(previousModel, COMPILE);
    assertThat(currentCompileTime)
        .as(new TextDescription(MSG_TEMPLATE, COMPILE, currentVersion, previousVersion))
        .containsExactlyInAnyOrder(previousCompileTime.toArray(new DependencyInformation[0]));

    List<DependencyInformation> currentProvided = extractDependencies(currentModel, PROVIDED);
    List<DependencyInformation> previousProvided = extractDependencies(previousModel, PROVIDED);
    assertThat(currentProvided)
        .as(new TextDescription(MSG_TEMPLATE, PROVIDED, currentVersion, previousVersion))
        .containsAll(previousProvided);
  }

  private static List<DependencyInformation> extractDependencies(Model model, String scope) {
    return model.getDependencies().stream()
        .filter(dependency -> Optional.ofNullable(dependency.getScope())
            .orElse(COMPILE)
            .equalsIgnoreCase(scope))
        .map(
            dependency ->
                new DependencyInformation(dependency.getGroupId(), dependency.getArtifactId()))
        .collect(Collectors.toList());
  }

  static class DependencyInformation {
    private final String groupId;
    private final String artifactId;

    DependencyInformation(String groupId, String artifactId) {
      this.groupId = groupId;
      this.artifactId = artifactId;
    }

    @Override
    public String toString() {
      return "{groupId='" + groupId + '\'' + ", artifactId='" + artifactId + "'}";
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
