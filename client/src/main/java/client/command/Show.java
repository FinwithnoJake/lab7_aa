package client.command;

import client.handlers.SessionHandler;
import client.netw.TCP;
import client.util.console.Console;
import common.exceptions.*;
import common.build.request.*;
import common.build.response.*;

import java.io.IOException;

/**
 * Команда 'show'. Выводит все элементы коллекции.
 *
 */
public class Show extends Command {
    private final Console console;
    private final TCP client;

    /**
     * Instantiates a new Show.
     *
     * @param console the console
     * @param client  the client
     */
    public Show(Console console, TCP client) {
        super("show");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) throw new WrongAmountOfElements();

            var response = (ShowRes) client.sendAndReceiveCommand(new ShowReq(SessionHandler.getCurrentUser()));
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new API(response.getError());
            }

            if (response.city.isEmpty()) {
                console.println("Коллекция пуста!");
                return true;
            }

            for (var person : response.city) {
                console.println(person + "\n");
            }
            return true;
        } catch (WrongAmountOfElements exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch(IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (API e) {
            console.printError(e.getMessage());
        }
        return false;
    }
}