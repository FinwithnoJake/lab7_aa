package client.command;

import client.handlers.SessionHandler;
import client.netw.TCP;
import client.util.console.Console;
import common.build.request.InfoReq;
import common.build.response.InfoRes;
import common.build.response.NoSuchCommandRes;
import common.build.response.NotLoggedInRes;

import java.io.IOException;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 */
public class Info extends Command {
    private final Console console;
    private final TCP client;

    /**
     * Instantiates a new Info.
     * @param console the console
     * @param client  the client
     */
    public Info(Console console, TCP client) {
        super("info");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        if (arguments.length > 1 && !arguments[1].isEmpty()) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        try {
            var response = client.sendAndReceiveCommand(new InfoReq(SessionHandler.getCurrentUser()));

            // Обрабатываем ответ
            if (response instanceof NotLoggedInRes) {
                console.printError("Вы не авторизованы. Пожалуйста, войдите в систему.");
            } else if (response instanceof NoSuchCommandRes) {
                console.printError("Сервер не понял...");
            } else if (response instanceof InfoRes) {
                console.println(((InfoRes) response).infoMessage);
                return true;
            } else {
                console.printError("Неизвестный ответ сервера");
            }
        } catch (IOException e) {
            console.printError("Ошибка при взаимодействии с сервером");
            return false;
        }
        return false;
    }

    @Override
    public boolean isNeedAuth() {
        return false;
    }
}