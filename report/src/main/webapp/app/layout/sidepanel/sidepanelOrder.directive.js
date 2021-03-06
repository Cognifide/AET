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
define(['angularAMD'], function (angularAMD) {
  'use strict';
  angularAMD.directive('aetSidepanelOrder',
      ['$rootScope', '$timeout', SidepanelOrderDirective]);

  function SidepanelOrderDirective($rootScope, $timeout) {
    return {
      restrict: 'AE',
      link: init,
      controller: function ($scope) {
        $scope.changeSortingOrder = changeSortingOrder;
      }
    };

    function init(scope, $element) {
      updateOrdering('name', 'Sort by name');
      $element.popover({
        placement: 'bottom',
        trigger: 'click',
        content: function () {
          return $element.find('.dropdown-menu').html();
        },
        html: true
      }).parent().on('click', 'button', function (event) {
        onOrderSelected(event, $element);
      });
    }

    function onOrderSelected(event, $element) {
      (($element.popover('hide').data('bs.popover')||{}).inState||{}).click = false;
      var orderingAttribute = $(event.target).data('ordering-attribute');
      var label = $(event.target).text();
      updateOrdering(orderingAttribute, label);
    }

    function updateOrdering(orderingAttribute, labelText){
      $timeout(function() {
        $rootScope.orderAttribute = orderingAttribute;
        $rootScope.orderLabel = labelText;
      });
    }

    function changeSortingOrder() {
      $timeout(function() {
        $rootScope.orderIsReverse = !$rootScope.orderIsReverse;
      });
    }
  }
});
