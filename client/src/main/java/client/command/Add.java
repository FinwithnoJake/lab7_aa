package client.command;

import client.forms.CityForm;
import client.handlers.SessionHandler;
import client.netw.TCP;
import client.util.console.Console;
import common.exceptions.*;
import common.build.request.*;
import common.build.response.*;

import java.io.IOException;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class Add extends Command {
    private final Console console;
    private final TCP client;

    public Add(Console console, TCP client) {
        super("add {element}");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды и сообщение об успешности.
     */

    @Override
    public boolean apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) throw new WrongAmountOfElements();
            console.println("* Создание нового City:");

            var newCity = (new CityForm(console).build());
            var response = client.sendAndReceiveCommand(new AddReq(newCity, SessionHandler.getCurrentUser()));
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new API(response.getError());
            }


            if (response.getClass().equals(NotLoggedInRes.class)) {
                console.printError("Вы не залогинены, войдите");
            }
            if (response.getClass().equals(NoSuchCommandRes.class)) {
                console.printError("??? дурачок залогинься");
            }


            console.println("Новый city с id=" + ((AddRes) response).newId + " успешно добавлен!");
            return true;

        } catch (WrongAmountOfElements exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (InvalidForm e) {
            console.printError("Поля city не валидны! City не создан!");
            console.printError("Причина: " + e.getMessage()); // Добавить причину ошибки
            console.println("Проверьте введенные данные и попробуйте снова");
        } catch(IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (API e) {
            console.printError(e.getMessage());
        } catch (IncorrectInputInScript ignored) {}
        return false;
    }

}