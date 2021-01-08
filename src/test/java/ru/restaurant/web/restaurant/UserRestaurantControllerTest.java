package ru.restaurant.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.restaurant.RestaurantTestData;
import ru.restaurant.TestUtil;
import ru.restaurant.UserTestData;
import ru.restaurant.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.web.restaurant.UserRestaurantController.REST_URL;

class UserRestaurantControllerTest extends AbstractControllerTest {
    @Test
    void getByNow() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.USER_1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(RestaurantTestData.TEST_MATCHER.contentJson(RestaurantTestData.RESTAURANT_1, RestaurantTestData.RESTAURANT_2));
    }

    @Test
    void getByIdNow() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + RestaurantTestData.RESTAURANT_1_ID)
                .with(TestUtil.userHttpBasic(UserTestData.USER_1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(RestaurantTestData.TEST_MATCHER.contentJson(RestaurantTestData.RESTAURANT_1));
    }

    @Test
    void getByIdNowNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/10")
                .with(TestUtil.userHttpBasic(UserTestData.USER_1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}