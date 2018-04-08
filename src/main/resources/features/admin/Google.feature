#Author: salmansaeed321@hotmail.com
Feature: Title of your feature

  @Google
  Scenario: Title of your scenario
    Given user opens "web" application
    When user enters "PSL T20" as "google.search-input-xpath"
    And user clicks on "google.search-button-xpath"
    Then verify "PSL-T20.Com" text is displayed at "google.search-result-xpath"