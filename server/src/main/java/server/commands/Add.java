package server.commands;

import common.build.request.*;
import common.build.response.*;
import server.service.CityService;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class Add extends Command {
    private final CityService cityService;

    /**
     * Instantiates a new Add.
     * @param cityServise the city servise
     */
    public Add(CityService cityService) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.cityService = cityService;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        var req = (AddReq) request;
        try {
            if (!req.city.validate()) {
                return new AddRes(-1, "Поля city не валидны! City не добавлен!");
            }
            var newId = cityService.add(req.getUser(), req.city);
            return new AddRes(newId, null);
        } catch (Exception e) {
            return new AddRes(-1, e.toString());
        }
    }
}