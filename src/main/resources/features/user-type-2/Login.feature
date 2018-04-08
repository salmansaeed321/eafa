#Author: salmansaeed321@hotmail.com
@Login @Regression
Feature: Register users should be able to login
  by providing correct credentials only

  Background: 
    Given user opens "web" application
    And user clicks on "landing.login-btn-xpath"

  @LoginNow
  Scenario Outline: Login test with Positive scenario
    And user enters "<username>" as "login.username-id"
    And user enters "<password>" as "login.password-id"
    And user clicks on "login.submit"
    Then verify "<message>" text is displayed at "<locator>"
    And user quits the browser

    Examples: 
      | username       | password    | message | location           |
      | salmansaeed321 | password123 | Logout  | landing.logout-btn |

  @Login @Negative
  Scenario Outline: Login test with Negative scenario
    And user enters "<username>" as "login.username-id"
    And user enters "<password>" as "login.password-id"
    And user clicks on "login.submit-linktext"
    Then verify "<message>" text is displayed at "<locator>"
    And user quits the browser

    Examples: 
      | username       | password    | message                                | location                 |
      | salmansaeed321 | password    | Please enter correct credentials       | validation.message-xpath |
      | salmansaeed    | password123 | Please first register and set password | validation.message-xpath |
      |                | password123 | Please enter username.                 | user-name.message-xpath  |
      | salmansaeed321 |             | Please enter password.                 | password.message-xpath   |
      |                |             | Please enter username and password.    | user-name.message-xpath  |
