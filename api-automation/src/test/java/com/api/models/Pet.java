package com.api.models;

/**
 * 
 * LOMBOK ANNOTATIONS:
 * -------------------
 * @Data:           Otomatik getter/setter/toString/equals/hashCode
 * @Builder:        Fluent API ile nesne oluşturma
 * @NoArgsConstructor: Jackson deserialization için zorunlu
 * @AllArgsConstructor: Tüm alanlı constructor
 * 
 * @JsonIgnoreProperties(ignoreUnknown = true):
 * -------------------------------------------
 * API'den gelen tanımlanmamış alanları yok say.
 * Esneklik sağlar - API'ye yeni alanlar eklendiğinde testler kırılmaz.
 * 
 * --------------------------------------------
 *  {
 *   "id": 12345,
 *   "category": {"id": 1, "name": "Dogs"},
 *   "name": "Buddy",
 *   "photoUrls": ["https://example.com/photo1.jpg"],
 *   "tags": [{"id": 1, "name": "trained"}],
 *   "status": "available"
 * }
 * 
 * Builder pattern:
 * 
 * Pet pet = Pet.builder()
 *     .id(12345)
 *     .name("Buddy")
 *     .category(Category.builder()
 *         .id(1)
 *         .name("Dogs")
 *         .build())
 *     .photoUrls(List.of("https://example.com/photo.jpg"))
 *     .tags(List.of(
 *         Tag.builder().id(1).name("trained").build()
 *     ))
 *     .status("available")
 *     .build();
 * 
 * 
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pet {
  
    private Long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status; // available, sold, pending
}
