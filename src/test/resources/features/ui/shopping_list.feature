Feature: One list page
  Scenario: I should see one list page
    When I open page with url: /
    Then I click on the link with text=Shopping list name
    And I should see a page with title=Shopping List - Shopping list name
    And I should see header with text=Shopping list name
    And I should see link with url=/item?id=1 and text=Item
    And I should see link with url=/item/bought?id=1&listId=1 and text=Bought
    And I should see link with url=/item/edit?id=1 and text=Edit
    And I should see link with url=/item/delete?id=1&listId=1 and text=Delete
    And I should see link with href=/item/add?listId=1
    And I should see link with href=/

  Scenario: I should create new list
    When I open page with url: /
    And I click on the link with href=/itemsList/add
    Then I should see header with text=Add new shopping list
    When I try to add new list with name=Shopping list
    Then I should see header with text=Shopping list

  Scenario: I should not create new list when name is empty
    When I open page with url: /
    And I click on the link with href=/itemsList/add
    Then I should see header with text=Add new shopping list
    When I try to add new list with name=
    Then I should see header with text=Add new shopping list
    And I should see error message=must not be empty

  Scenario: I should update list
    When I open page with url: /
    And I click on the link with text=Edit
    Then I should see header with text=Edit Shopping list name
    When I try to update list with new name=Edited name
    Then I should see header with text=Edited name

  Scenario: I should not update list when name is empty
    When I open page with url: /
    And I click on the link with text=Edit
    Then I should see header with text=Edit Edited name
    When I try to update list with new name=
    Then I should see header with text=Edit
    And I should see error message=must not be empty

  Scenario: I should delete list
    When I open page with url: /
    And I click on the link with text=Delete
    Then I should not see link with url=/itemList?id=1