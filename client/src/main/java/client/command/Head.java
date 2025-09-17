package client.command;

import client.handlers.SessionHandler;
import client.netw.TCP;
import client.util.console.Console;
import common.exceptions.*;
import common.build.request.*;
import common.build.response.*;

import java.io.IOException;


/**
 * Команда 'head'. Выводит первый элемент коллекции.
 */
public class Head extends Command {
    private final Console console;
    private final TCP client;

    /**
     * Instantiates a new Head.
     * @param console the console
     * @param client  the client
     */
    public Head(Console console, TCP client) {
        super("head");
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

            var response = (HeadRes) client.sendAndReceiveCommand(new HeadReq(SessionHandler.getCurrentUser()));
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new API(response.getError());
            }

            if (response.city == null) {
                console.println("Коллекция пуста!");
                return true;
            }

            console.println(response.city);
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