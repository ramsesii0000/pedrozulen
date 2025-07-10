package unmsm.BibliotecaCentralPedroZulen.service;

import unmsm.BibliotecaCentralPedroZulen.Utils.UtilsLibrary;
import unmsm.BibliotecaCentralPedroZulen.model.Book;
import unmsm.BibliotecaCentralPedroZulen.model.DetailsPrestamo;
import unmsm.BibliotecaCentralPedroZulen.model.Prestamo;
import unmsm.BibliotecaCentralPedroZulen.repository.BookRepository;
import unmsm.BibliotecaCentralPedroZulen.repository.DetailsPrestamoRepository;
import unmsm.BibliotecaCentralPedroZulen.repository.PrestamoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService {

    @Autowired
    public PrestamoRepository prestamoRepository;

    @Autowired
    public DetailsPrestamoRepository detailsPrestamoRepository;

    @Autowired
    public BookRepository bookRepository;

    public Collection<Prestamo> getAllPrestamos(){
        return (Collection<Prestamo>) prestamoRepository.findByDeletePrestamo(false);
    }

    @Transactional
    public ResponseEntity<String> savePrestamoWithDetail(Prestamo prestamo, List<Book> books) {
        if (books == null || books.isEmpty()) {
            return UtilsLibrary.getResponseEntity("La lista de libros está vacía", HttpStatus.BAD_REQUEST);
        }

        prestamoRepository.save(prestamo);

        for (Book book : books) {
            DetailsPrestamo detailPrestamo = new DetailsPrestamo();
            detailPrestamo.setPrestamo(prestamo);
            detailPrestamo.setBook(book);
            detailsPrestamoRepository.save(detailPrestamo);

            book.setStatusBook("No Disponible");
            bookRepository.save(book);
        }

        return UtilsLibrary.getResponseEntity("El préstamo fue guardado con éxito", HttpStatus.CREATED);
    }


    public Collection<Prestamo> findPrestamoByIdUser(Long idUser){
        return (Collection<Prestamo>) prestamoRepository.findByUser_IdUser(idUser);
    }

    public ResponseEntity<?> findPrestamoByIdPrestamo (Long idPrestamo){
        Optional<Prestamo> prestamo = prestamoRepository.findById(idPrestamo);
        if(prestamo.isPresent()){
            return new ResponseEntity<>(prestamo, HttpStatus.OK);
        }else{
            return UtilsLibrary.getResponseEntity("No existe el prestamo con id: ", HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<?> deletePrestamo(@PathVariable Long idPrestamo){
        Optional<Prestamo> prestamoOptional = prestamoRepository.findById(idPrestamo);
        if (prestamoOptional.isEmpty()) {
            return UtilsLibrary.getResponseEntity("No existe el prestamo con id: " + idPrestamo, HttpStatus.BAD_REQUEST);
        } else {
            Prestamo prestamo = prestamoOptional.get();
            prestamo.setDeletePrestamo(true);
            prestamoRepository.save(prestamo);
            return UtilsLibrary.getResponseEntity("El prestamo fue eliminado con exito", HttpStatus.OK);
        }
    }

    public ResponseEntity<?> updateStatusPrestamo(Long idPrestamo, String statusPrestamo){
        Optional<Prestamo> prestamoOptional = prestamoRepository.findById(idPrestamo);

        if (prestamoOptional.isPresent()) {
            Prestamo prestamo = prestamoOptional.get();
            prestamo.setStatusPrestamo(statusPrestamo);
            prestamoRepository.save(prestamo);

            if(statusPrestamo.equals("Devuelto")) {
                List<DetailsPrestamo> detailsPrestamosList = detailsPrestamoRepository.findByPrestamo_IdPrestamo(idPrestamo);
                for (DetailsPrestamo detail : detailsPrestamosList) {
                    Book book = detail.getBook();
                    book.setStatusBook("Disponible");
                    bookRepository.save(book);
                }
                return UtilsLibrary.getResponseEntity("Prestamo actualizado con éxito, los libros fueron devueltos", HttpStatus.OK);
            } else {
                List<DetailsPrestamo> detailsPrestamosList = detailsPrestamoRepository.findByPrestamo_IdPrestamo(idPrestamo);
                for (DetailsPrestamo detail : detailsPrestamosList) {
                    Book book = detail.getBook();
                    book.setStatusBook("No Disponible");
                    bookRepository.save(book);
                }
                return UtilsLibrary.getResponseEntity("Prestamo actualizado con éxito, los libros están no disponibles", HttpStatus.OK);
            }
        } else {
            return UtilsLibrary.getResponseEntity("No se encontró el préstamo con el ID proporcionado", HttpStatus.NOT_FOUND);
        }
    }



}
