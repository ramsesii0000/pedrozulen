package unmsm.BibliotecaCentralPedroZulen.service;

import unmsm.BibliotecaCentralPedroZulen.Utils.UtilsLibrary;
import unmsm.BibliotecaCentralPedroZulen.constants.LibraryConstants;
import unmsm.BibliotecaCentralPedroZulen.model.Book;
import unmsm.BibliotecaCentralPedroZulen.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import unmsm.BibliotecaCentralPedroZulen.repository.CategoryRepository;
import java.util.*;
import java.util.Optional;
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BookService bookService;

    public Collection<Category> findAllCategories() {
        return (Collection<Category>) categoryRepository.findAll();
    }

    public ResponseEntity<String> saveCategory(Category category) {

        if(!validateCategoryData(category)){
            return UtilsLibrary.getResponseEntity(LibraryConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }

        Category existingCategory = categoryRepository.findByNameCategory(category.getNameCategory());
        if(existingCategory != null){
            return UtilsLibrary.getResponseEntity("Esta categoria ya existe",HttpStatus.BAD_REQUEST);
        }

        categoryRepository.save(category);
        return UtilsLibrary.getResponseEntity("La categoria fue registrada con exito", HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateCategory(@PathVariable Long categoryId, @RequestBody Category categoryUpdate){
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isEmpty()){
            return UtilsLibrary.getResponseEntity("No existe la categoria con id: "+categoryId,HttpStatus.BAD_REQUEST);
        }

        if(!validateCategoryData(categoryUpdate)){
            return UtilsLibrary.getResponseEntity(LibraryConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }

        Category existingCategory = categoryRepository.findByNameCategoryAndIdCategoryNot(categoryUpdate.getNameCategory(), categoryId);
        if(existingCategory != null){
            return UtilsLibrary.getResponseEntity("Esta categoria ya existe",HttpStatus.BAD_REQUEST);
        }

        category.get().setNameCategory(categoryUpdate.getNameCategory());
        categoryRepository.save(category.get());
        return UtilsLibrary.getResponseEntity("La categoria fue actualizada con exito", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteCategory(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty()) {
            return UtilsLibrary.getResponseEntity("No existe la categoría con id: " + categoryId, HttpStatus.BAD_REQUEST);
        }
        Category category = categoryOptional.get();
        Collection<Book> books = bookService.findByCategory(categoryId);
        for (Book book : books) {
            bookService.deleteBook(book.getIdBook());
        }
        categoryRepository.delete(category);

        return UtilsLibrary.getResponseEntity("La categoría fue eliminada con éxito", HttpStatus.OK);
    }

    public static boolean validateCategoryData(Category category) {
        return category.getNameCategory() != null && !category.getNameCategory().isEmpty();
    }
}