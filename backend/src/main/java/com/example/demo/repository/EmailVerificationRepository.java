package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.EmailVerification;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {

	/**
	 * Este método hace:
	 * SELECT * FROM email_verifications WHERE token_hash = ?
	 */
    Optional<EmailVerification> findByTokenHash(String tokenHash);
}