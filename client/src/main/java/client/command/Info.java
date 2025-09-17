package client.command;

import client.handlers.SessionHandler;
import client.netw.TCP;
import client.util.console.Console;
import common.build.request.InfoReq;
import common.build.response.InfoRes;

import java.io.IOException;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 *
 */
public class Info extends Command {
    private final Console console;
    private final TCP client;

    /**
     * Instantiates a new Info.
     *
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
        if (!arguments[1].isEmpty()) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        try {
            var response = (InfoRes) client.sendAndReceiveCommand(new InfoReq(SessionHandler.getCurrentUser()));
            console.println(response.infoMessage);
            return true;
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        }
        return false;
    }
}