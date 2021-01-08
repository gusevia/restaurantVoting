package ru.restaurant.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Dish> dishes;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Restaurant restaurant) {
        super(restaurant.getId(), restaurant.getName());

    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
