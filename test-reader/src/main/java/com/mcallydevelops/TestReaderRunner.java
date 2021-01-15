package com.mcallydevelops;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Slf4j
@Component
public class TestReaderRunner implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public TestReaderRunner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        Stream<NumberRow> numberRowStream = jdbcTemplate.queryForStream("SELECT * FROM number_table", new NumberRowMapper());
        numberRowStream.forEach(x -> {
            log.info("Printing like we are uploading to sqs");
        });
    }
}
