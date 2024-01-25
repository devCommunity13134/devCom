package devcom.main.domain.category.service;

import devcom.main.domain.category.entity.Category;
import devcom.main.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Category getCategory(String category){
        Optional<Category> optionalCategory = this.categoryRepository.findByCategoryName(category);
        if(optionalCategory.isEmpty()){
            throw new RuntimeException();
        }
        return optionalCategory.get();
    }


}
