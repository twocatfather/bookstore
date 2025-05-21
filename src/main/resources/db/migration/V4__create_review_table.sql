CREATE TABLE IF NOT EXISTS review (
                                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                      content LONGTEXT NOT NULL,
                                      rating INT CHECK (rating BETWEEN 1 AND 5),
    user_id BIGINT,
    book_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
    );