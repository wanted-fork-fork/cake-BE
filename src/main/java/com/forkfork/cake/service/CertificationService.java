package com.forkfork.cake.service;

import com.forkfork.cake.domain.Certification;
import com.forkfork.cake.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CertificationService {
    private final CertificationRepository certificationRepository;

    public Certification saveCertification(Certification certification) {
        return certificationRepository.save(certification);
    }

    public Certification findCertificationByEmail(String email) {
        return certificationRepository.findByEmail(email);
    }

    public void deleteCertificationByEmail(String email) {
        certificationRepository.deleteByEmail(email);
    }
}
