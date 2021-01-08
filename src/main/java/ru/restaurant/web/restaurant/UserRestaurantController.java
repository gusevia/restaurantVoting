package ru.restaurant.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.restaurant.model.Restaurant;
import ru.restaurant.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;

import static ru.restaurant.web.restaurant.UserRestaurantController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController {
    public static final String REST_URL = "/rest/restaurants";

    private final RestaurantService restaurantService;

    public UserRestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<Restaurant> getByNow() {
        return restaurantService.getByNow();
    }

    @GetMapping("/{id}")
    public Restaurant getByIdNow(@PathVariable Integer id) {
        return restaurantService.getByIdAndDate(id, LocalDate.now());
    }

}
