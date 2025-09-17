package server.commands;

import common.build.request.*;
import common.build.response.*;
import server.repo.CityNativeBasedRepository;
import server.service.CityService;

/**
 * Команда 'update'. Обновляет элемент коллекции.
 */
public class Update extends Command {
    private final CityService service;

    public Update(CityService service) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
        this.service = service;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        var req = (UpdateReq) request;
        try {
            if (!service.isExist(req.id)) {
                return new UpdateRes("Продукта с таким ID в коллекции нет!");
            }
            if (!req.updatedPerson.validate()) {
                return new UpdateRes("Поля продукта не валидны! Продукт не обновлен!");
            }
            service.updateCity(req.getUser(), req.updatedPerson);
            return new UpdateRes(null);
        } catch (Exception e) {
            return new UpdateRes(e.toString());
        }
    }
}