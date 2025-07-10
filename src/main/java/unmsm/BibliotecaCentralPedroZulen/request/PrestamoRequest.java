package unmsm.BibliotecaCentralPedroZulen.request;

import unmsm.BibliotecaCentralPedroZulen.model.Book;
import unmsm.BibliotecaCentralPedroZulen.model.Prestamo;

import java.util.List;

public class PrestamoRequest {
    private Prestamo prestamo;
    private List<Book> books;

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}

