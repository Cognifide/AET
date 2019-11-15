# AET Upgrade Notes

If you are upgrading AET from the previous version, here are notes that will help you do all 
necessary configuration changes that were introduced in comparison to previously released AET version.

You may see all changes in the [Changelog](https://github.com/Cognifide/aet/blob/master/CHANGELOG.md).

## Unreleased

## Version 3.3.1
Changes:
* All suites have to be valid xmls. This is mostly related to URL's whch can have special characters like '&'. Such characters have to be escaped.
This changes requires to update all suites and escape all URL's inside. 
For example - given suite line:
<url href="https://en.wikipedia.org/wiki/Main_Page?a=b&c=d"/>
has to be updated into:
<url href="https://en.wikipedia.org/wiki/Main_Page?a=b&amp;c=d"/>
Related issue: [#441](https://github.com/Cognifide/aet/issues/441)

## Version 3.2.1
Changes:
* Counting line and column number of an accessibility issue occurrence in [[Accessibility Collector|AccessibilityCollector]] has been improved. 
Columns are now indexed starting from 1. Bug with two identical issues on a single page yielding the same line and column number has been fixed.
When upgrading from previous AET version you may need to increase `column` values by 1. Pay special attention to issues that occur more than once on a single page. In new AET version they will be listed twice, each with its exact column and line number.
Related issue: [#438](https://github.com/Cognifide/aet/issues/438)

## Version 3.2.0

### Users

### Admins

#### [PR-451](https://github.com/Cognifide/aet/pull/451) Collectors and comparators configured by single config number

Remove all `CollectorMessageListenerImpl` and `ComparatorMessageListenerImpl` config files.
Create `com.cognifide.aet.worker.listeners.WorkersListenersService.cfg` and configure proper 
number of collectors and comparators there.
You can find example config file [here](https://github.com/Cognifide/aet/blob/master/osgi-dependencies/configs/src/main/resources/com.cognifide.aet.worker.listeners.WorkersListenersService.cfg).


## Version 3.0.0

### Users

#### Suite update guide

Changes related to Chrome web driver migration (from old Firefox version):
* The [[Screen Collector|ScreenCollector]] now can only takes a screenshot of whole viewport size set by the [[Resolution Modifier|ResolutionModifier]]. 
If you want to take a screenshot of entire page, you should either skip the height parameter of resolution modifier 
(to let it be computed by JavaScript), or set it to a value which will cover the whole page.
If you want to take a partial screenshot (specified by xpath/css selector), then the whole element must be visible in the current viewport. 
Please see latest [[Screen Collector|ScreenCollector]] and [[Resolution Modifier|ResolutionModifier]] documentation in the wiki for details.
* The [[Open|Open]] may take more time to complete now, because it waits for all images to be loaded.
[[Wait For Image Completion Modifier|WaitForImageCompletionModifier]] is no longer needed, unless you want to wait for an image which is loaded asynchronously (e.g. by an AJAX call)
* [[Resolution Modifier|ResolutionModifier]] width accuracy - in previous AET versions, 
the width of collected screenshot could be different that the width set in the `<resolution>` tag because of the Firefox's scrollbar 
(see "Notes" section in [[Resolution Modifier|ResolutionModifier]] wiki). This issue doesn't occur when using AET 3.0 with Chrome browser - make
sure to adjust the resolution `width` value when updating your suite from previous AET versions.
##### Known issues

* [#357](https://github.com/Cognifide/aet/issues/357) - see Known issues section in [[Resolution Modifier|ResolutionModifier]] wiki
for possible workarounds.

#### `aet-maven-plugin` marked as deprecated
That means it will be no longer supported after release of this version and expect it will be removed soon.
Please use [[client script|ClientScripts]] instead or simply communicate with AET Web API to schedule your suite.


### Admins

#### [PR-326](https://github.com/Cognifide/aet/pull/326) Upgrade OSGI annotations to 6.0.0 version

With the OSGI annotations update to 6.0.0 version we had to change a little bit variable names. Currently, your config could have old names and you have to update them. Please follow instruction below:

Modify following OSGi config files (by default they should be located in `/opt/aet/karaf/aet_configs/current`):

|File name|Way to change variable names|
|---|---|
|`com.cognifide.aet.rest.helpers.ReportConfigurationManager.cfg`|`report-domain -> reportDomain`|
|`com.cognifide.aet.runner.MessagesManager.cfg`|`jxm-url -> jxmUrl`|
|`com.cognifide.aet.vs.mongodb.MongoDBClient.cfg`|`MongoURI -> mongoURI` <br> `AllowAutoCreate -> allowAutoCreate`|

For example in `com.cognifide.aet.vs.mongodb.MongoDBClient.cfg`:
```
MongoURI=mongodb://localhost
AllowAutoCreate=true
```

Please replace it by:
```
mongoURI=mongodb://localhost
allowAutoCreate=true
```

#### BrowserMob Proxy server connection  
  The address of BrowserMob Proxy server is set to `localhost:8080` by default (in Karaf config) - 
  it's used by workers to connect to the proxy server. Previously, when tests were being run on local Firefox instances
  running on the same machine as the proxy server, there was no need to change the default config.
  Currently, when tests are executed on Selenium Grid nodes on different machines, the address of proxy 
  server must be configured  to an IP/host name which is accessible for the nodes. 
  It can be configured in `com.cognifide.aet.proxy.RestProxyManager.cfg` file - default config for Vagrant is:
  ```
  server=192.168.123.100
  port=8080
  ```
  
