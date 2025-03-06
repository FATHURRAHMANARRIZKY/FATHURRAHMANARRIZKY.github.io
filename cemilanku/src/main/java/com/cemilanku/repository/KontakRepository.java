package com.cemilanku.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cemilanku.model.Kontak;

@Repository
public interface KontakRepository extends MongoRepository<Kontak, String> {

}
