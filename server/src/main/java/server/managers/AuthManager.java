package server.managers;

import com.google.common.hash.Hashing;
import common.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import server.ServerApp;
import server.repo.auth.AuthRepository;
import server.repo.auth.exceptions.UserNotExistException;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

public class AuthManager {

    private static final int SALT_LENGTH = 10;
    private final String pepper;
    private final Logger logger = ServerApp.logger;
    private final AuthRepository authRepository;

    public AuthManager(Connection connection, String pepper) {
        this.pepper = pepper;
        this.authRepository = new AuthRepository(connection);
    }

    public int registerUser(String login, String password) throws SQLException {
        logger.info("Создание нового бро{}", login);

        // Проверка на пустые значения
        if (login == null || password == null) {
            throw new IllegalArgumentException("Логин и пароль не могут быть пустыми");
        }

        // Проверка уникальности логина

        // Генерация соли и хеша
        var salt = generateSalt();
        var passwordHash = generatePasswordHash(password, salt);

        // Создание пользователя
        var user = new User();
        user.setName(login);
        user.setPassword(passwordHash);
        user.setSalt(salt);

        int id = authRepository.addNewUser(user);
        logger.info("БрО успешно сОздАн, id= {}", id);
        return id;
    }

    public int authenticateUser(String login, String password) throws SQLException {
        logger.info("Аутентификация бро {}", login);

        // Проверка на пустые значения
        if (login == null || password == null) {
            throw new IllegalArgumentException("Логин и пароль не могут быть пустыми");
        }

        try {
            User user = authRepository.findUserByUsername(login);
            if (user == null) {
                throw new UserNotExistException("Бро не существует");
            }

            var id = user.getId();
            var salt = user.getSalt();
            var expectedHashedPassword = user.getPassword();
            var actualHashedPassword = generatePasswordHash(password, salt);

            if (expectedHashedPassword.equals(actualHashedPassword)) {
                logger.info("Бро {} успешно аутентифицирован, ID: {}", login, id);
                return id;
            } else {
                logger.warn("Неудачная попытка аутентификации для бро {}", login);
            }
        } catch (UserNotExistException e) {
            logger.warn("Бро {} не найден", login);
            throw e;
        }

        return -1;
    }

    private String generateSalt() {
        return RandomStringUtils.randomAlphanumeric(SALT_LENGTH);
    }

    private String generatePasswordHash(String password, String salt) {
        try {
            return Hashing.sha256()
                    .hashString(pepper + password + salt, StandardCharsets.UTF_8)
                    .toString();
        } catch (Exception e) {
            logger.error("Ошибка при хешировании пароля", e);
            throw new RuntimeException(e);
        }
    }
}
