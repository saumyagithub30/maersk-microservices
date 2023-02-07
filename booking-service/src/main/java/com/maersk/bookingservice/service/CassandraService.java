package com.maersk.bookingservice.service;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;

public class CassandraService {
    private CqlSession session;

    public CqlSession connect() {
        session = CqlSession.builder().withConfigLoader(
                DriverConfigLoader.fromClasspath("application.conf"))
                .build();
        return session;
    }

}
