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
package com.cognifide.aet.job.common.modifiers.sleep;

import com.cognifide.aet.job.api.collector.CollectorFactory;
import com.cognifide.aet.job.api.collector.CollectorJob;
import com.cognifide.aet.job.api.collector.CollectorProperties;
import com.cognifide.aet.job.api.collector.WebCommunicationWrapper;
import com.cognifide.aet.job.api.exceptions.ParametersException;
import java.util.Map;

import com.cognifide.aet.job.api.info.FeatureMetadata;
import com.cognifide.aet.job.api.info.ParameterMetadata;
import org.osgi.service.component.annotations.Component;

@Component
public class SleepModifierFactory implements CollectorFactory {

  @Override
  public String getName() {
    return SleepModifier.NAME;
  }

  @Override
  public CollectorJob createInstance(CollectorProperties properties, Map<String, String> parameters,
      WebCommunicationWrapper webCommunicationWrapper) throws ParametersException {
    SleepModifier modifier = new SleepModifier();
    modifier.setParameters(parameters);
    return modifier;
  }

  @Override
  public FeatureMetadata getInformation() {
    return FeatureMetadata.builder()
            .type("Sleep")
            .tag(getName())
            .withParameters()
            .addParameter(
                    ParameterMetadata.builder()
                            .name("Duration")
                            .tag("duration")
                            .withoutValues()
                            .isMandatory(true)
                            .description("Sleep time, in milliseconds")
                            .build()
            )
            .and()
            .withoutDeps()
            .dropTo("Collectors")
            .group("Modifiers")
            .proxy(false)
            .wiki("https://github.com/Cognifide/aet/wiki/SleepModifier")
            .build();
  }
}
