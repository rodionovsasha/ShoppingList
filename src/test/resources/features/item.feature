Feature: One item page
  Scenario: I should see one item page
    When I open page with url: /item?id=1
    Then I should see a page with title=Shopping List - Item1
    And I should see header with text=Item1
    And I should see link with href=/itemList?id=1
    And I should see link with href=/
    And I should see link with href=/item/edit?id=1
    And I should see link with href=/item/delete?id=1