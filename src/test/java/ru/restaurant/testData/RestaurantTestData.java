package ru.restaurant.testData;

import ru.restaurant.TestMatcher;
import ru.restaurant.model.Restaurant;

import java.util.List;

import static ru.restaurant.testData.DishTestData.*;
import static ru.restaurant.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static TestMatcher<Restaurant> TEST_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(Restaurant.class);
    public static TestMatcher<Restaurant> TEST_MATCHER_WITHOUT_DISHES = TestMatcher.usingFieldsWithIgnoringAssertions(Restaurant.class, "dishes");

    public final static int RESTAURANT_1_ID = START_SEQ + 4;
    public final static int RESTAURANT_2_ID = START_SEQ + 5;
    public final static int RESTAURANT_3_ID = START_SEQ + 6;

    public final static Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_1_ID, "Семифреддо");
    public final static Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_2_ID, "Пушкин");
    public final static Restaurant RESTAURANT_3 = new Restaurant(RESTAURANT_3_ID, "Жеральдин");

    static {
        RESTAURANT_1.setDishes(List.of(DISH_2, DISH_3, DISH_4));
        RESTAURANT_2.setDishes(List.of(DISH_5));
    }

    public static List<Restaurant> getRestaurants() {
        return List.of(RESTAURANT_1, RESTAURANT_2, RESTAURANT_3);
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "Novikov");
    }

    public static Restaurant getUpdated() {
        Restaurant restaurant = new Restaurant(RESTAURANT_1);
        restaurant.setName("Большой");
        return restaurant;
    }
}
