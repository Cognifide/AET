/**
 * AET
 *
 * Copyright (C) 2013 Cognifide Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.cognifide.aet.runner.processing.data.wrappers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.cognifide.aet.communication.api.metadata.Suite;
import com.cognifide.aet.communication.api.metadata.Url;
import com.cognifide.aet.communication.api.wrappers.Run;
import com.cognifide.aet.runner.processing.data.UrlPacket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SuiteRunIndexWrapperTest {

  private SuiteRunIndexWrapper suiteRunIndexWrapper;

  @Mock
  Run objectToRunWrapper;

  @Mock
  private Suite suite;

  private Optional<com.cognifide.aet.communication.api.metadata.Test> test;

  private Optional<com.cognifide.aet.communication.api.metadata.Test> test2;

  private Optional<Url> url;

  private Optional<Url> url2;

  @Before
  public void setUp() throws Exception {
    suiteRunIndexWrapper = new SuiteRunIndexWrapper(objectToRunWrapper);
    when(objectToRunWrapper.getRealSuite()).thenReturn(suite);
    when(objectToRunWrapper.getObjectToRun()).thenReturn(suite);
    test = Optional
        .of(new com.cognifide.aet.communication.api.metadata.Test("testName", "proxy", "chrome"));
    test2 = Optional
        .of(new com.cognifide.aet.communication.api.metadata.Test("testName", "proxy", "chrome"));
    url = Optional.of(new Url("urlName", "urlUrl", "urlDomain"));
    url2 = Optional.of(new Url("urlName2", "urlUrl2", "urlDomain2"));
  }

  @Test
  public void getUrlPackets_expectZero() {
    prepareZeroUrls();
    List<UrlPacket> packets = suiteRunIndexWrapper.getUrlPackets();
    Long urlsCount = packets.stream().map(UrlPacket::getUrls).mapToLong(List::size).sum();
    assertThat(urlsCount, is(0L));
  }

  @Test
  public void getUrlPackets_expectThree() {
    prepareThreeUrls();

    List<UrlPacket> packets = suiteRunIndexWrapper.getUrlPackets();
    Long urlsCount = packets.stream().map(UrlPacket::getUrls).mapToLong(List::size).sum();
    assertThat(urlsCount, is(3L));
  }

  @Test
  public void countUrls_expectZero() {
    prepareZeroUrls();

    assertThat(suiteRunIndexWrapper.countUrls(), is(0));
  }

  @Test
  public void countUrls_expectThree() {
    prepareThreeUrls();

    assertThat(suiteRunIndexWrapper.countUrls(), is(3));
  }

  private void prepareZeroUrls() {
    List<com.cognifide.aet.communication.api.metadata.Test> tests = new ArrayList<>();
    tests.add(test.get());
    tests.add(test2.get());
    when(suite.getTests()).thenReturn(tests);
  }

  private void prepareThreeUrls() {
    Set<Url> firstUrlsSet = new HashSet<>();
    firstUrlsSet.add(url.get());
    test.get().setUrls(firstUrlsSet);

    Set<Url> secondUrlsSet = new HashSet<>();
    secondUrlsSet.add(url.get());
    secondUrlsSet.add(url2.get());
    test2.get().setUrls(secondUrlsSet);

    List<com.cognifide.aet.communication.api.metadata.Test> tests = new ArrayList<>();
    tests.add(test.get());
    tests.add(test2.get());
    when(suite.getTests()).thenReturn(tests);
  }
}