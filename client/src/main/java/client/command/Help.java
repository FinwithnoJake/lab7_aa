package client.command;

import client.handlers.SessionHandler;
import client.netw.TCP;
import client.util.console.Console;
import common.build.request.HelpReq;
import common.build.response.HelpRes;

import java.io.IOException;

/**
 * Команда 'help'. Выводит справку по доступным командам
 *
 */
public class Help extends Command {
    private final Console console;
    private final TCP client;

    /**
     * Instantiates a new Help.
     *
     * @param console the console
     * @param client  the client
     */
    public Help(Console console, TCP client) {
        super("help");
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
            var response = (HelpRes) client.sendAndReceiveCommand(new HelpReq(SessionHandler.getCurrentUser()));
            console.print(response.helpMessage);
            return true;
        } catch(IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        }
        return false;
    }
}