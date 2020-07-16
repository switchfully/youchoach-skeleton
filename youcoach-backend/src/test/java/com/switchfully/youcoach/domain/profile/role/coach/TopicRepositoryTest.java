package com.switchfully.youcoach.domain.profile.role.coach;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TopicRepositoryTest {

    @Autowired
    private TopicRepository topicRepository;

    @Test
    void test() {
        List<String> allTopics = topicRepository.getAllTopics();

        Assertions.assertThat(allTopics).containsExactlyInAnyOrder("Algebra", "Biology", "French");
    }
}
