/*
 * AET
 *
 * Copyright (C) 2020 Cognifide Limited
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
package com.cognifide.aet.accessibility.report.models

import org.apache.commons.text.StringEscapeUtils
import org.slf4j.LoggerFactory
import java.net.MalformedURLException
import java.net.URL

class ReportRow(
    code: String, message: String, issue: AccessibilityIssue, solutions: String) {

  val code = code.trim { it <= ' ' }
  val message = message.trim { it <= ' ' }
  val path = tryGetUrlsPath(issue)
  val url = issue.url
  val lineNumber = issue.lineNumber
  val snippet = StringEscapeUtils.unescapeHtml4(issue.elementStringAbbreviated).trim { it <= ' ' }
  val solutions = solutions

  private fun tryGetUrlsPath(issue: AccessibilityIssue): String {
    return try {
      URL(issue.url).path
    } catch (e: MalformedURLException) {
      LOG.warn("Exception for provided URL: ${issue.url}", e)
      issue.url
    }
  }

  companion object {
    private val LOG by lazy { LoggerFactory.getLogger(ReportRow::class.java) }
  }
}
