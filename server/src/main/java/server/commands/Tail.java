package server.commands;

import common.build.request.Request;
import common.build.response.*;
import server.repo.CityNativeBasedRepository;
import server.service.CityService;

/**
 * Команда 'tail'. Выводит last элемент коллекции.
 */
public class Tail extends Command {
    private final CityService service;

    public Tail(CityService service) {
        super("tail", "вывести последний элемент коллекции");
        this.service = service;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        try {
            return new TailRes(service.getLastElement(), null);
        } catch (Exception e) {
            return new TailRes(null, e.toString());
        }
    }
}