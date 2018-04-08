
locator-references or url-references must be provided in "OR.properties" file
each reference must have suffix as -xpath, -id, -linktext or css depending on the locator type
e.g. if we have an id of a button on landing page then the reference would be like: "landingpage.button-id"

- Below line must be written as first line on each scenario:
Given user opens "web/mobile" application
browser and url to open web is mentioned and can be updated in Config.properties file under src/main/resources folder
mobile configurations can also be updated in Cofig.properties file

- To click on a button or link:
When user clicks on "locator-reference"

- To provide text in any input field:
When user enters "Text to enter in any input field" as "locator-reference"

- To select any value from Radio buttons
And user selects radio button "locator-reference"

- To select any value from Dropdown:
And user selects "Value to select e.g. Pakistan" from "locator-reference" dropdown

- To verify any performed action e.g. user clicks on a button and popup appears
Then user verifies the action by "locator-reference"

- To verify exact text e.g. validation messages, success case that user lands on the screen and title is "Page Title" - exact match
Then verify "Text to verify" text is displayed at "locator-reference"

- To switch to displayed iframe e.g. user clicks on a button and iframe opens; then first switch to iframe and then perform other actions like click, input etc.
And user switches to iframe "locator-reference"

- To select a checkbox
And user clicks on a checkbox "locator-reference"

- To upload file against upload field - profile picture, document upload etc.
And user uploads "locator-reference" and selects file "File path on your system - can be kept in project also"

- To accept javascript alert:
And user accepts the alert

- To check spellings on any page that is open - can be executed on each page:
And user verifies page spellings

- To open any link in new tab - can also be used to open a new tab to perform some action there
And user opens a new tab by clicking "locator-reference"

- To switch to any tab in browser
And user switches to tab "tabIndex e.g. 0 or 1 ()"

- To open new url - can also be used in a new tab, after opening new tab navigate to xyz url and then perform actions like click, input etc. and then switch back to previous tab by above statement
And user navigates to "url-reference, url should be placed in OR.properties file"

- To get email verification link or OTP token from received email
And user gets "token/otp" against "email address" and "email password" with subject "email subject"

- To close browser:
And user quits the browser