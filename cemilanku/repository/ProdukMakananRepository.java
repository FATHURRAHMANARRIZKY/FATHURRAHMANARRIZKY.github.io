package com.cemilanku.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cemilanku.model.ProdukMakanan;

@Repository
public interface ProdukMakananRepository extends MongoRepository<ProdukMakanan, String> {

}
