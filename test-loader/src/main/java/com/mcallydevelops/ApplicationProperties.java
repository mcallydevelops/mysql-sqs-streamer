package com.mcallydevelops;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ApplicationProperties {

    @Value("${numberOfInserts}")
    private Integer numberOfInserts;

}
