package com.java_avanade.controllers;

import com.java_avanade.middlewares.LogService;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.java_avanade.exceptions.EntityNotFoundException;
/**
 * Exemplo de controller com implementação de logs.
 */
@RestController
@RequestMapping("/api/exemplo")
public class ExampleController {

    private static final Logger logger = LogService.getLogger(ExampleController.class);

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            LogService.logInfo(logger, "Buscando todos os registros");

            // Lógica de negócio aqui

            LogService.logInfo(logger, "Registros retornados com sucesso");
            return ResponseEntity.ok("Sucesso");
        } catch (Exception e) {
            LogService.logError(logger, "Erro ao buscar registros", e);
            throw e; // Será capturado pelo GlobalExceptionHandler
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        LogService.logInfo(logger, "Buscando registro com ID: " + id);

        try {
            // Exemplo de validação com log
            if (id <= 0) {
                LogService.logWarn(logger, "ID inválido fornecido: " + id);
                throw new IllegalArgumentException("ID deve ser um número positivo");
            }

            // Exemplo de registro não encontrado
            if (id == 999) {
                LogService.logWarn(logger, "Registro não encontrado para ID: " + id);
                throw new EntityNotFoundException("Registro com ID " + id + " não encontrado");
            }

            // Lógica de negócio bem-sucedida
            LogService.logDebug(logger, "Dados do registro: {...}");
            LogService.logInfo(logger, "Registro retornado com sucesso");

            return ResponseEntity.ok("Sucesso");
        } catch (Exception e) {
            // Registramos o erro, mas deixamos o GlobalExceptionHandler tratar
            LogService.logError(logger, "Erro ao buscar registro com ID: " + id, e);
            throw e;
        }
    }
}