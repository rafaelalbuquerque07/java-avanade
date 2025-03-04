package com.java_avanade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Configuração específica para o banco de dados de teste H2.
 * Esta classe é ativada apenas quando o perfil "test" está ativo.
 */
@Configuration
@Profile("test")
public class TestConfig {

    /**
     * Cria um banco de dados H2 em memória para testes.
     * Esta configuração garante que tenhamos um banco de dados limpo para cada execução de teste.
     *
     * @return DataSource configurado para banco H2 em memória
     */
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("testdb-" + System.currentTimeMillis()) // Nome único para evitar conflitos
                .addScript("classpath:schema-test.sql") // Opcional: script para criação de tabelas
                .addScript("classpath:data-test.sql")   // Opcional: script para dados iniciais
                .build();
    }

    // Você pode adicionar outros beans específicos para o ambiente de teste aqui
}