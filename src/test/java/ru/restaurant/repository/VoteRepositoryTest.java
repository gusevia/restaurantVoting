package ru.restaurant.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.restaurant.model.Vote;
import ru.restaurant.web.AbstractControllerTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.restaurant.UserTestData.USER_1_ID;
import static ru.restaurant.VoteTestData.*;

class VoteRepositoryTest extends AbstractControllerTest {

    @Autowired
    private VoteRepository voteRepository;

    @Test
    void getByUserIdOrderByDateDesc() {
        List<Vote> actual= voteRepository.getByUserIdOrderByDateDesc(USER_1_ID);
        List<Vote> expected= Stream.of(VOTE_2,VOTE_1).collect(Collectors.toList());
        TEST_MATCHER.assertMatch(actual,expected);

    }
}