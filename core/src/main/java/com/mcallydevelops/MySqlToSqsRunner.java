package com.mcallydevelops;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MySqlToSqsRunner implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private final SqsService sqs;

    public MySqlToSqsRunner(JdbcTemplate jdbcTemplate, SqsService sqs) {
        this.jdbcTemplate = jdbcTemplate;
        this.sqs = sqs;
    }

    @Override
    public void run(String... args) throws Exception {
        sqs.create();
        jdbcTemplate.queryForStream("SELECT * FROM number_table where example_number < 1000", new NumberRowMapper())
                .forEach(i -> {
                    try {
                        sqs.send(i);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });
        sqs.delete();
    }
}
