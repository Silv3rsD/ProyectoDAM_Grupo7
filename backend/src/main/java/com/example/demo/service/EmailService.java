package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Esta clase es provisional y su funcionamiento está adaptado a la fase del
 * desarrollo y proposito del proyecto. Se encarga de crear un enlace que sirve
 * para verificar las cuentas. En un proyecto real se enviaría por correo. Se
 * simula está función mandando el mensaje de verificación por consola.
 */
@Service
public class EmailService {

	private static final Logger log = LoggerFactory.getLogger(EmailService.class);

	private final ObjectProvider<JavaMailSender> mailSender;
	private final String from;
	private final String baseUrl;

	public EmailService(ObjectProvider<JavaMailSender> mailSender, @Value("${app.mail.from}") String from,
			@Value("${app.base-url}") String baseUrl) {
		this.mailSender = mailSender;
		this.from = from;
		this.baseUrl = baseUrl;
	}

	public void sendVerification(String to, String token) {
		String link = baseUrl + "/api/auth/verify?token=" + token;

		JavaMailSender sender = mailSender.getIfAvailable();
		if (sender == null) {
			// Recordatorio: Función simulada para el desarrollo del proyecto.
			log.info("[SIMULADO] Correo a {}: enlace de verificación {}", to, link);
			return;
		}

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject("Verifica tu cuenta de AutentIFPcar");
		message.setText("Hola,\n\nPulsa en este enlace para verificar tu correo (caduca en 24 horas):\n" + link
				+ "\n\nSi no has creado la cuenta, ignora este mensaje.");
		sender.send(message);
	}
}