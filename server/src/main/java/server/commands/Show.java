package server.commands;

import common.build.request.Request;
import common.build.response.*;
import common.model.City;
import org.checkerframework.checker.units.qual.C;
import server.repo.CityNativeBasedRepository;
import server.service.CityService;

import java.util.Comparator;
import java.util.List;

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
            List<City> cities = service.getAllCities();

            // Проверяем, нужно ли сортировать по имени
            if (request.hasParameter("sort") && "name".equalsIgnoreCase(request.getParameter("sort"))) {
                cities.sort(Comparator.comparing(City::getName));
            }

            return new ShowRes(cities, null);
        } catch (Exception e) {
            return new ShowRes(null, e.toString());
        }
    }
}