Feature: the strategy can be retrieved
  Scenario: client makes call to get /strategy
    When the client calls /strategy
    Then the client receives response status code of 200