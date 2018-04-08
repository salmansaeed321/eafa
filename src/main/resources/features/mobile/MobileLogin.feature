Feature: User should be able to login from mobile application

  @MobileLogin
  Scenario:
    Given user opens "mobile" application
    And user enters "user@domain.com" as "login.username-id"
    And user enters "12345" as "login.password-id"
    And user clicks on "login.submit-xpath"