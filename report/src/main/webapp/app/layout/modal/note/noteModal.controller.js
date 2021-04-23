/*
 * AET
 *
 * Copyright (C) 2021 Wunderman Thompson Technology
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
  angularAMD.controller('noteModalController', NoteModalController);

  function NoteModalController($scope, $uibModalInstance, model, viewMode, viewModeService,
      notesService, $stateParams) {

    var vm = this;
    init();

    /***************************************
     ***********  Private methods  *********
     ***************************************/

    function init() {
      vm.updateNote = updateNote;
      vm.cancelNote = cancelNote;
      vm.deleteNote = deleteNote;
      vm.viewMode = viewMode;
      vm.noteText = getComment(viewMode);
      vm.model = model;
    }

    function getComment(viewMode) {
      if (viewMode === viewModeService.COMPARATOR) {
        return model.comparator.comment ? model.comparator.comment : '';
      }
      else {
        return model.comment ? model.comment : '';
      }
    }

    function updateNote() {
      notesService.updateNote(vm);
      $uibModalInstance.close();
    }

    function cancelNote() {
      $uibModalInstance.close();
    }

    function deleteNote() {
      notesService.deleteNote(vm.model);
      $uibModalInstance.close();
    }
  }
});
