package com.example.checkinrequestMS.PlaceAPI.domain;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.context.annotation.Primary;

import java.util.Objects;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String placeName;
    private String addressName;
    private String roadAddressName;
    private String categoryName;
    private String phone;
    private String placeUrl;
    private double x;
    private double y;

    public void setValues(JsonNode document){
        this.setPlaceName(document.get("place_name").asText());
        this.setAddressName(document.get("address_name").asText());
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
