#### Source Comparator

Source Comparator is responsible for comparing collected page source with pattern.

Can be rebased from report.

Module name: **source**

Resource name: source

##### Parameters

| Parameter | Value | Description | Mandatory |
| --------- | ----- | ----------- | --------- |
| `compareType` | content | Compare only text inside HTML nodes. Ignore formatting, tag names and attributes. |  no<br/> If `compareType` is not provided default `all` value is taken. |
|  | markup | Compare only HTML markup and attributes. Ignore text inside HTML tags, formatting and white-spaces. Remove empty lines. |  |
|  | allFormatted | Compare full source with formatting and white-spaces ignored. Remove empty lines. |  |
|  | all | Compare all source (default). |  |

##### Example Usage

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<suite name="test-suite" company="cognifide" project="project">
    <test name="source-compare-test">
        <collect>
            ...
            <source />
            ...
        </collect>
        <compare>
            ...
            <source comparator="source" compareType="markup" />
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

| ! Important information |
|:----------------------- |
| [Extract Element Data Filter](ExtractElementDataFilter.md), [Remove Lines Data Filter](RemoveLinesDataFilter.md), [Remove Nodes Data Filter](RemoveNodesDataFilter.md) and [Remove Regexp Data Filter](RemoveRegexDataFilter.md) can be applied to collected source before comparison to modify source data that is to be compared. |
