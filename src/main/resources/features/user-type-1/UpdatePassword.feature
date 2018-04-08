@UpdatePassword @Regression @Possitive
Feature: Existing user should be able to
  update the existing password

  Background: 
    Given user opens "web" application
    And user clicks on "landing.login-btn-xpath"
    And user enters "salmansaeed321" as "login.username-xpath"
    And user enters "password123" as "login.password-xpath"
    And user clicks on "login.submit-id"

  Scenario Outline: Update existing user password
    And user clicks on "landing.dashboard-btn-xpath"
    And user clicks on "left-menu.updatepassword-xpath"
    And user enters "<newpass>" as "new-password-xpath"
    And user enters "<confirmpass>" as "new-confirm-password-xpath"
    And user clicks on "update-password-btn-xpath"
    And user clicks on "logout.username-linktext"
    And user clicks on "logout.lg-css"
    And user quits the browser

    Examples: 
      | username       | password    | newpass     | confirmpass |
      | salmansaeed321 | password123 | 321password | 321password |

  @UpdatePassword @Negative
  Scenario Outline: Negative flows of update password
    And user clicks on "landing.dashboard-btn-xpath"
    And user clicks on "left-menu.updatepassword-xpath"
    And user enters "<newpass>" as "new-password-xpath"
    And user enters "<confirmpass>" as "new-confirm-password-xpath"
    And user clicks on "update-password-btn-xpath"
    Then verify "<message>" text is displayed at "<locator>"
    And user quits the browser

    Examples: 
      | username       | password    | newpass     | confirmpass | message                       | locator                   |
      | salmansaeed321 | password123 |             |             | Please enter password.        | update-password.msg-xpath |
      | salmansaeed321 | password123 | password567 |             | Please confirm your password. | update-password.msg-xpath |
      | salmansaeed321 | password123 |             | password543 | Please enter password.        | update-password.msg-xpath |
      | salmansaeed321 | password123 | password11  | password023 | Passwords do not match.       | update-password.msg-xpath |
