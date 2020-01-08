package com.pgb.identity.annotations;

import com.pgb.identity.dto.Contact;
import com.pgb.identity.repository.IdentityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class UniqueCredentialsValidator implements ConstraintValidator<UniqueCredentials, Contact> {
    @Autowired
    IdentityRepository identityRepository;

    @Override
    public void initialize(UniqueCredentials constraintAnnotation) {
    }

    @Override
    public boolean isValid(Contact contact, ConstraintValidatorContext constraintValidatorContext) {
        if (identityRepository.findByEmailId(contact.getEmailId()).isPresent()) {
            log.info("\n \n emailId {} alreadyExists \n\n", contact.getEmailId());
            return false;
        } else if (identityRepository.findByEmailId(contact.getEmailId()).isPresent()) {
            log.info("\n\n mobileNo {} alreadyExists \n\n", contact.getEmailId());
            return false;
        } else {
            log.info("\n\n Contact is valid \n\n");
            return true;
        }
    }
}
