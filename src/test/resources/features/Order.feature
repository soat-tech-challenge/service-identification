Feature: Order

  Scenario: Identify when near totem
    Given User wants to identify with national id "69805914011"
    When Send the request with valid data
    Then User identified

  Scenario: Identified user want's to create and order
    Given User selected product 2 unit of product 1
    When User checkout
    Then Order created

  Scenario: Identified user with created order wants to pay
    Given User wants to pay order 5 with QR Code
    When Initialize payment
    Then QR Code generated

  Scenario: Identified user with payment initialized wants to finish it
    Given User paid initialized payment order 5
    When Finish payment
    Then Order should be received

  Scenario: Kitchen staff wants to advance status of order to PREPARING
    Given Staff started preparing order 5
    When Update order status
    Then Order status should be updated

  Scenario: Kitchen staff wants to advance status of order to READY
    Given Staff finished preparing order 5
    When Update order status
    Then Order status should be updated

  Scenario: Kitchen staff wants to advance status of order to FINISHED
    Given Staff delivered order 5
    When Update order status
    Then Order status should be updated