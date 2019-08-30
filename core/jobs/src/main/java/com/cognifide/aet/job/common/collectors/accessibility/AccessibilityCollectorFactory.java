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
package com.cognifide.aet.job.common.collectors.accessibility;

import com.cognifide.aet.job.api.collector.CollectorFactory;
import com.cognifide.aet.job.api.collector.CollectorJob;
import com.cognifide.aet.job.api.collector.CollectorProperties;
import com.cognifide.aet.job.api.collector.WebCommunicationWrapper;
import com.cognifide.aet.job.api.exceptions.ParametersException;
import com.cognifide.aet.job.api.info.FeatureMetadata;
import com.cognifide.aet.job.api.info.ParameterMetadata;
import com.cognifide.aet.job.common.utils.javascript.JavaScriptJobExecutor;
import com.cognifide.aet.vs.ArtifactsDAO;
import java.util.Map;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class AccessibilityCollectorFactory implements CollectorFactory {

  @Reference
  private ArtifactsDAO artifactsDAO;

  private BundleContext context;

  @Override
  public String getName() {
    return AccessibilityCollector.NAME;
  }

  @Override
  public CollectorJob createInstance(CollectorProperties properties, Map<String, String> parameters,
      WebCommunicationWrapper webCommunicationWrapper) throws ParametersException {
    AccessibilityCollector collector = new AccessibilityCollector(artifactsDAO, properties,
        new JavaScriptJobExecutor(webCommunicationWrapper.getWebDriver()), context);
    collector.setParameters(parameters);
    return collector;
  }

  @Activate
  public void activate(BundleContext context) {
    this.context = context;
  }

  @Override
  public FeatureMetadata getInformation() {
    return FeatureMetadata.builder()
            .type("Accessibility")
            .tag(getName())
            .withParameters()
            .addParameter(
                    ParameterMetadata.builder()
                            .name("Standard")
                            .tag("standard")
                            .withValues()
                            .addValue("WCAG2A")
                            .addValue("WCAG2AA")
                            .addValue("WCAG2AAA")
                            .and().defaultValue("WCAG2AA")
                            .isMandatory(false)
                            .description("The parameter specifies the standard which the page is validated against")
                            .build()
            )
            .and()
            .withDeps("accessibility-comparators").depType("Warning")
            .dropTo("Collectors")
            .group("Collectors")
            .proxy(false)
            .wiki("https://github.com/Cognifide/aet/wiki/AccessibilityCollector")
            .build();
  }
}
