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
package com.mycila.maven.plugin.license.util;

import java.io.File;
import java.nio.charset.Charset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileContentTest {

  @Test
  void test_states() throws Exception {
    FileContent c = new FileContent(new File("src/test/data/compileCP/test2.txt"), Charset.defaultCharset());
    Assertions.assertEquals("a", c.nextLine());
    Assertions.assertEquals(3, c.getPosition());
  }

  @Test
  void test_delete() throws Exception {
    FileContent c = new FileContent(new File("src/test/data/compileCP/test2.txt"), Charset.defaultCharset());
    c.delete(2, 8);
    Assertions.assertEquals("a\r\nd\r\ne\r\nf\r\ng\r\nh\r\ni\r\n", c.getContent());
  }

  @Test
  void test_insert() throws Exception {
    FileContent c = new FileContent(new File("src/test/data/compileCP/test2.txt"), Charset.defaultCharset());
    c.insert(4, "hello");
    Assertions.assertEquals("a\r\nbhello\r\nc\r\nd\r\ne\r\nf\r\ng\r\nh\r\ni\r\n", c.getContent());
  }

  @Test
  void test_removeDupliatedEmptyEndLines() throws Exception {
    FileContent c = new FileContent(new File("src/test/data/compileCP/test3.txt"), Charset.defaultCharset());
    c.removeDuplicatedEmptyEndLines();
    Assertions.assertEquals("a\r\nb\r\n", c.getContent());

    c = new FileContent(new File("src/test/data/compileCP/test4.txt"), Charset.defaultCharset());
    c.removeDuplicatedEmptyEndLines();
    Assertions.assertEquals("\r\n", c.getContent());
  }
}
