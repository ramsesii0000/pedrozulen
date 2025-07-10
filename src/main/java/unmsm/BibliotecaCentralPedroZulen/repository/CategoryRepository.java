package unmsm.BibliotecaCentralPedroZulen.repository;


import unmsm.BibliotecaCentralPedroZulen.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByNameCategory(String nameCategory);
    Category findByNameCategoryAndIdCategoryNot (String nameCategory, Long idCategory);
}
