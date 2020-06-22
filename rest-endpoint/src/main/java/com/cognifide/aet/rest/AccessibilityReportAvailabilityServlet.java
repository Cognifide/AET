/*
 * AET
 * <p>
 * Copyright (C) 2013 Cognifide Limited
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.cognifide.aet.rest;

import static com.cognifide.aet.rest.Helper.isValidCorrelationId;
import static com.cognifide.aet.rest.Helper.responseAsJson;

import com.cognifide.aet.accessibility.report.service.AccessibilityReportService;
import com.cognifide.aet.rest.helpers.BundleVersionProvider;
import com.cognifide.aet.vs.ArtifactsDAO;
import com.cognifide.aet.vs.DBKey;
import com.cognifide.aet.vs.MetadataDAO;
import com.cognifide.aet.vs.StorageException;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true)
public class AccessibilityReportAvailabilityServlet extends BasicDataServlet {

  private static final long serialVersionUID = 7801533499484174272L;

  private static final Logger LOGGER = LoggerFactory.getLogger(AccessibilityReportAvailabilityServlet.class);

  private static final String APP_VERSION_HEADER = "X-Application-Version";

  @Reference
  private MetadataDAO metadataDAO;

  @Reference
  private ArtifactsDAO artifactsDAO;

  @Reference
  private BundleVersionProvider bundleVersionProvider;

  @Reference
  private AccessibilityReportService accessibilityReportService;

  @Reference
  protected transient HttpService httpService;


  @Override
  protected void process(DBKey dbKey, HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String correlationId = request.getParameter(Helper.CORRELATION_ID_PARAM);
    String verbosityType = request.getParameter("type");
    String extension = request.getParameter("extension");

    response.setHeader(APP_VERSION_HEADER, bundleVersionProvider.getBundleVersion());
    response.setCharacterEncoding("UTF-8");

    try {
      if (isValidCorrelationId(correlationId)) {
        AccessibilityReportService.ServiceTask serviceTask =
            accessibilityReportService
                .newTask()
                .withDbKey(dbKey)
                .withObjectId(correlationId);

        boolean isAvailable = serviceTask.invokeIsAvailable();
        Map<String, Boolean> json = new HashMap<>();

        response.setContentType("application/json");
        json.put("isAvailable", isAvailable);

        if (!isAvailable) {
          response.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
        }

        response.getWriter().write(GSON.toJson(json, new TypeToken<HashMap<String, Boolean>>() {}.getType()));
      } else {
        createNotFoundResponse(response, dbKey, correlationId);
      }
    } catch (StorageException | IllegalArgumentException e) {
      LOGGER.error("Failed during retrieval", e);
      response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
      response.getWriter().write(responseAsJson(GSON, "Exception while processing: %s", e.getMessage()));
    }
  }

  @Override
  protected HttpService getHttpService() {
    return this.httpService;
  }

  @Override
  protected void setHttpService(HttpService httpService) {
    this.httpService = httpService;
  }

  private void createNotFoundResponse(HttpServletResponse response, DBKey dbKey, String correlationId) throws IOException {
    response.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
    String paramsValuesMessage = "";

    if (correlationId != null) {
      paramsValuesMessage = String.format("correlationId: %s", correlationId);
    }

    response.getWriter()
        .write(responseAsJson(GSON, "Unable to get accessibility report with %s for %s", paramsValuesMessage, dbKey.toString()));
  }


  @Activate
  public void start() {
    register(Helper.getAccessibilityReportAvailabilityPath());
  }

  @Deactivate
  public void stop() {
    unregister(Helper.getAccessibilityReportAvailabilityPath());
  }
}
