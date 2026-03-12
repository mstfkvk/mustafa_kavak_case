# Pet Store API Test Automation

API test automation project for [Swagger Petstore](https://petstore.swagger.io/) using REST Assured and JUnit 5.

---

## Tech Stack

| Tool | Purpose |
|---|---|
| Java 17 | Programming language |
| REST Assured 5.3.0 | API testing |
| JUnit 5 | Test framework |
| AssertJ | Assertions |
| Jackson Databind | JSON serialization |
| Lombok | Boilerplate reduction |
| JavaFaker | Test data generation |
| Logback | Logging |
| Maven | Build & dependency management |

---

## Project Structure

```
src/
  test/
    java/
      com/api/
        models/          # Pet, Category, Tag
        services/        # PetService (API calls)
        tests/           # PetCrudTest, PetNegativeTest
        utils/           # SpecBuilder (request/response specs)
    resources/
      cat.png            # Test image for upload
```

---

## Test Cases

### PetCrudTest
| Test | Description |
|---|---|
| `shouldPerformFullPetLifecycle` | Create → FindByStatus → UploadImage → Update → FormUpdate → Delete |
| `shouldReturn404WhenDeletingAlreadyDeletedPet` | Delete the same pet twice, expect 404 on second attempt |
| `shouldReturn404WhenUpdatingNonExistingPet` | Update a pet that does not exist |

### PetNegativeTest
| Test | Description |
|---|---|
| `shouldReturn400WhenSearchingWithInvalidStatus` | Search with invalid status value *(skipped: API bug)* |
| `shouldReturn400WhenCreatingPetWithoutName` | Create pet without name field *(skipped: API bug)* |
| `shouldReturn404WhenFetchingPetWithInvalidIdFormat` | Fetch pet with string ID like "abc" |
| `shouldReturn200WhenCreatingPetWithDuplicateId` | Create two pets with the same ID, API overwrites |

---

## Running Tests

```bash
# Run all tests
mvn clean test

# Run with detailed logging
mvn clean test -Ddetailed.logging=true
```

---

## Notes

- Three tests are marked as **SKIPPED** due to known API bugs in the public Petstore:
  - Invalid status search returns `200` instead of `400`
  - Creating a pet without a name returns `200` instead of `400`
  - Creating a pet with duplicate id returns `200` instead of `400`
- Tests use `assumeFalse` to skip gracefully instead of failing on known bugs
- All test data is generated dynamically via JavaFaker, no hardcoded values