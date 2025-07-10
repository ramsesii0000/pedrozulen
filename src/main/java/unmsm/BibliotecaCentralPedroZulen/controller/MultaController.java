package unmsm.BibliotecaCentralPedroZulen.controller;

import unmsm.BibliotecaCentralPedroZulen.Utils.UtilsLibrary;
import unmsm.BibliotecaCentralPedroZulen.constants.LibraryConstants;
import unmsm.BibliotecaCentralPedroZulen.model.Book;
import unmsm.BibliotecaCentralPedroZulen.model.Multa;
import unmsm.BibliotecaCentralPedroZulen.service.MultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/multa")
public class MultaController {

    @Autowired
    private MultaService multaService;

    @GetMapping("/")
    public ResponseEntity<?> getAllsMultas(){
        try {
            Collection<Multa> itemsMultas =  multaService.findAllMultas();
            return new ResponseEntity<>(itemsMultas, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createMulta(@RequestBody Multa multa){
        try {
            return multaService.createMulta(multa);
        }catch (Exception e){
            e.printStackTrace();
        }
        return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update/{idMulta}")
    public ResponseEntity<?> updateBook(@PathVariable Long idMulta, @RequestBody Multa multaUpdate){
        try{
            return multaService.updateMulta(idMulta, multaUpdate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/delete/{idMulta}")
    public ResponseEntity<?> deleteMulta(@PathVariable Long idMulta) {
        try {
            return multaService.deleteMulta(idMulta);
        } catch (Exception e) {
            e.printStackTrace();
            return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
