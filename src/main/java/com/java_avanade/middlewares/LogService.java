package com.java_avanade.middlewares;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Serviço utilitário para logging na aplicação.
 * Fornece métodos para diferentes níveis de log.
 */
@Service
public class LogService {

    /**
     * Obtém um logger para a classe especificada.
     *
     * @param clazz A classe para qual o logger será criado
     * @return Logger configurado
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * Loga uma mensagem de erro com exceção.
     *
     * @param logger O logger a ser usado
     * @param message A mensagem de erro
     * @param ex A exceção relacionada
     */
    public static void logError(Logger logger, String message, Throwable ex) {
        logger.error(message, ex);
    }

    /**
     * Loga uma mensagem de erro sem exceção.
     *
     * @param logger O logger a ser usado
     * @param message A mensagem de erro
     */
    public static void logError(Logger logger, String message) {
        logger.error(message);
    }

    /**
     * Loga uma mensagem de aviso.
     *
     * @param logger O logger a ser usado
     * @param message A mensagem de aviso
     */
    public static void logWarn(Logger logger, String message) {
        logger.warn(message);
    }

    /**
     * Loga uma mensagem informativa.
     *
     * @param logger O logger a ser usado
     * @param message A mensagem informativa
     */
    public static void logInfo(Logger logger, String message) {
        logger.info(message);
    }

    /**
     * Loga uma mensagem de debug.
     *
     * @param logger O logger a ser usado
     * @param message A mensagem de debug
     */
    public static void logDebug(Logger logger, String message) {
        logger.debug(message);
    }
}