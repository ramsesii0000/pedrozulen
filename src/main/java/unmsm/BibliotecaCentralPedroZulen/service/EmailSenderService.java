package unmsm.BibliotecaCentralPedroZulen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import unmsm.BibliotecaCentralPedroZulen.dto.EmailDataDTO;


@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendEmail(EmailDataDTO emailData) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(emailData.getToEmail());
            message.setSubject(emailData.getSubject());

            String emailContent = "Gracias por comunicarte con nosotros " + emailData.getName() + ". \n";
            emailContent += "En un momento uno de nuestros administradores se pondrá en contacto con usted por esta vía \n\n";
            emailContent += "Tipo de consulta: " + emailData.getConsultation() + "\n";
            emailContent += "Detalle de la consulta: " + emailData.getDetails() + "\n\n";
            emailContent += "Atentamente. El equipo de Golden Blog";

            message.setText(emailContent);
            emailSender.send(message);
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e);
        }
    }

}