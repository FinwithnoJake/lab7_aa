package client.command;

import client.handlers.SessionHandler;
import client.netw.TCP;
import client.util.console.Console;
import common.build.request.SumOfPopulationReq;
import common.build.response.SumOfPopulationRes;
import common.build.response.NoSuchCommandRes;
import common.build.response.NotLoggedInRes;
import common.build.response.Response;
import common.exceptions.*;

import java.io.IOException;

/**
 * Команда 'sum_of_population'. Сумма человеков.
 */
public class SumOfPopulation extends Command {
    private final Console console;
    private final TCP client;

    public SumOfPopulation(Console console, TCP client) {
        super("sum_of_population");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) throw new WrongAmountOfElements();
            var response = client.sendAndReceiveCommand(new SumOfPopulationReq(SessionHandler.getCurrentUser()));
            if (response.getError() != null && !response.getError().isEmpty()) {
                throw new API(response.getError());
            }

            if (response.getClass().equals(NotLoggedInRes.class)) {
                console.printError("Вы не залогинены, войдите");
            }
            if (response.getClass().equals(NoSuchCommandRes.class)) {
                console.printError("???");
            }
            if (response.getClass().equals(getTargetClassCastOrErrorResponse(this.getClass()))) {
                console.println("Общее количество человеков: " + ((SumOfPopulationRes) response).sum);
                return true;
            }

        } catch (WrongAmountOfElements exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (API e) {
            console.printError(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}