package ru.restaurant.testData;

import ru.restaurant.TestMatcher;
import ru.restaurant.model.Vote;

import java.time.LocalDate;

import static ru.restaurant.testData.UserTestData.*;
import static ru.restaurant.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static TestMatcher<Vote> TEST_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(Vote.class);

    public final static int VOTE_1_ID = START_SEQ + 12;
    public final static int VOTE_2_ID = START_SEQ + 13;
    public final static int VOTE_3_ID = START_SEQ + 14;

    public final static Vote VOTE_1 = new Vote(VOTE_1_ID, USER_1_ID, RestaurantTestData.RESTAURANT_1_ID, LocalDate.of(2020, 12, 31));
    public final static Vote VOTE_2 = new Vote(VOTE_2_ID, USER_1_ID, RestaurantTestData.RESTAURANT_1_ID, LocalDate.now());
    public final static Vote VOTE_3 = new Vote(VOTE_3_ID, USER_2_ID, RestaurantTestData.RESTAURANT_2_ID, LocalDate.now());

}
