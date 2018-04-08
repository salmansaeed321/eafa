#Author: salmansaeed321@hotmail.com
@NewUserPaymentFlow @Smoke
Feature: User should be able to procees with payment after signup

  Background: 
    Given user opens "web" application
    And user clicks on "landing.signup-btn-xpath"
    And user enters "1112223334" as "signup.phonenumber-id"
    And user clicks on a checkbox "signup.tc-checkbox-id"
    And user clicks on "signup.register-btn-xpath"

  Scenario Outline: New user payment flow
    And user enters "<sendmoney>" as "sendmoney.sender-amount-xpath"
    And user selects radio button "sendmoney.cashout-xpath"
    And user clicks on "sendmoney.proceed-transfer-xpath"
    And user selects "<profiletitle>" from "remitterprofile.title-xpath" dropdown
    And user enters "<profilefirstname>" as "remitterprofile.first-name-xpath"
    And user enters "<profilelastname>" as "remitterprofile.last-name-xpath"
    And user enters "<day>" as "remitterprofile.dob-date-xpath"
    And user selects "<month>" from "remitterprofile.dob-month-xpath" dropdown
    And user enters "<year>" as "remitterprofile.dob-year-xpath"
    And user enters "<profileemail>" as "remitterprofile.email-xpath"
    And user enters "<profileaddress>" as "remitterprofile.address-xpath"
    And user enters "<profilecity>" as "remitterprofile.city-xpath"
    And user enters "<profilepostcode>" as "remitterprofile.postcode-xpath"
    And user enters "<newpass>" as "remitterprofile.set-password-xpath"
    And user enters "<confirmpass>" as "remitterprofile.confirm-password-xpath"
    And user clicks on "remitterprofile.create-profile-btn-xpath"
    And user enters "<beneficiaryname>" as "beneficiary.name-xpath"
    And user enters "<beneficiaryPhone>" as "beneficiary.phone-number-xpath"
    And user enters "<beneficiaryaddress>" as "beneficiary.address-xpath"
    And user enters "<benificairycity>" as "beneficiary.city-xpath"
    And user selects "<selectbank>" from "beneficiary.select-bank-xpath" dropdown
    And user selects "<selectreasonforsending>" from "beneficiary.select-reason-xpath" dropdown
    And user clicks on "beneficiary.next-btn-xpath"
    And user clicks on "beneficiary.save-popup-yes-xpath"
    Then verify "CONFIRM TRANSFER" text is displayed at "confirmtransfer.page-header-xpath"
    And user clicks on "confirmtransfer.confirm-btn-xpath"
    And user enters "<cardnumber>" as "payment.card-number-xpath"
    And user selects "<month1>" from "payment.select-expiry-month-xpath" dropdown
    And user selects "<year1>" from "payment.select-expiry-year-xpath" dropdown
    And user enters "<CVV>" as "payment.cvv-xpath"
    And user clicks on "payment.pay-btn-xpath"
    And user clicks on "payment.save-popup-no-xpath"
    Then verify "PAYMENT SUCCESSFUL" text is displayed at "paymentsuccess.page-header-xpath"
    And user quits the browser

    Examples: 
      | countrycode | phonenumber | sendmoney | profiletitle | profilefirstname | profilelastname | day | month | year | profileemail  | profileaddress | profilecity | profilepostcode | newpass     | confirmpass | beneficiaryname | beneficiaryPhone | beneficiaryaddress | benificairycity | selectbank          | selectreasonforsending | cardnumber       | month1 | year1 | CVV |
      | +44         |  1112223334 |        50 | Mr           | Mark             | Smith           |  19 | Feb   | 1991 | abc@gmail.com | 789 No 789     | Lahore      | TE456ST         | password123 | password123 | Adam Smith      |      03210000000 | 123 Gulberg        | Lahore          | United Bank Limited | Loan                   | 4111110000000211 |     04 |  2018 | 123 |
