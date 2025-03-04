package com.java_avanade.config;

import com.java_avanade.entities.*;
import com.java_avanade.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AffiliateRepository affiliateRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository, AffiliateRepository affiliateRepository,
                      ProductRepository productRepository, StockRepository stockRepository,
                      ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.affiliateRepository = affiliateRepository;
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Criar usuários iniciais
        createUsers();

        // Criar afiliados
        Affiliate affiliate1 = createAffiliate("Eco Fraldas Ltda", "contato@ecofraldas.com");
        Affiliate affiliate2 = createAffiliate("Fraldas Naturais S.A.", "vendas@fraldasnaturais.com");

        // Criar produtos
        Product product1 = createProduct(101L, "Premium", "Recém-nascido", affiliate1);
        Product product2 = createProduct(102L, "Standard", "Recém-nascido", affiliate1);
        Product product3 = createProduct(103L, "Premium", "Infantil", affiliate2);
        Product product4 = createProduct(104L, "Standard", "Infantil", affiliate2);

        // Criar estoque
        createStock(product1, 50);
        createStock(product2, 75);
        createStock(product3, 30);
        createStock(product4, 60);

        // Criar clientes
        createClient("Maria Silva", "maria@example.com");
        createClient("João Santos", "joao@example.com");
    }

    private void createUsers() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            Set<String> adminRoles = new HashSet<>();
            adminRoles.add("ADMIN");
            adminRoles.add("USER");
            admin.setRoles(adminRoles);
            userRepository.save(admin);
        }

        if (!userRepository.existsByUsername("user")) {
            User user = new User();
            user.setUsername("user");
            user.setEmail("user@example.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRoles(Collections.singleton("USER"));
            userRepository.save(user);
        }
    }

    private Affiliate createAffiliate(String name, String email) {
        Affiliate affiliate = new Affiliate();
        affiliate.setName(name);
        affiliate.setEmail(email);
        affiliateRepository.save(affiliate);
        return affiliate;
    }

    private Product createProduct(Long code, String choice, String type, Affiliate affiliate) {
        Product product = new Product();
        product.setProductCode(code);
        product.setProductChoice(choice);
        product.setProductType(type);
        product.setAffiliate(affiliate);
        productRepository.save(product);
        return product;
    }

    private void createStock(Product product, Integer quantity) {
        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setQuantity(quantity);
        stockRepository.save(stock);
    }

    private void createClient(String name, String email) {
        Client client = new Client();
        client.setName(name);
        client.setEmail(email);
        clientRepository.save(client);
    }
}