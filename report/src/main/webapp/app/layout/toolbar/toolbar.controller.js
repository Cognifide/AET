/*
 * AET
 *
 * Copyright (C) 2013 Cognifide Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
define([], function () {
  'use strict';
  return ['$scope', '$rootScope', '$uibModal', '$stateParams',
    'patternsService', 'metadataAccessService', 'notesService',
    'viewModeService', 'suiteInfoService', 'rerunService', 'historyService',
    '$http', 'endpointConfiguration',
    ToolbarController
  ];

  function ToolbarController($scope, $rootScope, $uibModal, $stateParams,
    patternsService, metadataAccessService, notesService, viewModeService,
    suiteInfoService, rerunService, historyService, $http, endpointConfiguration) {
    var vm = this;

    // disables accept button if compared against another suite patterns
    if (suiteInfoService.getInfo().patternCorrelationId) {
      vm.usesCrossSuitePattern = true;
    }

    vm.showAcceptButton = patternsMayBeUpdated;
    vm.showRevertButton = patternsMarkedForUpdateMayBeReverted;
    vm.displayAccessibilityModal = displayAccessibilityModal;
    vm.displayCommentModal = displayCommentModal;
    vm.scrollSidepanel = scrollSidepanel;
    vm.rerunSuite = rerunSuite;
    vm.rerunTest = rerunTest;
    vm.rerunURL = rerunURL;
    vm.rerunService = rerunService;
    vm.suiteInfoService = suiteInfoService;
    vm.checkRerunStatus = rerunService.checkRerunStatus;

    $rootScope.$on('metadata:changed', updateLeftHalfToolbar);
    $rootScope.$on('metadata:changed', updateRightHalfToolbar);

    $scope.$watch('viewMode', function () {
      setTimeout(function () {
        $('[data-toggle="popover"]').not('.pre-initialized-popover').popover({
          placement: 'bottom'
        });
      }, 0);
    });

    $('[data-toggle="popover"]').popover({
      placement: 'bottom'
    }).addClass('pre-initialized-popover');

    historyService.fetchHistory(suiteInfoService.getInfo().version, function () {
      var currentVersion = suiteInfoService.getInfo().version;
      vm.isLastSuiteVersion = historyService.getNextVersion(currentVersion) == null;
      getPreviousVersion(currentVersion);
      getNextVersion(currentVersion);
      updateLeftHalfToolbar();
    });

    updateRightHalfToolbar();

    updateAccessibilityInfo();

    $scope.copyToClipboard = function (textToCopy) {
      var copyElement = document.createElement("textarea");
      copyElement.style.position = 'fixed';
      copyElement.style.opacity = '0';
      copyElement.textContent = decodeURI(textToCopy);
      var tempBody = document.getElementsByTagName('body')[0];
      tempBody.appendChild(copyElement);
      copyElement.select();
      document.execCommand('copy');
      tempBody.removeChild(copyElement);
    }

    /***************************************
     ***********  Private methods  *********
     ***************************************/

    function updateAccessibilityInfo() {
      var suiteInfo = suiteInfoService.getInfo();
      var baseUrl = endpointConfiguration.getEndpoint().getUrl;

      $http.get(baseUrl + 'accessibility/report/available', {
        params: {
          company: suiteInfo.company,
          project: suiteInfo.project,
          suite: suiteInfo.name,
          correlationId: suiteInfo.correlationId
        }
      })
        .then(function (response) {
          vm.canGenerateAccessibilityReport = response.data.isAvailable;
        })
        .catch(function (error) {
          vm.canGenerateAccessibilityReport = false;
          console.error(error);
        });
    }

    function updateLeftHalfToolbar() {
      vm.showSuiteHistory = function () {
        displayHistoryModal();
      };
      vm.suiteInfo = suiteInfoService.getInfo();
      vm.suiteStatistics = metadataAccessService.getSuite();
      if (vm.suiteStatistics.patternCorrelationId) {
        vm.pattern = {
          name: vm.suiteStatistics.patternCorrelationId,
          url: 'report.html?company=' + vm.suiteStatistics.company +
            '&project=' + vm.suiteStatistics.project +
            '&correlationId=' + vm.suiteStatistics.patternCorrelationId
        };
      }
    }

    function updateRightHalfToolbar() {
      vm.viewMode = viewModeService.get();
      switch (vm.viewMode) {
        case viewModeService.SUITE:
          setupSuiteToolbarModel();
          break;
        case viewModeService.TEST:
          setupTestToolbarModel();
          break;
        case viewModeService.URL:
          setupUrlToolbarModel();
          break;
        default:
          setupSuiteToolbarModel();
      }
    }

    function getPreviousVersion(currentVersion) {
      vm.previousVersion = historyService.getPreviousVersion(currentVersion);
    }

    function getNextVersion(currentVersion) {
      vm.nextVersion = historyService.getNextVersion(currentVersion);
    }

    function displayHistoryModal() {
      $uibModal.open({
        animation: true,
        templateUrl: 'app/layout/modal/history/historyModal.view.html',
        controller: 'historyModalController',
        controllerAs: 'historyModal',
        size: 'lg',
        windowClass: 'modal-history-window',
        resolve: {
          model: function () {
            return vm.model;
          },
          viewMode: function () {
            return vm.viewMode;
          },
        }
      });
    }

    function patternsMayBeUpdated() {
      var result = false;
      if (vm.model) {
        var patternsToAcceptLeft =
          vm.model.patternsToAccept - vm.model.acceptedPatterns;
        result = patternsToAcceptLeft > 0;
      }
      if (vm.usesCrossSuitePattern) {
        result = false;
      }
      return result;
    }

    function patternsMarkedForUpdateMayBeReverted() {
      var result = false;
      if (vm.model) {
        result =
          vm.model.acceptedPatterns > 0 &&
          vm.model.acceptedPatterns <= vm.model.patternsToAccept;
      }
      if (vm.usesCrossSuitePattern) {
        result = false;
      }
      return result;
    }

    function displayAccessibilityModal() {
      $uibModal.open({
        animation: true,
        templateUrl: 'app/layout/modal/accessibility/accessibilityModal.view.html',
        controller: 'accessibilityModalController',
        controllerAs: 'accessibilityModal',
        resolve: {
          model: function () {
            return vm.model;
          }
        }
      });
    }

    function displayCommentModal() {
      $uibModal.open({
        animation: true,
        templateUrl: 'app/layout/modal/note/noteModal.view.html',
        controller: 'noteModalController',
        controllerAs: 'noteModal',
        resolve: {
          model: function () {
            return vm.model;
          },
          viewMode: function () {
            return vm.viewMode;
          },
          notesService: function () {
            return notesService;
          }
        }
      });
    }

    function scrollSidepanel(findParentGroup) {
      var $currentElement = $('a.test-name.is-active, a.test-url.is-active');
      var $reportGroup = $currentElement.closest('.aside-report.is-visible');
      var $parentElement = $reportGroup.find('.test-name');

      if (findParentGroup) {
        $parentElement.click();
        performScroll($parentElement);
        $reportGroup.addClass('is-expanded');
      } else {
        $reportGroup.addClass('is-expanded');
        performScroll($currentElement);
      }
    }

    function rerunTest() {
      var testName = vm.model.name;
      if (vm.model.testGroupName) {
        testName = vm.model.testGroupName;
      }
      rerunService.rerunTest(testName);
    }

    function rerunSuite() {
      rerunService.rerunSuite();
    }

    function rerunURL(testUrl) {
      var testName = vm.model.name;
      if (vm.model.testGroupName) {
        testName = vm.model.testGroupName;
      }
      rerunService.rerunURL(testName, testUrl);
    }

    function performScroll(element) {
      element[0].scrollIntoView({
        behavior: 'smooth',
        block: 'center',
        inline: 'nearest'
      });
    }

    /***************************************
     ***********  SUITE VIEW PART  *********
     ***************************************/
    function setupSuiteToolbarModel() {
      if (localStorage.getItem('currentRerunEndpointUrl')) {
        vm.checkRerunStatus(localStorage.getItem('currentRerunEndpointUrl'));
      }
      vm.model = metadataAccessService.getSuite();
      vm.updatePatterns = function () {
        patternsService.updateSuite();
      };
      vm.revertAcceptedPatterns = function () {
        patternsService.revertSuite();
      };
    }

    /***************************************
     ***********  TEST VIEW PART  *********
     ***************************************/
    function setupTestToolbarModel() {
      if (localStorage.getItem('currentRerunEndpointUrl')) {
        vm.checkRerunStatus(localStorage.getItem('currentRerunEndpointUrl'));
      }
      var testName = $stateParams.test;
      vm.model = metadataAccessService.getTest(testName);
      vm.updatePatterns = function () {
        patternsService.updateTest(vm.model.name, true);
      };
      vm.revertAcceptedPatterns = function () {
        patternsService.revertTest(vm.model.name, true);
      };
    }

    /***************************************
     ***********  URL VIEW PART  *********
     ***************************************/
    function setupUrlToolbarModel() {
      if (localStorage.getItem('currentRerunEndpointUrl')) {
        vm.checkRerunStatus(localStorage.getItem('currentRerunEndpointUrl'));
      }
      var testName = $stateParams.test;
      vm.model = metadataAccessService.getUrl($stateParams.test,
        $stateParams.url);
      vm.model.testGroupName = metadataAccessService.getTest(testName).name;
      vm.updatePatterns = function () {
        patternsService.updateUrl($stateParams.test, $stateParams.url, true);
      };
      vm.revertAcceptedPatterns = function () {
        patternsService.revertUrl($stateParams.test, $stateParams.url, true);
      };
    }

  }
});