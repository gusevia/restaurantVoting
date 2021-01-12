package ru.restaurant.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant.testData.DishTestData;
import ru.restaurant.testData.RestaurantTestData;
import ru.restaurant.TestUtil;
import ru.restaurant.testData.UserTestData;
import ru.restaurant.web.AbstractControllerTest;
import ru.restaurant.model.Dish;
import ru.restaurant.service.DishService;
import ru.restaurant.util.exception.NotFoundException;
import ru.restaurant.web.json.JsonUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.web.dish.AdminDishController.REST_URL;

class AdminDishControllerTest extends AbstractControllerTest {
    @Autowired
    private DishService service;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/dishes/" + DishTestData.DISH_2_ID)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(DishTestData.TEST_MATCHER.contentJson(DishTestData.DISH_2));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/dishes/10")
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getByRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/restaurants/" + RestaurantTestData.RESTAURANT_1_ID + "/dishes")
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(DishTestData.TEST_MATCHER.contentJson(List.of(DishTestData.DISH_1, DishTestData.DISH_2, DishTestData.DISH_3, DishTestData.DISH_4)));
    }

    @Test
    void create() throws Exception {
        Dish expectedCreated = DishTestData.getNew();
        ResultActions resultActions = perform(MockMvcRequestBuilders.post(REST_URL + "/restaurants/" + RestaurantTestData.RESTAURANT_1_ID + "/dishes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN))
                .content(JsonUtil.writeValue(expectedCreated)))
                .andDo(print())
                .andExpect(status().isCreated());
        Dish actual = TestUtil.readFromJson(resultActions, Dish.class);
        expectedCreated.setId(actual.getId());
        expectedCreated.setDate(actual.getDate());
        DishTestData.TEST_MATCHER.assertMatch(actual, expectedCreated);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createNotFoundRestaurant() throws Exception {
        Dish expectedCreated = DishTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_URL + "/restaurants/10/dishes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN))
                .content(JsonUtil.writeValue(expectedCreated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        Dish dish = new Dish(DishTestData.DISH_2);
        dish.setId(null);
        dish.setRestaurant(null);
        perform(MockMvcRequestBuilders.post(REST_URL + "/restaurants/" + RestaurantTestData.RESTAURANT_1_ID + "/dishes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN))
                .content(JsonUtil.writeValue(dish)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void update() throws Exception {
        Dish expectedUpdated = DishTestData.getUpdated();
        ResultActions resultActions = perform(MockMvcRequestBuilders.put(REST_URL +
                "/restaurants/" +
                RestaurantTestData.RESTAURANT_1_ID +
                "/dishes/" +
                DishTestData.DISH_2_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN))
                .content(JsonUtil.writeValue(expectedUpdated)))
                .andDo(print())
                .andExpect(status().isOk());
        Dish actual = TestUtil.readFromJson(resultActions, Dish.class);
        DishTestData.TEST_MATCHER.assertMatch(actual, expectedUpdated);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Dish dish = new Dish(DishTestData.DISH_3);
        dish.setId(DishTestData.DISH_2_ID);
        perform(MockMvcRequestBuilders.put(REST_URL +
                "/restaurants/" +
                RestaurantTestData.RESTAURANT_1_ID +
                "/dishes/" +
                DishTestData.DISH_2_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN))
                .content(JsonUtil.writeValue(dish)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void updateNotFoundRestaurant() throws Exception {
        Dish expectedUpdated = DishTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL +
                "/restaurants/10/dishes/" + DishTestData.DISH_2_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN))
                .content(JsonUtil.writeValue(expectedUpdated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/dishes/" + DishTestData.DISH_2_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.getById(DishTestData.DISH_2_ID));
    }

}