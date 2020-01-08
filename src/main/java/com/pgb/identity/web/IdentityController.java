package com.pgb.identity.web;

import com.pgb.identity.dto.Contact;
import com.pgb.identity.service.IdentityService;
import com.pgb.identity.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * Controller class for Identity Service , contains login and contact creation endpoints
 */
@Slf4j
@RestController
@RequestMapping
public class IdentityController {

    private final IdentityService identityService;
    private final TokenService tokenService;

    public IdentityController(IdentityService identityService, TokenService tokenService) {
        this.identityService = identityService;
        this.tokenService = tokenService;
    }


    @RequestMapping(value = "/contacts/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginCredentials) {
        String emailId = loginCredentials.get("emailId");
        String password = loginCredentials.get("password");
        String mobileNumber = loginCredentials.get("mobileNumber");
        if (emailId == null || password == null)
            return ResponseEntity.badRequest().body(new Error("User or password missing, or malformed request"));
        try {
            Map<String, Object> data = identityService.loginService(emailId, password);
            return ResponseEntity.ok((data));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new Error(e.getMessage()));
        }
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.POST)
    public ResponseEntity<?> contact(@RequestBody Contact contact) {
        try {
            Optional<Map<String, Object>> result = identityService.contactService(contact);
            return ResponseEntity.ok(result);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Error(e.getMessage()));
        }
    }

    @RequestMapping(value = "/contacts/private", method = RequestMethod.POST)
    public ResponseEntity<?> privateFunction(@RequestHeader("Authorization") String authentication, @RequestBody Map<String, String> username) {
        if (authentication == null || !authentication.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Error("Bearer Authentication must be used"));
        }
        try {
            tokenService.verifyAuthenticationHeader(authentication, username.get("emailId"));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new String("The user had valid token"));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Error("Forbidden, you can't book for this user"));
        }
    }
}
