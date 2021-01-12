package ru.restaurant.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.restaurant.TestUtil;
import ru.restaurant.testData.UserTestData;
import ru.restaurant.testData.VoteTestData;
import ru.restaurant.web.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.web.vote.AdminVoteController.REST_URL;

class AdminVoteControllerTest extends AbstractControllerTest {

    @Test
    void getToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VoteTestData.TEST_MATCHER.contentJson(List.of(VoteTestData.VOTE_2, VoteTestData.VOTE_3)));
    }

    @Test
    void getHistory() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/history")
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VoteTestData.TEST_MATCHER.contentJson(List.of(VoteTestData.VOTE_2, VoteTestData.VOTE_3, VoteTestData.VOTE_1)));
    }

    @Test
    void getHistoryBetween() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/history")
                .with(TestUtil.userHttpBasic(UserTestData.ADMIN))
                .param("startDate", "2020-12-31")
                .param("endDate", "2020-12-31"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VoteTestData.TEST_MATCHER.contentJson(List.of(VoteTestData.VOTE_1)));
    }
}