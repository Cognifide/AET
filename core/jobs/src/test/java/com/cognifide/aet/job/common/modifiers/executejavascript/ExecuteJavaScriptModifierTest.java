/**
 * Automated Exploratory Tests
 * <p>
 * Author: pnad@github
 * used HideModifier code - Copyright (C) 2017 Cognifide Limited
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
package com.cognifide.aet.job.common.modifiers.executejavascript;

import com.cognifide.aet.job.api.exceptions.ParametersException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import java.util.Map;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ExecuteJavaScriptModifierTest {

    private static final String PARAM_CMD = "cmd";

    private static final String PARAM_CMD_VALUE = "document.getElementById('toRemove').style.display='none'";

    private static final String URL = "http://www.cognifide.com";

    @Mock
    private WebDriver webDriver;

    @InjectMocks
    private ExecuteJavaScriptModifier tested;

    @Mock
    private Map<String, String> params;

    @Before
    public void setUp() {
        when(webDriver.getCurrentUrl()).thenReturn(URL);
    }

    @Test
    public void setParameters_CmdIsValid_ValidationPassedSuccessfuly() throws ParametersException {
        when(params.containsKey(PARAM_CMD)).thenReturn(true);
        when(params.get(PARAM_CMD)).thenReturn(PARAM_CMD_VALUE);
        tested.setParameters(params);
    }

    @Test(expected = ParametersException.class)
    public void setParameters_CmdIsNotPassed_ValidationPassedUnsuccessfuly()
            throws ParametersException {
        tested.setParameters(params);
    }


}
