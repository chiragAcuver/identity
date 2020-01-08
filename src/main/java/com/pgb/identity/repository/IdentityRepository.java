package com.pgb.identity.repository;

import com.pgb.identity.dto.Contact;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Repository to deal with Contact Details , Key is string
 */
@Component
@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "contact")
public interface IdentityRepository extends CouchbaseRepository<Contact, String> {
    Optional<Contact> findByEmailId(String emailId);
}
