package devcom.main.domain.category.service;

import devcom.main.domain.category.entity.Category;
import devcom.main.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    //category create
    public void create(String categoryName){
        Category category = Category.builder()
                .categoryName(categoryName)
                .build();

        this.categoryRepository.save(category);
    }

    //category modify
    public void modify(Category category, String categoryName){
       Category category1 = category.toBuilder()
               .categoryName(categoryName)
               .build();

       this.categoryRepository.save(category1);
    }

    //category delete
    public void delete(Category category){
        this.categoryRepository.delete(category);
    }
}
