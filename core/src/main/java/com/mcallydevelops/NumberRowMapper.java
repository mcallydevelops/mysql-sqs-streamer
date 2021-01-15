package com.mcallydevelops;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NumberRowMapper implements RowMapper<NumberRow> {
    @Override
    public NumberRow mapRow(ResultSet resultSet, int i) throws SQLException {

        var exampleNumber = resultSet.getInt("example_number");

        return new NumberRow(exampleNumber);
    }
}
