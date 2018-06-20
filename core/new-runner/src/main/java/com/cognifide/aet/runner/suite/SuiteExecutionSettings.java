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
package com.cognifide.aet.runner.suite;

import com.cognifide.aet.runner.RunnerMode;
import java.io.Serializable;

/**
 * SuiteExecutionSettings
 *
 * @author: Maciej Laskowski
 */
public class SuiteExecutionSettings implements Serializable {

  private static final long serialVersionUID = -8453726127638448352L;

  final RunnerMode runnerMode;

  final boolean maintenanceMessage;

  public SuiteExecutionSettings(RunnerMode runnerMode, boolean maintenanceMessage) {
    this.runnerMode = runnerMode;
    this.maintenanceMessage = maintenanceMessage;
  }

  public RunnerMode getRunnerMode() {
    return runnerMode;
  }

  public boolean isMaintenanceMessage() {
    return maintenanceMessage;
  }

  public boolean shouldExecute() {
    return runnerMode.isMaintenanceTaskExecuted() == maintenanceMessage;
  }
}