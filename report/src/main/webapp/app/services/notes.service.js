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
define(['angularAMD', 'metadataService'], function (angularAMD) {
  'use strict';
  angularAMD.factory('notesService', NotesService);

  /**
   * Service responsible adding and removing notes from metadata.
   */
  function NotesService(metadataService, viewModeService) {
    var service = {
      updateNote: updateNote,
      deleteNote: deleteNote,
      unsavedNotesExist: unsavedNotesExist
    };

    return service;

    function updateNote(vm) {
      if (vm.viewMode === viewModeService.COMPARATOR) {
        vm.model.comparator.comment = vm.noteText;
      } else {
        vm.model.comment = vm.noteText;
      }
      updateUnsavedNotesFlag();
      metadataService.saveChangesLocally();
      metadataService.notifyMetadataChanged();
    }

    function deleteNote(object) {
      var text = object.comment;
      if (object.comment) {
        delete object.comment;
      } else {
        text = object.comparator.comment;
        delete object.comparator.comment;
      }
      updateUnsavedNotesFlag();
      metadataService.saveChangesLocally();
      metadataService.notifyMetadataChanged();
      return text;
    }

    function unsavedNotesExist() {
      var suite = metadataService.getSuite();
      return suite.unsavedNotes === true;
    }

    /***************************************
     ***********  Private methods  *********
     ***************************************/

    function updateUnsavedNotesFlag() {
      var suite = metadataService.getSuite();
      if (!suite.unsavedNotes) {
        suite.unsavedNotes = true;
      }
    }

  }
});
