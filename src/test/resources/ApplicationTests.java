package com.java_avanade;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Testes de integração que verificam se o contexto da aplicação Spring carrega corretamente.
 * Esta classe é executada como parte dos testes de integração para garantir que todos
 * os componentes, configurações e beans Spring são inicializados corretamente.
 */
@SpringBootTest
@ActiveProfiles("test") // Ativa o perfil "test" para usar configurações específicas de teste
public class ApplicationTests {

	/**
	 * Testa se o contexto da aplicação Spring é carregado com sucesso.
	 * Este teste falha se houver problemas na configuração do Spring, como beans
	 * com definições conflitantes, problemas de injeção de dependência, etc.
	 */
	@Test
	void contextLoads() {
		// O teste passa se o contexto da aplicação carregar sem erros
		// Não é necessário adicionar asserções aqui - o Spring faz isso automaticamente
	}

	/**
	 * Este teste pode ser expandido para verificar outros aspectos da aplicação.
	 * Por exemplo, você pode adicionar testes para verificar se certos beans
	 * são criados corretamente, se a configuração de banco de dados funciona,
	 * se os endpoints da API estão ativos, etc.
	 */
	@Test
	void applicationStartsSuccessfully() {
		// Este teste também é bem-sucedido se a aplicação iniciar corretamente
		// É um bom lugar para adicionar verificações básicas de sanidade
	}

	// Você pode adicionar mais testes aqui conforme necessário para
	// verificar outros aspectos da inicialização da sua aplicação
}