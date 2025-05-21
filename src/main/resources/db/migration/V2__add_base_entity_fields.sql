ALTER TABLE category
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                                                                                                              ADD COLUMN is_deleted BOOLEAN NOT NULL DEFAULT FALSE;

UPDATE category SET
                    created_at = CURRENT_TIMESTAMP,
                    updated_at = CURRENT_TIMESTAMP,
                    is_deleted = false;