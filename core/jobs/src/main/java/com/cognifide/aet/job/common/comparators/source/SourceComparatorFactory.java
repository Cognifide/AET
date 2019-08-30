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
package com.cognifide.aet.job.common.comparators.source;

import com.cognifide.aet.communication.api.metadata.Comparator;
import com.cognifide.aet.job.api.comparator.ComparatorFactory;
import com.cognifide.aet.job.api.comparator.ComparatorJob;
import com.cognifide.aet.job.api.comparator.ComparatorProperties;
import com.cognifide.aet.job.api.datafilter.DataFilterJob;
import com.cognifide.aet.job.api.exceptions.ParametersException;
import com.cognifide.aet.job.api.info.FeatureMetadata;
import com.cognifide.aet.job.api.info.ParameterMetadata;
import com.cognifide.aet.job.common.comparators.source.diff.DiffParser;
import com.cognifide.aet.vs.ArtifactsDAO;
import java.util.List;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class SourceComparatorFactory implements ComparatorFactory {

  private static final String COMPARATOR_TYPE = "source";
  private static final String COMPARATOR_NAME = "source";

  @Reference
  private ArtifactsDAO artifactsDAO;

  @Override
  public final String getType() {
    return COMPARATOR_TYPE;
  }

  @Override
  public final String getName() {
    return COMPARATOR_NAME;
  }

  @Override
  public final int getRanking() {
    return DEFAULT_COMPARATOR_RANKING;
  }

  @Override
  public ComparatorJob createInstance(Comparator comparator, ComparatorProperties properties,
      List<DataFilterJob> filters) throws ParametersException {

    Sources sources = new Sources(artifactsDAO, properties, filters, new CodeFormatter());
    final SourceComparator sourceComparator = new SourceComparator(artifactsDAO, properties,
        new DiffParser(), sources);
    sourceComparator.setParameters(comparator.getParameters());
    return sourceComparator;
  }

  @Override
  public FeatureMetadata getInformation() {
    return FeatureMetadata.builder()
            .type("Source")
            .tag(getName())
            .withParameters()
            .addParameter(
                    ParameterMetadata.builder()
                            .name("Comparator")
                            .tag("comparator")
                            .withValues()
                            .addValue("source")
                            .and().defaultValue("source")
                            .isMandatory(true)
                            .description("See wiki for specific information")
                            .build()
            )
            .addParameter(
                    ParameterMetadata.builder()
                            .name("Compare Type")
                            .tag("compareType")
                            .withValues()
                            .addValue("content")
                            .addValue("markup")
                            .addValue("allFormatted")
                            .addValue("all")
                            .and().defaultValue("all")
                            .isMandatory(false)
                            .description("See wiki for specific information")
                            .build()
            )
            .and()
            .withDeps("source-collectors").depType("Error")
            .dropTo("Comparators")
            .group("Comparators")
            .proxy(false)
            .wiki("https://github.com/Cognifide/aet/wiki/SourceComparator")
            .build();
  }
}
