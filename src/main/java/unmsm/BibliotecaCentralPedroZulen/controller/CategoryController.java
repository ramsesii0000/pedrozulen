package unmsm.BibliotecaCentralPedroZulen.controller;

import unmsm.BibliotecaCentralPedroZulen.model.Category;
import unmsm.BibliotecaCentralPedroZulen.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<Collection<Category>> getAllCategories() {
        try {
            Collection<Category> categories = categoryService.findAllCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/all/user")
    public ResponseEntity<Collection<Category>> getAllCategoriesUser() {
        try {
            Collection<Category> categories = categoryService.findAllCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        try {
            ResponseEntity<String> response = categoryService.saveCategory(category);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                return ResponseEntity.ok("Categoría creada exitosamente");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la categoría");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la categoría");
        }
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId, @RequestBody Category categoryUpdate) {
        try {
            ResponseEntity<?> response = categoryService.updateCategory(categoryId, categoryUpdate);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Categoría actualizada exitosamente");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la categoría");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la categoría");
        }
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        try {
            ResponseEntity<?> response = categoryService.deleteCategory(categoryId);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Categoría eliminada exitosamente");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la categoría");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la categoría");
        }
    }
}

