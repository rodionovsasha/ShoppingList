Feature: Item API

  Scenario: I should see one item
    When I get item json with id = 1
    Then Response should contain id = 1
    And Response should contain name = Item1
