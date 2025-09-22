package client.command;

import client.forms.AuthenthicateForm;
import client.handlers.SessionHandler;
import client.netw.TCP;
import client.util.console.Console;
import common.build.request.AuthReq;
import common.build.response.AuthRes;
import common.exceptions.API;
import common.exceptions.InvalidForm;
import common.exceptions.WrongAmountOfElements;

import java.io.IOException;

public class Auth extends Command {
    private final Console console;
    private final TCP client;
    private static final int MAX_ATTEMPTS = 3;

    public Auth(Console console, TCP client) {
        super("auth");
        this.console = console;
        this.client = client;
    }

    @Override
    public boolean apply(String[] arguments) {

        int attempts = 0;
        boolean success = false;

        while (attempts < MAX_ATTEMPTS) {

            try {
                if (arguments.length > 1 && !arguments[1].isEmpty()) {
                    throw new WrongAmountOfElements();
                }

                console.println("Залогинивание пользователя:");
                var user = (new AuthenthicateForm(console)).build();

                var response = (AuthRes) client.sendAndReceiveCommand(new AuthReq(user));

                if (response.getError() != null && !response.getError().isEmpty()) {
                    attempts++;
                    console.printError("Ошибка: " + response.getError());
                    console.println("Попытка " + attempts + " из " + MAX_ATTEMPTS);
                    continue;
                }

                SessionHandler.setCurrentUser(response.user);
                console.println("Вы в системе, " + response.user.getName() +
                        ", с id=" + response.user.getId() + "!");
                success = true;
                break;

            } catch (WrongAmountOfElements exception) {
                console.printError("Неправильное количество аргументов!");
                console.println("Использование: '" + getName() + "'");
            } catch (InvalidForm exception) {
                console.printError("Введенные данные не валидны! Пользователь не вхожден");
            } catch (IOException e) {
                console.printError("Ошибка взаимодействия с сервером");
            } catch (API e) {
                console.printError(e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (!success && attempts >= MAX_ATTEMPTS) {
            console.printError("Превышено максимальное количество попыток авторизации");
        }
        return false;
    }

    @Override
    public boolean isNeedAuth() {
        return false;
    }
}
