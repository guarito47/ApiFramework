Feature: Validating Place API's
@AddPlace @Regression
  Scenario Outline: Verify if place is beign successfully added using AddPlaceAPI
    Given Add place payload with "<name>" "<language>" "<address>"
    When user calls "AddPlaceAPI" with "POST" http request
    Then the api call is success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place_id created maps to "<name>" using "GetPlaceAPI"

    Examples:
    |name|language|address|
#    |  bit scz  |English |tiluchis 2024 |
#   to work with hashMap excel driven use
   | addPlace  |spanish |sucre 933     |

@DeletePlace @Regression
    Scenario: Verify if delete place functionality is working
      Given DeletePlace Payload
      When user calls "DeletePlaceAPI" with "POST" http request
      Then the api call is success with status code 200
      And "status" in response body is "OK"
