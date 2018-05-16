Feature: the strategy can be retrieved
  Scenario: client makes call to get /strategy
    When the client calls /strategy
    Then the client receives response status code of 200
    And the client receives response json includes1 place
    And the client receives response json includes2 routes
    And the client receives response json includes3 recommend