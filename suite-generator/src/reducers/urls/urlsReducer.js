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
export default function (state = {}, action = null) {

  switch(action.type) {

    case "URL_ADDED": {
      const newURL = action.payload;
      const newState = [
        ...state,
        newURL
      ];
      return newState;
    }

    case "URL_REMOVED": {
      let newState = [...state];
      newState.forEach((url, index) => {
        if(url === action.payload) {
          newState = [
            ...newState.splice(0, index),
            ...newState.splice(index + 1)
          ]        
        }
      });
      return newState;
    }

    case "URL_LIST_CLEARED": {
      return [];
    }

    case "URLS_LOADED": {
      return action.payload;
    }

    default: {
      return state;
    }
  }
}