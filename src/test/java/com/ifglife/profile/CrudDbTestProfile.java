package com.ifglife.profile;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.HashMap;
import java.util.Map;

public class CrudDbTestProfile implements QuarkusTestProfile {

    @Override
    public Map<String, String> getConfigOverrides() {
        Map<String, String> config = new HashMap<>();
        config.put("quarkus.hibernate-orm.enabled", "true");
        config.put("quarkus.datasource.db-kind", "h2");
        config.put("quarkus.datasource.jdbc.url", "jdbc:h2:mem:crudtest;DB_CLOSE_DELAY=-1");
        config.put("quarkus.datasource.username", "sa");
        config.put("quarkus.datasource.password", "sa");
        config.put("quarkus.hibernate-orm.database.generation", "drop-and-create");
        return config;
    }
}
