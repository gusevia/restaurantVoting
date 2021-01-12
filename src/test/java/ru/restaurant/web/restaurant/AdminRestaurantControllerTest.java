package ru.restaurant.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant.testData.RestaurantTestData;
import ru.restaurant.TestUtil;
import ru.restaurant.testData.UserTestData;
import ru.restaurant.web.AbstractControllerTest;
import ru.restaurant.model.Restaurant;
import ru.restaurant.service.RestaurantService;
import ru.restaurant.util.exception.NotFoundException;
import ru.restaurant.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.web.restaurant.AdminRestaurantController.REST_URL;

class AdminRestaurantControllerTest extends AbstractControllerTest {
    @Autowired
    private RestaurantService service;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(RestaurantTestData.TEST_MATCHER_WITHOUT_DISHES
                        .contentJson(RestaurantTestData.getRestaurants()));
    }

    @Test
    void getById() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + RestaurantTestData.RESTAURANT_1_ID)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(RestaurantTestData.TEST_MATCHER_WITHOUT_DISHES.contentJson(RestaurantTestData.RESTAURANT_1));
    }

    @Test
    void getByIdNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/10")
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void create() throws Exception {
        Restaurant expectedCreated = RestaurantTestData.getNew();
        ResultActions resultActions = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(expectedCreated)))
                .andDo(print())
                .andExpect(status().isCreated());
        Restaurant actual = TestUtil.readFromJson(resultActions, Restaurant.class);
        expectedCreated.setId(actual.getId());
        RestaurantTestData.TEST_MATCHER_WITHOUT_DISHES.assertMatch(actual, expectedCreated);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        Restaurant restaurant = new Restaurant(RestaurantTestData.RESTAURANT_1);
        restaurant.setId(null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .content(JsonUtil.writeValue(restaurant))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void update() throws Exception {
        Restaurant expectedUpdated = RestaurantTestData.getUpdated();
        ResultActions resultActions = perform(MockMvcRequestBuilders.put(REST_URL + "/" + RestaurantTestData.RESTAURANT_1_ID)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(expectedUpdated)))
                .andDo(print())
                .andExpect(status().isOk());
        Restaurant actual = TestUtil.readFromJson(resultActions, Restaurant.class);
        RestaurantTestData.TEST_MATCHER_WITHOUT_DISHES.assertMatch(actual, expectedUpdated);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Restaurant restaurant = new Restaurant(RestaurantTestData.RESTAURANT_1);
        restaurant.setName(RestaurantTestData.RESTAURANT_2.getName());
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + RestaurantTestData.RESTAURANT_1_ID)
                .content(JsonUtil.writeValue(restaurant))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + RestaurantTestData.RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.getById(RestaurantTestData.RESTAURANT_1_ID));
    }
}