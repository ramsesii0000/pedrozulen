package unmsm.BibliotecaCentralPedroZulen.service;

import unmsm.BibliotecaCentralPedroZulen.Utils.UtilsLibrary;
import unmsm.BibliotecaCentralPedroZulen.model.Multa;
import unmsm.BibliotecaCentralPedroZulen.repository.MultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.Optional;

@Service
public class MultaService {

    @Autowired
    private MultaRepository multaRepository;

    public Collection<Multa> findAllMultas() {
        return (Collection<Multa>) multaRepository.findByDeleteMulta(false);
    }

    public ResponseEntity<String> createMulta(Multa multa) {
        multaRepository.save(multa);
        return UtilsLibrary.getResponseEntity("Multa registrada con exito", HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateMulta(Long idMulta, Multa multaUpdate){
        Optional<Multa> multa = multaRepository.findById(idMulta);

        multa.get().setDescriptionMulta(multaUpdate.getDescriptionMulta());
        multa.get().setTypeMulta(multaUpdate.getTypeMulta());
        multa.get().setMultaDate(multaUpdate.getMultaDate());
        multa.get().setStatusMulta(multaUpdate.getStatusMulta());
        multa.get().setMontoTotal(multaUpdate.getMontoTotal());
        multa.get().setPrestamo(multaUpdate.getPrestamo());

        multaRepository.save(multa.get());

        return UtilsLibrary.getResponseEntity("La multa fue actualizada con éxito", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteMulta(@PathVariable Long idMulta){
        Optional<Multa> multaOptional = multaRepository.findById(idMulta);
        if (multaOptional.isEmpty()) {
            return UtilsLibrary.getResponseEntity("No existe la multa con id: " + idMulta, HttpStatus.BAD_REQUEST);
        } else {
            Multa multa = multaOptional.get();
            multa.setDeleteMulta(true);
            multaRepository.save(multa);
            return UtilsLibrary.getResponseEntity("La Multa fue eliminada con exito", HttpStatus.OK);
        }
    }
}
