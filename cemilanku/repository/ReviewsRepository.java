package com.cemilanku.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cemilanku.model.Reviews;

@Repository
public interface ReviewsRepository extends MongoRepository<Reviews, String> {

}
