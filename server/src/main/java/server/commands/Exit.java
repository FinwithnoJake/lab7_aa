package server.commands;

import common.build.response.*;
import common.build.request.*;
import server.service.CityService;

/**
 * Команда 'exit'. Завершает выполнение.
 */
public class Exit extends Command {
    private final CityService cityService;

    /**
     * Instantiates a new Exit.
     * @param cityService the city repository
     */
    public Exit(CityService cityService) {
        super("exit", "выход из программы");
        this.cityService = cityService;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        try {
            cityService.exit(request.getUser());
            return new ExitRes(null);
        } catch (Exception e) {
            return new ExitRes(e.toString());
        }
    }
}