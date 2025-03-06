package com.cemilanku.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cemilanku.model.Contact;

public interface ContactRepository extends MongoRepository<Contact, String> {
}
