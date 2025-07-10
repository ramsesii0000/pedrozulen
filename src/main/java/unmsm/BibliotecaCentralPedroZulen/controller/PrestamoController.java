package unmsm.BibliotecaCentralPedroZulen.controller;

import unmsm.BibliotecaCentralPedroZulen.model.Book;
import unmsm.BibliotecaCentralPedroZulen.model.DetailsPrestamo;
import unmsm.BibliotecaCentralPedroZulen.model.Prestamo;
import unmsm.BibliotecaCentralPedroZulen.request.PrestamoRequest;
import unmsm.BibliotecaCentralPedroZulen.Utils.UtilsLibrary;
import unmsm.BibliotecaCentralPedroZulen.constants.LibraryConstants;
import unmsm.BibliotecaCentralPedroZulen.service.DetailsPrestamoService;
import unmsm.BibliotecaCentralPedroZulen.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping("/")
    public ResponseEntity<?> getAllBooks(){
        try {
            Collection<Prestamo> itemsPrestamos =  prestamoService.getAllPrestamos();
            return new ResponseEntity<>(itemsPrestamos, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<String> createPrestamoAndDetails(@RequestBody(required = true) PrestamoRequest prestamoRequest) {
        try {
            return prestamoService.savePrestamoWithDetail(prestamoRequest.getPrestamo(), prestamoRequest.getBooks());
        } catch (Exception e) {
            e.printStackTrace();
            return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{idPrestamo}/{statusPrestamo}")
    public ResponseEntity<?> updateSatusBook(@PathVariable Long idPrestamo, @PathVariable String statusPrestamo) {
        try {
            return prestamoService.updateStatusPrestamo(idPrestamo, statusPrestamo);
        } catch (Exception e) {
            e.printStackTrace();
            return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/user/{idUser}")
    public ResponseEntity<?> findPrestamoByIdUser(@PathVariable Long idUser) {
        try {
            Collection<Prestamo> prestamos = prestamoService.findPrestamoByIdUser(idUser);
            return new ResponseEntity<>(prestamos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/delete/{idPrestamo}")
    public ResponseEntity<?> deletePrestamo(@PathVariable Long idPrestamo) {
        try {
            return prestamoService.deletePrestamo(idPrestamo);
        } catch (Exception e) {
            e.printStackTrace();
            return UtilsLibrary.getResponseEntity(LibraryConstants.AN_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
