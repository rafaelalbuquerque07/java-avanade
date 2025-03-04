-- Populando dados de teste para o sistema de fraldas ecológicas baseado no diagrama de classes

-- Inserindo Afiliados (Fornecedores)
INSERT INTO affiliates (id, name, email) VALUES
                                             (1, 'EcoBaby Fornecedores', 'contato@ecobaby.com'),
                                             (2, 'Fraldas Sustentáveis Ltda', 'vendas@fraldassustentaveis.com'),
                                             (3, 'Green Diapers Brasil', 'comercial@greendiapers.com.br'),
                                             (4, 'Bebê Ecológico', 'fornecedor@bebeecologico.com'),
                                             (5, 'Mamãe Natureza', 'atacado@mamaenatureza.com');

-- Inserindo Produtos
INSERT INTO products (product_code, product_choice, product_type, affiliate_id) VALUES
                                                                                    (10001, 'Fralda de Pano Básica', 'Pano', 1),
                                                                                    (10002, 'Fralda Ecológica Premium', 'Pano', 1),
                                                                                    (10003, 'Fralda de Bambu', 'Bambu', 2),
                                                                                    (10004, 'Fralda de Algodão Orgânico', 'Algodão', 2),
                                                                                    (10005, 'Fralda Reutilizável Noturna', 'Pano', 3),
                                                                                    (10006, 'Absorvente para Fralda', 'Acessório', 3),
                                                                                    (10007, 'Fralda de Cânhamo', 'Cânhamo', 4),
                                                                                    (10008, 'Capa Impermeável para Fralda', 'Acessório', 4),
                                                                                    (10009, 'Kit Iniciante Fraldas Ecológicas', 'Kit', 5),
                                                                                    (10010, 'Fralda de Lã Merino', 'Lã', 5);

-- Inserindo Estoques
INSERT INTO stocks (id, product_code, quantity) VALUES
                                                    (1, 10001, 150),
                                                    (2, 10002, 100),
                                                    (3, 10003, 80),
                                                    (4, 10004, 120),
                                                    (5, 10005, 90),
                                                    (6, 10006, 200),
                                                    (7, 10007, 70),
                                                    (8, 10008, 110),
                                                    (9, 10009, 50),
                                                    (10, 10010, 60);

-- Inserindo Clientes
INSERT INTO clients (id, name, email) VALUES
                                          (1, 'Maria Silva', 'maria.silva@email.com'),
                                          (2, 'João Pereira', 'joao.pereira@email.com'),
                                          (3, 'Ana Oliveira', 'ana.oliveira@email.com'),
                                          (4, 'Carlos Santos', 'carlos.santos@email.com'),
                                          (5, 'Juliana Lima', 'juliana.lima@email.com'),
                                          (6, 'Roberto Almeida', 'roberto.almeida@email.com'),
                                          (7, 'Fernanda Costa', 'fernanda.costa@email.com'),
                                          (8, 'Pedro Souza', 'pedro.souza@email.com'),
                                          (9, 'Camila Rodrigues', 'camila.rodrigues@email.com'),
                                          (10, 'Lucas Ferreira', 'lucas.ferreira@email.com');

-- Inserindo Pedidos
INSERT INTO orders (order_id, client_id) VALUES
                                             (1, 1), -- Pedido 1: Maria Silva
                                             (2, 2), -- Pedido 2: João Pereira
                                             (3, 3), -- Pedido 3: Ana Oliveira
                                             (4, 1), -- Pedido 4: Maria Silva (segundo pedido)
                                             (5, 4), -- Pedido 5: Carlos Santos
                                             (6, 5), -- Pedido 6: Juliana Lima
                                             (7, 6), -- Pedido 7: Roberto Almeida
                                             (8, 7), -- Pedido 8: Fernanda Costa
                                             (9, 8), -- Pedido 9: Pedro Souza
                                             (10, 9); -- Pedido 10: Camila Rodrigues

-- Inserindo itens no Carrinho
INSERT INTO carts (id, order_id, product_code, payment_type, quantity) VALUES
                                                                           (1, 1, 10001, 'Cartão de Crédito', 3),
                                                                           (2, 1, 10006, 'Cartão de Crédito', 5),
                                                                           (3, 2, 10003, 'Boleto', 2),
                                                                           (4, 2, 10008, 'Boleto', 1),
                                                                           (5, 3, 10009, 'Pix', 1),
                                                                           (6, 4, 10002, 'Cartão de Débito', 4),
                                                                           (7, 5, 10004, 'Cartão de Crédito', 2),
                                                                           (8, 5, 10005, 'Cartão de Crédito', 1),
                                                                           (9, 6, 10007, 'Pix', 3),
                                                                           (10, 7, 10010, 'Boleto', 2),
                                                                           (11, 8, 10001, 'Cartão de Crédito', 5),
                                                                           (12, 9, 10009, 'Pix', 1),
                                                                           (13, 10, 10003, 'Cartão de Débito', 3);

-- Inserindo produtos finalizados (Checkout)
INSERT INTO checkouts (id, order_id, product_code, quantity) VALUES
                                                                 (1, 1, 10001, 3),
                                                                 (2, 1, 10006, 5),
                                                                 (3, 2, 10003, 2),
                                                                 (4, 2, 10008, 1),
                                                                 (5, 3, 10009, 1),
                                                                 (6, 4, 10002, 4),
                                                                 (7, 5, 10004, 2),
                                                                 (8, 5, 10005, 1),
                                                                 (9, 6, 10007, 3);

-- Inserindo Usuários
INSERT INTO users (id, username, password, email) VALUES
                                                      (1, 'admin', '$2a$10$XqnUClbIYhJ2hXLxH7vE6eGGdcBNVwD.L8yjYpS5Z5GhGVklZ3Twe', 'admin@fraldas.com'), -- senha: admin123
                                                      (2, 'vendedor1', '$2a$10$b9kDRUIDCZiPzFzO/GcUBuphkRaKXA2Urb/3OOPg0gYGOlHF7XsYu', 'vendedor1@fraldas.com'), -- senha: venda123
                                                      (3, 'estoque1', '$2a$10$9l4TH7tHm5bJ0X6PVdR6gue5BQe3hm6nHNtJ9ZCPS8DHZLOWPVlc.', 'estoque1@fraldas.com'), -- senha: estoque123
                                                      (4, 'cliente1', '$2a$10$T62AUJ1m3KJ9BsYRzGRz0O1W3mD7qkv1zTe9QD.wQ8LIBcXSGYHcu', 'cliente1@fraldas.com'), -- senha: cliente123
                                                      (5, 'gerente1', '$2a$10$lrL.8WlHBHIY1UMbXjLH9eAjYUSZ2vwNQkd/hqCNwFPH5TK2O15X.', 'gerente1@fraldas.com'); -- senha: gerente123

-- Inserindo Papéis de Usuário
INSERT INTO user_roles (user_id, role) VALUES
                                           (1, 'ADMIN'),
                                           (2, 'VENDOR'),
                                           (3, 'STOCK_MANAGER'),
                                           (4, 'CLIENT'),
                                           (5, 'MANAGER'),
                                           (1, 'MANAGER'), -- admin também tem papel de gerente
                                           (5, 'VENDOR'); -- gerente também tem papel de vendedor