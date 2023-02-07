package com.maersk.bookingservice.repository;

import com.maersk.bookingservice.model.IdGenerator;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IdGeneratorRepository extends ReactiveCassandraRepository<IdGenerator, Integer> {
    @Query("CREATE TABLE IF NOT EXISTS id_generator (id int PRIMARY KEY, cluster_id int);")
    Mono<Void> createTable();

//
//    @Query("SELECT * FROM item WHERE columnName = ?0")
//    Mono<IdGenerator> findByColumnName(String columnValue);
    @AllowFiltering
    Mono<IdGenerator> findByClusterId(Integer clusterId);



}
