-- Esquema de banco de dados para testes
-- Cria as tabelas necessárias para o sistema de fraldas ecológicas

-- Tabela de Afiliados (Fornecedores)
CREATE TABLE IF NOT EXISTS affiliates (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
    );

-- Tabela de Produtos
CREATE TABLE IF NOT EXISTS products (
                                        product_code BIGINT PRIMARY KEY,
                                        product_choice VARCHAR(255) NOT NULL,
    product_type VARCHAR(255) NOT NULL,
    affiliate_id BIGINT,
    FOREIGN KEY (affiliate_id) REFERENCES affiliates(id)
    );

-- Tabela de Estoques
CREATE TABLE IF NOT EXISTS stocks (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      product_code BIGINT NOT NULL,
                                      quantity INTEGER NOT NULL,
                                      FOREIGN KEY (product_code) REFERENCES products(product_code)
    );

-- Tabela de Clientes
CREATE TABLE IF NOT EXISTS clients (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
    );

-- Tabela de Pedidos
CREATE TABLE IF NOT EXISTS orders (
                                      order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      client_id BIGINT,
                                      FOREIGN KEY (client_id) REFERENCES clients(id)
    );

-- Tabela de Carrinho
CREATE TABLE IF NOT EXISTS carts (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     order_id BIGINT NOT NULL,
                                     product_code BIGINT NOT NULL,
                                     payment_type VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_code) REFERENCES products(product_code)
    );

-- Tabela de Checkout (produtos finalizados)
CREATE TABLE IF NOT EXISTS checkouts (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         order_id BIGINT NOT NULL,
                                         product_code BIGINT NOT NULL,
                                         quantity INTEGER NOT NULL,
                                         FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_code) REFERENCES products(product_code)
    );

-- Tabela de Usuários
CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
    );

-- Tabela de Papéis de Usuário
CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id BIGINT NOT NULL,
                                          role VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
    );

-- Criação de índices para melhorar a performance
CREATE INDEX idx_products_affiliate ON products(affiliate_id);
CREATE INDEX idx_stocks_product ON stocks(product_code);
CREATE INDEX idx_orders_client ON orders(client_id);
CREATE INDEX idx_carts_order ON carts(order_id);
CREATE INDEX idx_carts_product ON carts(product_code);
CREATE INDEX idx_checkouts_order ON checkouts(order_id);
CREATE INDEX idx_checkouts_product ON checkouts(product_code);
CREATE INDEX idx_user_roles_user ON user_roles(user_id);