package server.commands;

import common.build.request.Request;
import common.build.response.*;
import org.checkerframework.checker.units.qual.C;
import server.repo.CityNativeBasedRepository;
import server.service.CityService;

/**
 * Команда 'show'. Выводит все элементы коллекции.
 */
public class Show extends Command {
    private final CityService service;

    public Show(CityService service) {
        super("show", "вывести все элементы коллекции");
        this.service = service;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        try {
            return new ShowRes(service.getSortedCitys(), null);
        } catch (Exception e) {
            return new ShowRes(null, e.toString());
        }
    }
}