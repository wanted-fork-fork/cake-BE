package com.forkfork.cake.service;

import com.forkfork.cake.domain.ConsentForm;
import com.forkfork.cake.repository.ConsentFormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsentFormService {

    private final ConsentFormRepository consentFormRepository;

    public ConsentForm saveConsentForm(ConsentForm consentForm) {
        return consentFormRepository.save(consentForm);
    }
}
