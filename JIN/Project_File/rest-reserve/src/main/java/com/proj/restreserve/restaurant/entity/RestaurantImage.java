package com.proj.restreserve.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.domain.Persistable;

@Entity
@Data
@Table(name = "restaurantimage")
public class RestaurantImage implements Persistable<String> {
    @Id
    private String restaurantimageid;

    private String imagelink;

    @ManyToOne
    @JoinColumn(name = "restaurantid")
    @JsonBackReference
    private Restaurant restaurant;

    @Override
    public String getId() {
        return restaurantimageid;
    }
    @Override
    public boolean isNew() {
        return true; //UUID를 직접 set하여 .save()사용시 merge를 실행하여 id값이 변경되는 문제와 select문이 나오는걸 막으려 사용
    }
}

