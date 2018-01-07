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
package com.cognifide.aet.communication.api.job;

import com.cognifide.aet.communication.api.metadata.Url;
import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * Model which stores collection job description (configuration).
 */
public class CollectorJobData extends JobData {

  private static final long serialVersionUID = -1868453529026678407L;

  private final List<Url> urls;

  private final String proxy;

  /**
   * @param company - company name.
   * @param project - project name.
   * @param suiteName - suite name.
   * @param testName - test name.
   * @param urls - list of urls for collector job.
   * @param proxy - used proxy name or null if none proxy is used
   */
  public CollectorJobData(String company, String project, String suiteName, String testName,
      List<Url> urls,
      String proxy) {
    super(company, project, suiteName, testName);
    this.urls = urls;
    this.proxy = proxy;
  }

  /**
   * @return list of urls for collector job.
   */
  public List<Url> getUrls() {
    return ImmutableList.copyOf(urls);
  }

  public String getProxy() {
    return proxy;
  }
}
