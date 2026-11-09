package com.example.demo.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.MeResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.AccountStatus;
import com.example.demo.model.EmailVerification;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.EmailVerificationRepository;
import com.example.demo.repository.UserRepository;

/**
 * Maneja la lógica de gestión de cuentas:
 */
@Service
public class AuthService {

	private static final SecureRandom RANDOM = new SecureRandom();

	private final UserRepository users;
	private final EmailVerificationRepository verifications;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;
	private final JwtService jwtService;

	public AuthService(UserRepository users, EmailVerificationRepository verifications, PasswordEncoder passwordEncoder,
			EmailService emailService, JwtService jwtService) {
		this.users = users;
		this.verifications = verifications;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
		this.jwtService = jwtService;
	}

	/**
	 * Crea un usuario INDIVIDUAL con la contraseña cifrada, genera un token de
	 * verificación y envía el enlace.
	 */
	@Transactional
	public void register(RegisterRequest req) {
		String username = req.username().trim();
		String email = req.email().trim().toLowerCase();

		if (users.existsByUsername(username)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe");
		}
		if (users.existsByEmail(email)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
		}

		User user = users.save(new User(username, email, passwordEncoder.encode(req.password()), Role.INDIVIDUAL));

		String token = newToken();
		verifications.save(new EmailVerification(user.getId(), hash(token), LocalDateTime.now().plusHours(24)));

		// Si el envío falla, se deshace todo el registro
		emailService.sendVerification(email, token);
	}

	/**
	 * Valida el token, marca el correo como verificado, activa la cuenta INDIVIDUAL
	 * y elimina el token para que no pueda reutilizarse.
	 */
	@Transactional
	public void verifyEmail(String token) {
		EmailVerification verification = verifications.findByTokenHash(hash(token))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enlace inválido"));

		if (verification.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El enlace ha caducado");
		}

		User user = users.findById(verification.getUserId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enlace inválido"));

		user.setEmailVerified(true);
		
		/**
		 * El INDIVIDUAL queda activo al verificar el correo; el TECHNICIAN seguirá
		 * PENDING hasta que un ADMIN lo apruebe
		 */

		if (user.getRole() == Role.INDIVIDUAL) {
			user.setAccountStatus(AccountStatus.ACTIVE);
		}
		verifications.delete(verification);
	}

	/**
	 * Comprueba credenciales y estado de la cuenta y devuelve un JWT. me: devuelve
	 * los datos del usuario autenticado.
	 */
	public LoginResponse login(LoginRequest req) {
		User user = users.findByUsername(req.username().trim())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas"));

		if (!passwordEncoder.matches(req.password(), user.getPasswordHash())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas");
		}
		if (!user.isEnabled()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cuenta deshabilitada");
		}
		if (!user.isEmailVerified()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Debes verificar tu correo");
		}
		if (user.getAccountStatus() == AccountStatus.PENDING) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cuenta pendiente de aprobación");
		}
		if (user.getAccountStatus() == AccountStatus.REJECTED) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cuenta rechazada");
		}

		return new LoginResponse(jwtService.generate(user), user.getRole().name());
	}

	public MeResponse me(String username) {
		User user = users.findByUsername(username)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
		return new MeResponse(user.getUsername(), user.getEmail(), user.getRole().name(),
				user.getAccountStatus().name());
	}

	private static String newToken() {
		byte[] bytes = new byte[32];
		RANDOM.nextBytes(bytes);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	}

	private static String hash(String token) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			return HexFormat.of().formatHex(digest.digest(token.getBytes(StandardCharsets.UTF_8)));
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}
}