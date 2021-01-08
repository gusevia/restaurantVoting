package ru.restaurant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@Table(name = "dishes", uniqueConstraints = {
        @UniqueConstraint(name = "dishes_unique_restaurant_name_date_idx", columnNames = {
                "restaurant_id", "name", "date"
        })
})
public class Dish extends AbstractNamedEntity {
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "price",nullable = false)
    @Min(1)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(String name, int price) {
        this(null, name, price, null);
    }

    public Dish(Integer id, String name, int price, LocalDate date) {
        super(id, name);
        this.price = price;
        this.date = date;
    }

    public Dish(Dish dish) {
        this.id = dish.id;
        this.name = dish.name;
        this.price = dish.price;
        this.date = dish.date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}