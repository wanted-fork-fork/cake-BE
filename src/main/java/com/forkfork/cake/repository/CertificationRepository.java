package com.forkfork.cake.repository;

import com.forkfork.cake.domain.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
    Certification findByEmail(String email);

    void deleteByEmail(String email);
}
