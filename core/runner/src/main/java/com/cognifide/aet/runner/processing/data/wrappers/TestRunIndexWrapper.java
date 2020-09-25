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

import com.cognifide.aet.communication.api.metadata.Suite;
import com.cognifide.aet.communication.api.metadata.Test;
import com.cognifide.aet.communication.api.metadata.Url;
import com.cognifide.aet.communication.api.wrappers.Run;
import com.cognifide.aet.runner.processing.data.UrlPacket;
import java.util.Collections;
import java.util.List;

public class TestRunIndexWrapper extends RunIndexWrapper<Test> {

  public TestRunIndexWrapper(Run<Test> objectToRunWrapper) {
    super(objectToRunWrapper);
  }

  @Override
  public List<UrlPacket> getUrlPackets() {
    Suite suite = objectToRunWrapper.getRealSuite();
    Test test = objectToRunWrapper.getObjectToRun();
    UrlPacket packet = new UrlPacket(suite, test);
    for (Url url : test.getUrls()) {
      cleanUrlFromExecutionData(url);
      packet.addUrl(url);
    }
    return Collections.singletonList(packet);
  }

  @Override
  public int countUrls() {
    Test test = objectToRunWrapper.getObjectToRun();
    return test.getUrls().size();
  }

}
