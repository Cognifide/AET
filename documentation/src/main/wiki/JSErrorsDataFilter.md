#### JS Errors Data Filter

Js Errors Data Filter filters JS Errors Collector result - it removes matched javascript errors from reports.  
This filter can be only applied to `js-errors` comparator tag in test case.  
When more than one parameter is provided then only fully matched errors are filtered.  
If some XML-specific charactes (e.g. `&`) are in parameter's value, then they must be escaped.

Module name: **js-errors-filter**

Resource name: js-errors

##### Parameters

| Parameter | Value | Description | Mandatory |
| --------- | ----- | ----------- | --------- |
|`error`|string error|Exact error message|At least one of parameter is required|
|`source`|string file name|Source file name (full path including `http://`) in which error occurred|
|`errorPattern` | pattern error text | Regular expression that matches message text of issue to be filter out|At least one parameter is required|
|`sourcePattern` | pattern file name | Regular expression that should match file name to be filter out (full path including `http://`) in which error occurred|
|`line`  integer line number|Line number in file in which error occurred|
*Note:
- 'error' param will be overridden by 'errorPattern' if set
- 'source' param will be overridden by 'sourcePattern' if set


##### Example Usage

In this sample exact match of js error from file  "[http://w.iplsc.com/external/jquery/jquery-1.8.3.js](http://w.iplsc.com/external/jquery/jquery-1.8.3.js)", line 2 with message "Error: Syntax error, unrecognized expression: .iwa_block=pasek-ding" will be totally ignored (not included in report)

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<suite name="test-suite" company="cognifide" project="project">
    <test name="js-errors-filter-test">
        <collect>
            ...
            <open/>
            ...
            <js-errors/>
            ...
        </collect>
        <compare>
            ...
            <js-errors>
                <js-errors-filter
                    error="Error: Syntax error, unrecognized expression: .iwa_block=pasek-ding"
                    line="2"
                    source="http://w.iplsc.com/external/jquery/jquery-1.8.3.js" />
                <js-errors-filter
                    errorPattern="^.*Syntax error, unrecognized expression.*$"
                    sourcePattern="^.*jquery-1.8.3.js$" />                    
            </js-errors>
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

There can be more than one `js-errors-filter` tag in `js-errors` comparator eg:

```xml
<js-errors>
    <js-errors-filter error="Error: Syntax error, unrecognized expression: .iwa_block=pasek-ding" />
    <js-errors-filter source="http://w.iplsc.com/external/jquery/jquery-1.8.3.js"
                      line="2" />
</js-errors>
```
