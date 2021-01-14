package com.mcallydevelops;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Component
public class TestLoaderRunner implements CommandLineRunner {

  private final JdbcTemplate jdbcTemplate;
  private final ApplicationProperties applicationProperties;

  public TestLoaderRunner(JdbcTemplate jdbcTemplate, ApplicationProperties applicationProperties) {
    this.jdbcTemplate = jdbcTemplate;
    this.applicationProperties = applicationProperties;
  }

  @Override
  public void run(String... args) throws Exception {
    String query = "insert into number_table (example_number)" + " values (?)";
    var numberOfInserts = applicationProperties.getNumberOfInserts();
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    for (int i = 0; i < numberOfInserts; ++i) {
      if (i % 1000 == 0) {
        log.info("Completed " + i);
      }
      jdbcTemplate.update(query, i);
    }
    stopWatch.stop();
    log.info("Completed: " + numberOfInserts);
    log.info("Completed in: " + stopWatch.getTotalTimeSeconds());
  }
}
