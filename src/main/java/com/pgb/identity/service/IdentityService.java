package com.pgb.identity.service;

import com.couchbase.client.java.document.json.JsonObject;
import com.pgb.identity.dto.Contact;
import com.pgb.identity.repository.IdentityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 *
 */
@Slf4j
@Service
public class IdentityService {

    private final IdentityRepository identityRepository;
    private final TokenService tokenService;

    public IdentityService(IdentityRepository identityRepository, TokenService tokenService) {
        this.identityRepository = identityRepository;
        this.tokenService = tokenService;
    }

    /**
     * Function to login using emailId and password
     *
     * @param emailId  emailID provided in the POST call
     * @param password password provided in the POST call
     * @return emailId of the user if present
     */
    public Map<String, Object> loginService(final String emailId, final String password) {
        /* TODO :
         * Implement the functionality for logout and user login timeout using tokens if possible
         * */
        if (emailId == null || password == null) {
            throw new AuthenticationCredentialsNotFoundException("Bad Username or Password due to null values ");
        } else if (BCrypt.checkpw(password, identityRepository.findByEmailId(emailId).get().getPassword())) {
            return JsonObject.create()
                    .put("token", tokenService.buildJwtToken(emailId))
                    .toMap();
        } else {
            throw new AuthenticationCredentialsNotFoundException("Bad Username or Password");
        }
    }

    /**
     * Create contact in Couchbase db using repository
     *
     * @param contact Contact Details
     * @return return email id of the created contact
     */
    public Optional<Map<String, Object>> contactService(final Contact contact) {
        if (!Objects.isNull(contact)) {
            String passHash = BCrypt.hashpw(contact.getPassword(), BCrypt.gensalt());
            contact.setPassword(passHash);
            identityRepository.save(contact);
            return Optional.of(
                    JsonObject.create().put("token", tokenService.buildJwtToken(contact.getEmailId())).toMap());
        } else {
            throw new NullPointerException();
        }
    }

}
