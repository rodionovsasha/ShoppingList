Feature: Items list API
  Scenario: I should see all lists
    When I read json array from url: /v1/api
    Then Response should contain JSON array:
    """
    [{"name":"Shopping list name","id":1,"items":[{"itemsList":1,"bought":false,"name":"Item1","comment":null,"id":1}]}]
    """
    And Response code should be 200

  Scenario: I should see one list
    When I read json object from url: /v1/api/itemsList/1
    Then Response should contain JSON object:
    """
   {"name":"Shopping list name","id":1,"items":[{"itemsList":1,"bought":false,"name":"Item1","comment":null,"id":1}]}
    """
    And Response code should be 200

  Scenario: I should create a new list
    When I send a POST request to /v1/api/itemsList with the following:
    """
    {"name":"List 1"}
    """
    Then Response code should be 201
    And Response should contain JSON object:
    """
    {"name":"List 1","id":0}
    """

  Scenario: I should not create a new list when name is empty
    When I send a POST request to /v1/api/itemsList with the following:
    """
    {"name":""}
    """
    Then Response code should be 400
    And Response should contain JSON object:
    """
    {"fieldErrors":[{"field":"name","message":"may not be empty"}]}
    """

  Scenario: I should update list
    When I read json object from url: /v1/api/itemsList/1
    And Response code should be 200
    When I send a PUT request to /v1/api/itemsList with the following:
    """
    {"id":1,"name":"updated list"}
    """
    Then Response should contain JSON object:
    """
    {"id":1,"name":"updated list"}
    """
    And Response code should be 200

  Scenario: I should not update list when name is empty
    When I read json object from url: /v1/api/itemsList/1
    And Response code should be 200
    When I send a PUT request to /v1/api/itemsList with the following:
    """
    {"id":1,"name":""}
    """
    And Response code should be 400
    And Response should contain JSON object:
    """
    {"fieldErrors":[{"field":"name","message":"may not be empty"}]}
    """

  Scenario: I should delete list
    When I read json object from url: /v1/api/itemsList/1
    And Response code should be 200
    When I send a DELETE request to /v1/api/itemsList/1
    And Response code should be 204

  Scenario: I should not delete list if list does not exist
    When I send a DELETE request to /v1/api/itemsList/100
    And Response code should be 404