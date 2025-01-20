/**
 * Copyright (C) 2008 Mycila (mathieu.carbou@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mycila.maven.plugin.license;

import java.nio.file.Path;

import org.apache.maven.monitor.logging.DefaultLog;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LicenseSetTest {

  @Test
  void multipleLicenseSets() throws Exception {
    final LicenseSet licenseSet1 = new LicenseSet();
    licenseSet1.basedir = Path.of("src/test/resources/check/strict").toFile();
    licenseSet1.header = "src/test/resources/test-header1-diff.txt";

    final LicenseSet licenseSet2 = new LicenseSet();
    licenseSet2.basedir = Path.of("src/test/resources/check/issue76").toFile();
    licenseSet2.header = "src/test/resources/test-header1.txt";

    final LicenseSet licenseSetWithoutBaseDir = new LicenseSet();
    licenseSetWithoutBaseDir.header = "test-header1.txt";

    final LicenseSet[] licenseSets = {
        licenseSet1,
        licenseSet2,
        licenseSetWithoutBaseDir
    };

    final LicenseCheckMojo check = new LicenseCheckMojo();
    check.licenseSets = licenseSets;
    check.project = new MavenProjectStub();
    check.strictCheck = false;
    check.defaultBasedir = Path.of("src/test/resources/unknown").toFile();
    final MockedLog logger = new MockedLog();
    check.setLog(new DefaultLog(logger));
    check.execute();

    final String log = logger.getContent();
    final String fileFromFirstSet = Path.of("src/test/resources/check/strict/space.java").toFile().getCanonicalPath().replace('\\', '/');
    final String fileFromSecondSet = Path.of("src/test/resources/check/issue76/after.xml").toFile().getCanonicalPath().replace('\\', '/');
    final String fileFromDefaultBaseDirSet = Path.of("src/test/resources/unknown/header.txt").toFile().getCanonicalPath().replace('\\', '/');

    Assertions.assertTrue(log.contains("Header OK in: " + fileFromFirstSet));
    Assertions.assertTrue(log.contains("Header OK in: " + fileFromSecondSet));
    Assertions.assertTrue(log.contains("Header OK in: " + fileFromDefaultBaseDirSet));
  }

  @Test
  void multipleLicenseSetsWithRelativePaths() throws Exception {
    final LicenseSet licenseSet1 = new LicenseSet();
    licenseSet1.basedir = Path.of("src/test/resources/check/def/../strict").toFile();
    licenseSet1.header = "src/test/resources/test-header1-diff.txt";

    final LicenseSet licenseSet2 = new LicenseSet();
    licenseSet2.basedir = Path.of("src/test/resources/check/def/../issue76").toFile();
    licenseSet2.header = "src/test/resources/test-header1.txt";

    final LicenseSet licenseSetWithoutBaseDir = new LicenseSet();
    licenseSetWithoutBaseDir.header = "test-header1.txt";

    final LicenseSet[] licenseSets = {
        licenseSet1,
        licenseSet2,
        licenseSetWithoutBaseDir
    };

    final LicenseCheckMojo check = new LicenseCheckMojo();
    check.licenseSets = licenseSets;
    check.project = new MavenProjectStub();
    check.strictCheck = false;
    check.defaultBasedir = Path.of("src/test/resources/unknown/../unknown").toFile();
    final MockedLog logger = new MockedLog();
    check.setLog(new DefaultLog(logger));
    check.execute();

    final String log = logger.getContent();
    final String fileFromFirstSet = Path.of("src/test/resources/check/strict/space.java").toFile().getCanonicalPath().replace('\\', '/');
    final String fileFromSecondSet = Path.of("src/test/resources/check/issue76/after.xml").toFile().getCanonicalPath().replace('\\', '/');
    final String fileFromDefaultBaseDirSet = Path.of("src/test/resources/unknown/header.txt").toFile().getCanonicalPath().replace('\\', '/');

    Assertions.assertTrue(log.contains("Header OK in: " + fileFromFirstSet));
    Assertions.assertTrue(log.contains("Header OK in: " + fileFromSecondSet));
    Assertions.assertTrue(log.contains("Header OK in: " + fileFromDefaultBaseDirSet));
  }


}
