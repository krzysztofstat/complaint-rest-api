CREATE TABLE customer (
    id VARCHAR(36) NOT NULL,
    email VARCHAR(255),
    create_date DATETIME NOT NULL,
    modify_date DATETIME,
    version INTEGER,
    PRIMARY KEY (id)
);

CREATE TABLE product (
    id VARCHAR(36) NOT NULL,
    product_name VARCHAR(255),
    price DECIMAL(19,2),
    create_date DATETIME NOT NULL,
    modify_date DATETIME,
    version INTEGER,
    PRIMARY KEY (id)
);

CREATE TABLE complaint (
    id VARCHAR(36) NOT NULL,
    description VARCHAR(255) NOT NULL,
    product_id VARCHAR(36) NOT NULL,
    customer_id VARCHAR(36) NOT NULL,
    complaint_country VARCHAR(255),
    complaint_count BIGINT NOT NULL,
    create_date DATETIME NOT NULL,
    modify_date DATETIME,
    version INTEGER,
    PRIMARY KEY (id),
    CONSTRAINT fk_complaint_product FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT fk_complaint_customer FOREIGN KEY (customer_id) REFERENCES customer(id),
    CONSTRAINT uk_product_customer UNIQUE (product_id, customer_id)
);

CREATE INDEX idx_customer_create_date ON customer(create_date) USING BTREE;

CREATE INDEX idx_product_create_date ON product(create_date) USING BTREE;

CREATE INDEX idx_complaint_create_date ON complaint(create_date) USING BTREE;

CREATE INDEX idx_complaint_product_id ON complaint(product_id) USING BTREE;
CREATE INDEX idx_complaint_customer_id ON complaint(customer_id) USING BTREE;