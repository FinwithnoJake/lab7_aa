package server.repo.auth.exceptions;

/**
 * Исключение, которое возникает при неверных учетных данных пользователя
 */
public class BadCredentialsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Создает новое исключение с указанным сообщением
     * @param message описание ошибки
     */
    public BadCredentialsException(String message) {
        super(message);
    }

    /**
     * Создает новое исключение с сообщением по умолчанию
     */
    public BadCredentialsException() {
        this("Неверные учетные данные");
    }

    /**
     * Возвращает код ошибки
     * @return код ошибки
     */
    public int getErrorCode() {
        return 401; // Unauthorized
    }

    /**
     * Возвращает подробное сообщение об ошибке
     * @return подробное сообщение
     */
    public String getDetailedMessage() {
        return "Попытка авторизации с неверными учетными данными. " +
                "Проверьте правильность введенного логина и пароля.";
    }
}
