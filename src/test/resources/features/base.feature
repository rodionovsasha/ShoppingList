Feature: All lists page
  Scenario: I should see All lists page
    When I open page with url: /
    Then I should see a page with title=Shopping List - Main page
    And I should see All lists header

  Scenario: I should see one list info and tools on the page
    When I open page with url: /
    Then I should see link with url=/itemsList?id=1 and text=Shopping list name
    And I should see link with url=/itemsList/edit?id=1 and text=Edit
    And I should see link with url=/itemsList/delete?id=1 and text=Delete
    And I should see link with href=/itemsList/add