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
package com.cognifide.aet.job.common.comparators.statuscodes;

import com.cognifide.aet.job.common.collectors.statuscodes.StatusCode;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import java.util.List;

class StatusCodesFilter {

  private final int lowerBound;

  private final int upperBound;

  private final List<Integer> filterCodes;

  StatusCodesFilter(int lowerBound, int upperBound, List<Integer> filterCodes) {
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
    this.filterCodes = filterCodes;
  }

  public List<StatusCode> filter(List<StatusCode> codes) {
    return FluentIterable.from(codes).filter(new StatusCodeFilterPredicate()).toList();
  }

  private class StatusCodeFilterPredicate implements Predicate<StatusCode> {

    @Override
    public boolean test(StatusCode statusCode) {
      return apply(statusCode);
    }

    @Override
    public boolean apply(StatusCode statusCode) {
      if (statusCode.getCode() < lowerBound) {
        return false;
      }
      if (statusCode.getCode() > upperBound) {
        return false;
      }
      if (!filterCodes.isEmpty() && !filterCodes.contains(statusCode.getCode())) {
        return false;
      }
      if (statusCode.getCode() < 0) {
        return false;
      }
      return true;
    }
  }
}
