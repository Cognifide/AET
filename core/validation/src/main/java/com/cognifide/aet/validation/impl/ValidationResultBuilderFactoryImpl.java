/**
 * AET
 *
 * Copyright (C) 2013 Cognifide Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cognifide.aet.validation.impl;

import com.cognifide.aet.validation.ValidationResultBuilder;
import com.cognifide.aet.validation.ValidationResultBuilderFactory;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

@Service
@Component(
        label = "AET Validation Result Builder Factory",
        description = "AET Validation Result Builder Factory")
public class ValidationResultBuilderFactoryImpl implements ValidationResultBuilderFactory {

  @Override
  public ValidationResultBuilder createInstance() {
    return new ValidationResultBuilderImpl();
  }

}
