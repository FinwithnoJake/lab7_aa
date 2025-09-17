package server.commands;

import common.build.request.Request;
import common.build.response.*;
import server.repo.CityNativeBasedRepository;
import server.service.CityService;

/**
 * Команда 'head'. Выводит 1 элемент коллекции.
 */
public class Head extends Command {
    private final CityService service;

    public Head(CityService service) {
        super("head", "вывести первый элемент коллекции");
        this.service = service;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        try {
            return new HeadRes(service.getFirstElement(), null);
        } catch (Exception e) {
            return new HeadRes(null, e.toString());
        }
    }
}