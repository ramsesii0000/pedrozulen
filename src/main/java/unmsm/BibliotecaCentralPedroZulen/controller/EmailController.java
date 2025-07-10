package unmsm.BibliotecaCentralPedroZulen.controller;

import unmsm.BibliotecaCentralPedroZulen.dto.EmailDataDTO;
import unmsm.BibliotecaCentralPedroZulen.service.EmailSenderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailSenderService emailSender;

    public EmailController(EmailSenderService emailSender) {
        this.emailSender = emailSender;
    }

    @PostMapping("/consulta")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDataDTO emailData) {
        try {
            emailSender.sendEmail(emailData);
            return ResponseEntity.ok("Correo electrónico enviado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar el correo electrónico");
        }
    }
}