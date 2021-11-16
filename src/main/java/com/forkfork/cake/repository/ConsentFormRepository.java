package com.forkfork.cake.repository;

import com.forkfork.cake.domain.ConsentForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsentFormRepository extends JpaRepository<ConsentForm, Long> {
}
