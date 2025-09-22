package server.handler;

import common.build.request.Request;
import common.build.response.NotLoggedInRes;
import common.build.response.Response;
import common.build.response.NoSuchCommandRes;
import common.build.response.ServerErrorRes;
import org.slf4j.Logger;
import server.ServerApp;
import server.managers.AuthManager;
import server.managers.CommandManager;
import server.repo.auth.exceptions.BadCredentialsException;

import java.sql.SQLException;

public class CommandHandler {
    private final CommandManager manager;
    private final AuthManager authManager;
    private final Logger logger = ServerApp.logger;

    public CommandHandler(CommandManager manager, AuthManager authManager) {
        this.manager = manager;
        this.authManager = authManager;
    }

    public Response handle(Request request) throws SQLException {
        try {
            // Проверка аутентификации
            if (!request.isAuth()) {
                var user = request.getUser();

                if (user == null) {
                    return new NotLoggedInRes("Пользователь не предоставлен для аутентификации");
                }

                if (authManager.authenticateUser(user.getName(), user.getPassword()) <= 0) {
                    return new NotLoggedInRes("Неверные учетные данные. Пожалуйста, войдите в свой аккаунт.");
                }
            }

            // Получение команды
            String commandName = request.getName();
            var command = manager.getCommands().get(commandName);

            if (command == null) {
                return new NoSuchCommandRes(commandName);
            }

            // Выполнение команды
            return command.apply(request);

        } catch (BadCredentialsException e) {
            logger.warn("Ошибка аутентификации для пользователя {}", request.getUser().getName());
            return new NotLoggedInRes(e.getMessage());
        } catch (NullPointerException e) {
            return new ServerErrorRes("Внутренняя ошибка сервера: " + e.getMessage());
        } catch (SQLException e) {
            logger.error("SQL ошибка при обработке запроса: {}", request, e);
            return new ServerErrorRes("Ошибка базы данных: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Непредвиденная ошибка при обработке запроса: {}", request, e);
            return new ServerErrorRes("Произошла непредвиденная ошибка: " + e.getMessage());
        }
    }

}