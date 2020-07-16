package com.switchfully.youcoach.domain.profile.role.coach;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TopicRepository {

    private JdbcTemplate jdbcTemplate;

    public TopicRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getAllTopics(){
        return jdbcTemplate.queryForList("select distinct name from TOPIC", String.class);
    }
}
