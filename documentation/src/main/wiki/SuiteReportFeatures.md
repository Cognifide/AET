### Features
#### Accepting/reverting patterns
There is possibility to accept failed test on each level (suite, test and URL) for the test cases in which the pattern is used (for now acceptable cases are: cookie compare, layout for screen and source "Source"). It is possible to revert certain acceptation, but for now only if changes are not saved. Accepting on suite level makes all acceptable test accepted, on test level it provides acceptation of each URL in certain test case. The lowest level is accepting certain URLs.

Accepting pattern example:

![Test's acceptation](assets/suiteReport/test_accepting.png)

Reverting pattern example:

![Test's reverting](assets/suiteReport/test_reverting.png)

#### Notes creation
There is possibility to add notes to each level of the report. Notes are useful to give some information 
about test case e.g. why failed test was accepted.

![Note example](assets/suiteReport/note_example.png)

#### Filtering test results
Every test case has one of the following states:

1. passed — if the comparator doesn't find any change, i.e. validation passes,
2. passed with warnings — if there are some warnings, but they are not very important,
3. failed — if the comparator detects some changes or some validation rules are broken,
4. accepted — if failed test was accepted.
The state of the test case is propagated to the URL then to the test and to the test suite. It is possible to accept all test cases for a given URL or in a current test suite.

Tests and URLs may be filtered by:

* text included in the test name or URL,
* test status.

Filtering by status works as an alternative: if one selects passed and failed tests both should be visible.

![Filter example](assets/suiteReport/filter-example.png)

#### Searching for specific test/URL
It is possible to search tests and URLs by the query. Searched fields are the URL and test name.

![Search example](assets/suiteReport/search-example.png)

#### Scrolling Side Panel to currently opened test/URL
It is possible to find currently opened test/URL in Side Panel by using the crosshair buttons. 
Moreover, every URL has information to which test group it belongs.

![Find example](assets/suiteReport/find-in-sidepanel-example.png)

#### Navigation via keyboard shortcuts
* press **q** to expand/collapse all items
* press **e** to expand/collapse all error items
* use **[** / **]** to navigate between test's urls
* press **m** to show/hide layout mask (when available)
* press **←** / **→** to navigate between url's tabs