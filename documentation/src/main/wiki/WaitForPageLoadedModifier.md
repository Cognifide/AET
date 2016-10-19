#### Wait For Page Loaded Modifier

Wait For Page Loaded Modifier waits until the page is loaded (all DOM elements are loaded - this does not wait for dynamically loaded elements by e.g. JavaScript) or a fixed amount of time is up. The idea of waiting for the page is counting the amount of elements [by `findElements(By.xpath("//*"))`] on the current page state in a loop. If the number of elements has increased since the last checkout, continue the loop (or break if the timeout is reached). Else if the number of elements is still, assume the page is loaded and finish waiting.

Module name: **wait-for-page-loaded**

##### Parameters

No parameters.


| ! Important information |
|:----------------------- |
| Timeout for waiting is 10000 milliseconds.<br/><br/> The Page is checked every 1000 milliseconds. |

##### Sample Usage

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<suite name="test-suite" company="cognifide" project="project">
    <test name="wait-for-page-loaded-test">
        <collect>
            ...
            <open />
            ...
            <wait-for-page-loaded />
            ...
        </collect>
        <compare>
            ...
        </compare>
        <urls>
        ...
        </urls>
    </test>
    ...
    <reports>
        ...
    </reports>
</suite>
```
