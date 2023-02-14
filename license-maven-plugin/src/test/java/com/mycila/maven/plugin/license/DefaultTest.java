package com.mycila.maven.plugin.license;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

class DefaultTest {
  @Test
  void test_Jenkinsfile_is_not_a_default_exclude() {
    assertThat(Arrays.asList(Default.EXCLUDES)).doesNotContain("Jenkinsfile");
  }
}
