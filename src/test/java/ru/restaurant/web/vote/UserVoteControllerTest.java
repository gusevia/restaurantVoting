package ru.restaurant.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.restaurant.testData.RestaurantTestData;
import ru.restaurant.TestUtil;
import ru.restaurant.testData.UserTestData;
import ru.restaurant.testData.VoteTestData;
import ru.restaurant.web.AbstractControllerTest;
import ru.restaurant.service.VoteService;
import ru.restaurant.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.web.vote.UserVoteController.REST_URL;

class UserVoteControllerTest extends AbstractControllerTest {
    @Autowired
    private VoteService service;

    @Test
    void getUserHistory() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/votes")
                .with(TestUtil.userHttpBasic(UserTestData.USER_1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VoteTestData.TEST_MATCHER.contentJson(List.of(VoteTestData.VOTE_2, VoteTestData.VOTE_1)));
    }

    @Test
    void create() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + "/" +
                RestaurantTestData.RESTAURANT_1_ID + "/votes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.USER_3)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void createRepeat() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + "/" + RestaurantTestData.RESTAURANT_1_ID + "/votes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.USER_1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createWithRestaurantWithoutMenu() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + "/" + RestaurantTestData.RESTAURANT_3_ID + "/votes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.USER_3)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        ResultActions resultActions = perform(MockMvcRequestBuilders.put(REST_URL + "/" + RestaurantTestData.RESTAURANT_2_ID + "/votes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.USER_1)))
                .andDo(print());
        if (VoteService.END_VOTING_TIME.isAfter(LocalTime.now())) {
            resultActions.andExpect(status().isNoContent());
        } else {
            resultActions.andExpect(status().isUnprocessableEntity());
        }
    }

    @Test
    void delete() throws Exception {
        ResultActions resultActions = perform(MockMvcRequestBuilders.delete(REST_URL + "/" + RestaurantTestData.RESTAURANT_1_ID + "/votes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(TestUtil.userHttpBasic(UserTestData.USER_1)))
                .andDo(print());
        if (VoteService.END_VOTING_TIME.isAfter(LocalTime.now())) {
            resultActions.andExpect(status().isNoContent());
            assertThrows(NotFoundException.class, () -> service.getByUserIdAndDate(UserTestData.USER_1_ID, LocalDate.now()));
        } else {
            resultActions.andExpect(status().isUnprocessableEntity());
        }
    }
}