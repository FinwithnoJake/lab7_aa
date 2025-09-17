package server.commands;

import common.build.request.*;
import common.build.response.*;
import server.repo.CityNativeBasedRepository;
import server.service.CityService;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции.
 */
public class RemoveById extends Command {
    private final CityService service;

    public RemoveById(CityService service) {
        super("remove_by_id <ID>", "удалить элемент из коллекции по ID");
        this.service = service;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        var req = (RemoveByIdReq) request;

        try {
            if (!service.isExist(req.id)) {
                return new RemoveByIdRes("Продукта с таким ID в коллекции нет!");
            }

            service.removeById(req.getUser(), req.id);
            return new RemoveByIdRes(null);
        } catch (Exception e) {
            return new RemoveByIdRes(e.toString());
        }
    }
}