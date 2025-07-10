package unmsm.BibliotecaCentralPedroZulen.controller;

import unmsm.BibliotecaCentralPedroZulen.Utils.UtilsLibrary;
import unmsm.BibliotecaCentralPedroZulen.constants.LibraryConstants;
import unmsm.BibliotecaCentralPedroZulen.model.DetailsPrestamo;
import unmsm.BibliotecaCentralPedroZulen.service.DetailsPrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/detailsPrestamo")
public class DetailsPrestamoController {

    @Autowired
    private DetailsPrestamoService detailsPrestamoService;

    @GetMapping("/details/{idPrestamo}")
    public ResponseEntity<?> getAllsDetailsByIdPrestamo(@PathVariable Long idPrestamo ){
        try {
            Collection<DetailsPrestamo> itemsDetails =  detailsPrestamoService.findDetailsByIdPrestamo(idPrestamo);
            return new ResponseEntity<>(itemsDetails, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
