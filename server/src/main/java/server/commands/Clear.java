package server.commands;

import common.build.response.*;
import common.build.request.*;
import server.service.CityService;

/**
 * Команда 'clear'. Очищает коллекцию.
 */
public class Clear extends Command {
    private final CityService cityService;

    /**
     * Instantiates a new Clear.
     * @param cityService the city repository
     */
    public Clear(CityService cityService) {
        super("clear", "очистить коллекцию");
        this.cityService = cityService;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        try {
            cityService.clear(request.getUser());
            return new ClearRes(null);
        } catch (Exception e) {
            return new ClearRes(e.toString());
        }
    }
}