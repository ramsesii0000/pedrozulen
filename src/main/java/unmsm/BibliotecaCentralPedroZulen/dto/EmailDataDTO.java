package unmsm.BibliotecaCentralPedroZulen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDataDTO {
    private String toEmail;
    private String name;
    private String consultation;
    private String subject;
    private String details;
}