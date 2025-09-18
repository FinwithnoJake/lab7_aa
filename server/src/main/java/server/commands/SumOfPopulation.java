package server.commands;

import common.build.request.Request;
import common.build.response.*;
import server.service.CityService;

/**
 * Команда 'sum_of_impactSpeed'. Сумма скорости всех продуктов.
 */
public class SumOfPopulation extends Command {
    private final CityService service;

    public SumOfPopulation(CityService service) {
        super("sum_of_population", "вывести сумму значений поля population для всех элементов коллекции");
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
            return new SumOfPopulationRes(service.getSumOfPopulation(), null);
        } catch (Exception e) {
            return new SumOfPopulationRes(-1, e.toString());
        }
    }
}