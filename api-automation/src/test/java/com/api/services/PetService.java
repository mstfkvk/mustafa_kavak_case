package com.api.services;

import java.io.File;
import java.util.List;

import com.api.config.SpecBuilder;
import com.api.models.Pet;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class PetService {
    /**
     * given()           // Request yapılandırması
    .spec()       
    .body()       // JSON/Object
    .queryParam() // URL query parametreleri (?key=value)
    .pathParam()  // URL path parametreleri (/{id})
    .header()     
    .cookie()     
    .auth()       
    .contentType(ContentType.JSON)
    
    .when()           // İsteğin yapıldığı nokta
    .get(url)
    .post(url
    .request(Method.GET, url)  // Dinamik method kullanımı
    
    .then()           // Response doğrulama
    .spec()       
    .statusCode(200)
    .body("field", equalTo("value"))
    .header("Content-Type", containsString("json"))
    .time(lessThan(3000L))     // Response time assertion
    
    .extract()        // Response'ten veri çıkarma
    .as(MyClass.class)         // JSON → Java object (Jackson/Gson)
    .path("data.id")           // JSONPath ile alan çekme
    .header("Location")        // Header değeri çekme
    .response()                // Ham Response objesi
    .statusCode()              // int olarak status code
    .body().asString()         // String body
    .jsonPath()                // JsonPath objesi döner
     */

    private static final String PET_ENDPOINT = "/pet";

    private RequestSpecification getRequestSpec() {
        return SpecBuilder.requestSpec();
    }

    /**
     * Yeni Pet Oluştur (CREATE)
     */
    public Pet createPet(Pet pet, int code) {
        return given()
                .spec(getRequestSpec())
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .spec(SpecBuilder.responseSpec())
                .statusCode(code)
                .extract()
                .as(Pet.class);
    }

    // Pet oluşturma Response dönen versiyon
    public Response createPetResponse(Pet pet) {
        return given()
                .spec(getRequestSpec())
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    /**
     * ID ile Pet bulma
     */
    public Pet getPetById(Long id) {
        return given()
                .spec(getRequestSpec())
                .when()
                .get(PET_ENDPOINT + "/{id}", id)
                .then()
                .spec(SpecBuilder.responseSpec())
                .extract()
                .as(Pet.class);
    }

    /**
     * response olrak döner
     */
    public Response getPetByIdResponse(Long id) {
        return given()
                .spec(getRequestSpec())
                .when()
                .get(PET_ENDPOINT + "/{id}", id)
                .then()
                .extract()
                .response();
    }

    // String ID ile pet getirme (invalid format için)
    public Response getPetByStringIdResponse(String id) {
        return given()
                .spec(getRequestSpec())
                .when()
                .get(PET_ENDPOINT + "/" + id)
                .then()
                .extract()
                .response();
    }

    // Status araması için Response dönen versiyon
    public Response getPetsByStatusResponse(String status) {
        return given()
                .spec(getRequestSpec())
                .queryParam("status", status)
                .when()
                .get(PET_ENDPOINT + "/findByStatus")
                .then()
                .extract()
                .response();
    }

    /**
     * status ile petleri bulma
     */
    public List<Pet> getPetsByStatus(String status) {
        return given()
                .spec(getRequestSpec())
                .queryParam("status", status)
                .when()
                .get(PET_ENDPOINT + "/findByStatus")
                .then()
                .spec(SpecBuilder.responseSpec())
                .extract()
                .as(new TypeRef<List<Pet>>() {
                });
    }

    /**
     * resim yükleme
     */
    public Response uploadImage(Long id, String path) {
        return given()
                .spec(getRequestSpec())
                .contentType("multipart/form-data")
                .multiPart("file", new File(path)) // ✅ gerçek dosya
                .when()
                .post(PET_ENDPOINT + "/{id}/uploadImage", id)
                .then()
                .spec(SpecBuilder.responseSpec())
                .extract()
                .response();
    }

    /**
     * Pet Güncelle (UPDATE)
     */
    public Pet updatePet(Pet pet) {
        return given()
                .spec(getRequestSpec())
                .body(pet)
                .when()
                .put(PET_ENDPOINT)
                .then()
                .spec(SpecBuilder.responseSpec())
                .extract()
                .as(Pet.class);
    }

    /**
    * Pet Güncelle response dönecek 
    */
    public Response updatePetResponse(Pet pet) {
        return given()
                .spec(getRequestSpec())
                .body(pet)
                .when()
                .put(PET_ENDPOINT)
                .then()
                .spec(SpecBuilder.responseSpec())
                .extract()
                .response();
    }

    /**
    * Update pet form data ile
    */
    public Response updatePetWithFormData(Long id, String name, String status) {
        return given()
                .spec(getRequestSpec())
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", name)
                .formParam("status", status)
                .when()
                .post(PET_ENDPOINT + "/{id}", id)
                .then()
                .extract()
                .response();
    }

    /**
     * ID ile pet'i sil
     */
    public Response deletePet(Long id) {
        return given()
                .spec(getRequestSpec())
                .when()
                .delete(PET_ENDPOINT + "/{id}", id)
                .then()
                .extract()
                .response();
    }

    /**
     * Belirli bir status'a sahip petleri getirir.
     */
    public Pet[] findPetsByStatus(String status) {
        return given()
                .spec(getRequestSpec())
                .queryParam("status", status)
                .when()
                .get(PET_ENDPOINT + "/findByStatus")
                .then()
                .spec(SpecBuilder.responseSpec())
                .extract()
                .as(Pet[].class);
    }
}
