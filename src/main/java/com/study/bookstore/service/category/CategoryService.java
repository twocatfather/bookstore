package com.study.bookstore.service.category;

import com.study.bookstore.domain.book.Category;
import com.study.bookstore.infrastructure.category.entity.CategoryJpaEntity;
import com.study.bookstore.infrastructure.category.repository.CategoryJpaRepository;
import com.study.bookstore.service.category.dto.CategoryRequest;
import com.study.bookstore.service.category.dto.CategoryResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryJpaRepository categoryJpaRepository;

    public CategoryResponse createCategory(CategoryRequest request) {
        Category domain = request.toDomain();
        CategoryJpaEntity categoryJpaEntity = CategoryJpaEntity.from(domain);
        return CategoryResponse.from(categoryJpaRepository.save(categoryJpaEntity));
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryJpaRepository.findAll()
                .stream()
                .map(CategoryResponse::from)
                .toList();
    }

    // getCategoryById
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long categoryId) {
        CategoryJpaEntity categoryJpaEntity = categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾을 수 없습니다."));
        return CategoryResponse.from(categoryJpaEntity);
    }

    // updateCategory
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        CategoryJpaEntity categoryJpaEntity = categoryJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾을 수 없습니다."));

        categoryJpaEntity.updateName(request.name());
        categoryJpaEntity.updateCode(request.code());

        return CategoryResponse.from(categoryJpaEntity);
    }

    // deleteCategory
    public void deleteCategory(Long id) {
        if (!categoryJpaRepository.existsById(id)) {
            throw new EntityNotFoundException("카테고리를 찾을 수 없습니다.");
        }
        categoryJpaRepository.deleteById(id);
    }

    public List<CategoryResponse> createCategories(List<CategoryRequest> requests) {
        List<CategoryJpaEntity> categories = requests.stream()
                .map(request -> CategoryJpaEntity.builder()
                        .name(request.name())
                        .code(request.code())
                        .build())
                .toList();

        List<CategoryJpaEntity> categoryJpaEntities = categoryJpaRepository.saveAll(categories);
        return categoryJpaEntities.stream()
                .map(CategoryResponse::from)
                .toList();
    }

}
