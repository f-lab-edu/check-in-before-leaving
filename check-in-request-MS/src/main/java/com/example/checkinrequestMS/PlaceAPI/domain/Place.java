package com.example.checkinrequestMS.PlaceAPI.domain;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.context.annotation.Primary;

import java.util.Objects;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id", nullable = false)
    private Long id;
    @Column(unique = true)
    private String placeName;
    private String address;
    private String roadAddressName;
    private String categoryName;
    private String phone;
    private String placeUrl;
    private double x;
    private double y;

    public static Place createEmptyPlace(){
        return Place.builder().build();
    }
    public static Place createEmptyPlaceWithOnlyId(Long id){
        return Place.builder().id(id).build();
    }

    public void setValues(JsonNode document){
        this.setPlaceName(document.get("place_name").asText());
        this.setAddress(document.get("address_name").asText());
        this.setRoadAddressName(document.get("road_address_name").asText());
        this.setCategoryName(document.get("category_name").asText());
        this.setPhone(document.get("phone").asText());
        this.setPlaceUrl(document.get("place_url").asText());
        this.setX(document.get("x").asDouble());
        this.setY(document.get("y").asDouble());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Place)) return false;

        Place p = (Place) o;
        return Objects.equals(placeName, p.getPlaceName());
    }

}
