package ru.restaurant.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.restaurant.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static ru.restaurant.testData.UserTestData.USER_1_ID;
import static ru.restaurant.testData.VoteTestData.*;

@SpringJUnitConfig(locations = {"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @Test
    void getByUserIdOrderByDateDesc() {
        List<Vote> actual = voteRepository.getByUserIdOrderByDateDesc(USER_1_ID);
        List<Vote> expected = Stream.of(VOTE_2, VOTE_1).collect(Collectors.toList());
        TEST_MATCHER.assertMatch(actual, expected);

    }

    @Test
    void getByDate() {
        List<Vote> actual = voteRepository.getByDate(LocalDate.now());
        List<Vote> expected = Stream.of(VOTE_2, VOTE_3).collect(Collectors.toList());
        TEST_MATCHER.assertMatch(actual, expected);
    }
}