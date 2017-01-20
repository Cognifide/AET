#
# Automated Exploratory Tests
#
# Copyright (C) 2013 Cognifide Limited
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
@filtering
Feature: Tests Results Filtering

  As an user of AET reports web application
  I want to filter visible tests results
  In order to check tests statuses easily

   Scenario: Filtering Tests Results: accessibility
    Given I have opened sample tests report page
    When I search for tests containing "accessibility"
    Then There are 15 tiles visible
    And Statistics text contains "15 ( 7 / 3 / 5 / 0 )"

   Scenario: Filtering Tests Results: cookie
    Given I have opened sample tests report page
    When I search for tests containing "cookie"
    Then There are 9 tiles visible
    And Statistics text contains "9 ( 4 / 0 / 5 / 0 )"
    
  Scenario: Filtering Tests Results: layout
    Given I have opened sample tests report page
    When I search for tests containing "layout"
    Then There are 7 tiles visible
    And Statistics text contains "9 ( 3 / 0 / 4 / 0 )"

   Scenario: Filtering Tests Results: jserrors
    Given I have opened sample tests report page
    When I search for tests containing "jserrors"
    Then There are 12 tiles visible
    And Statistics text contains "12 ( 5 / 0 / 7 / 0 )"
    
  Scenario: Filtering Tests Results: source
    Given I have opened sample tests report page
    When I search for tests containing "source"
    Then There are 17 tiles visible
    And Statistics text contains "17 ( 7 / 0 / 10 / 0 )"

  Scenario: Filtering Tests Results: status-codes
    Given I have opened sample tests report page
    When I search for tests containing "status"
    Then There are 20 tiles visible
    And Statistics text contains "20 ( 8 / 0 / 12 / 0 )"

  Scenario: Filtering Tests Results: w3c-html5
    Given I have opened sample tests report page
    When I search for tests containing "w3c-html5"
    Then There are 7 tiles visible
    And Statistics text contains "7 ( 1 / 1 / 5 / 0 )"

  Scenario: Filtering Tests Results: click-modifier
    Given I have opened sample tests report page
    When I search for tests containing "click"
    Then There are 1 tiles visible
    And Statistics text contains "1 ( 0 / 0 / 1 / 0 )"
    
  Scenario: Filtering Tests Results: header
    Given I have opened sample tests report page
    When I search for tests containing "header"
    Then There are 1 tiles visible
    And Statistics text contains "1 ( 0 / 0 / 1 / 0 )"  

  Scenario: Filtering Tests Results: sleep-modifier
    Given I have opened sample tests report page
    When I search for tests containing "sleep"
    Then There are 2 tiles visible
    And Statistics text contains "2 ( 1 / 0 / 1 / 0 )" 
    
  Scenario: Filtering Tests Results: wait-for-page-modifier
    Given I have opened sample tests report page
    When I search for tests containing "wait"
    Then There are 2 tiles visible
    And Statistics text contains "2 ( 1 / 0 / 1 / 0 )"
