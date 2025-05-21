package com.study.bookstore.data;

import com.study.bookstore.infrastructure.book.entity.BookJpaEntity;
import com.study.bookstore.infrastructure.book.repository.BookJpaRepository;
import com.study.bookstore.infrastructure.category.entity.CategoryJpaEntity;
import com.study.bookstore.infrastructure.category.repository.CategoryJpaRepository;
import com.study.bookstore.infrastructure.user.entity.UserJpaEntity;
import com.study.bookstore.infrastructure.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
    private final CategoryJpaRepository categoryRepository;
    private final BookJpaRepository bookRepository;
    private final UserJpaRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (isDatabaseEmpty()) {
            log.info("Database is empty. Initializing data...");

            Map<String, CategoryJpaEntity> categories = initializeCategories();
            initializeBooks(categories);
            initializeUsers();

            log.info("Data initialization complete");
        }
    }

    private boolean isDatabaseEmpty() {
        return categoryRepository.count() == 0 &&
                bookRepository.count() == 0 &&
                userRepository.count() == 0;
    }

    private Map<String, CategoryJpaEntity> initializeCategories() {
        List<CategoryJpaEntity> categories = new ArrayList<>();

        categories.add(CategoryJpaEntity.builder().name("소설").code("NOVEL").build());
        categories.add(CategoryJpaEntity.builder().name("교육").code("EDUCATION").build());
        categories.add(CategoryJpaEntity.builder().name("컴퓨터/IT").code("COMPUTER").build());
        categories.add(CategoryJpaEntity.builder().name("과학").code("SCIENCE").build());
        categories.add(CategoryJpaEntity.builder().name("자기계발").code("SELF_HELP").build());
        categories.add(CategoryJpaEntity.builder().name("경제/경영").code("BUSINESS").build());

        List<CategoryJpaEntity> savedCategories = categoryRepository.saveAll(categories);
        log.info("카테고리 {}개 생성 완료", savedCategories.size());

        // 코드를 키로 하는 맵 생성
        Map<String, CategoryJpaEntity> categoryMap = new HashMap<>();
        savedCategories.forEach(category -> categoryMap.put(category.getCode(), category));

        return categoryMap;
    }

    private void initializeBooks(Map<String, CategoryJpaEntity> categories) {
        List<BookJpaEntity> books = new ArrayList<>();

        // 소설 카테고리
        books.add(createBook("김영하", "살인자의 기억법", "9788954645055", 13000, 50, categories.get("NOVEL")));
        books.add(createBook("한강", "채식주의자", "9788936433598", 12000, 30, categories.get("NOVEL")));

        // 컴퓨터/IT 카테고리
        books.add(createBook("김영한", "스프링 부트와 JPA 활용", "9791162241479", 32000, 100, categories.get("COMPUTER")));
        books.add(createBook("조슈아 블로크", "이펙티브 자바", "9788966262281", 36000, 80, categories.get("COMPUTER")));

        // 자기계발 카테고리
        books.add(createBook("데일 카네기", "인간관계론", "9788901054175", 15000, 40, categories.get("SELF_HELP")));
        books.add(createBook("스티븐 코비", "성공하는 사람들의 7가지 습관", "9788934976622", 16000, 60, categories.get("SELF_HELP")));

        List<BookJpaEntity> savedBooks = bookRepository.saveAll(books);
        log.info("도서 {}권 생성 완료", savedBooks.size());
    }

    private BookJpaEntity createBook(String author, String title, String isbn,
                                     Integer price, Integer stock, CategoryJpaEntity category) {
        return BookJpaEntity.builder()
                .author(author)
                .title(title)
                .isbn(isbn)
                .price(price)
                .stockQuantity(stock)
                .category(category)
                .build();
    }

    private void initializeUsers() {
        List<UserJpaEntity> users = new ArrayList<>();

        users.add(createUser("user1", "user1@example.com", "password123"));
        users.add(createUser("user2", "user2@example.com", "password123"));
        users.add(createUser("admin", "admin@example.com", "adminpass"));

        List<UserJpaEntity> savedUsers = userRepository.saveAll(users);
        log.info("사용자 {}명 생성 완료", savedUsers.size());
    }

    private UserJpaEntity createUser(String username, String email, String password) {
        return UserJpaEntity.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
