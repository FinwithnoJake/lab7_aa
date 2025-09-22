package client.command;

import client.handlers.SessionHandler;
import client.netw.TCP;
import client.util.console.Console;
import common.exceptions.*;
import common.build.request.*;
import common.build.response.*;

import java.io.IOException;

public class Clear extends Command {
    private final Console console;
    private final TCP client;

    public Clear(Console console, TCP client) {
        super("clear");
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

            var response = client.sendAndReceiveCommand(new ClearReq(SessionHandler.getCurrentUser()));
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new API(response.getError());
            }
            if (response.getClass().equals(NotLoggedInRes.class)) {
                console.printError("Вы не залогинены, войдите");
            }
            if (response.getClass().equals(NoSuchCommandRes.class)) {
                console.printError("???");
            }

            console.println("Коллекция очищена!");
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