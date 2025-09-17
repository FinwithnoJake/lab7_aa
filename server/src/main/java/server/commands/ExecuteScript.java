package server.commands;

import common.build.request.*;
import common.build.response.*;
import server.service.CityService;
import java.io.File;

/**
 * Команда 'execute_script'. Выполняет скрипт из файла.
 */
public class ExecuteScript extends Command {
    private final CityService cityService;

    /**
     * Instantiates a new Execute script.
     * @param cityService the city repository
     */
    public ExecuteScript(CityService cityService) {
        super("execute_script <file_name>", "запустить код из скрипта");
        this.cityService = cityService;
    }


    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        var req = (ExecuteScriptReq) request;

        try {
            File scriptFile = new File(req.getScriptPath());
            if (!scriptFile.exists()) {
                return new ExecuteScriptRes("Файл скрипта не найден: " + req.getScriptPath());
            }
            cityService.executeScript(req.id);
            return new ExecuteScriptRes(null);
        } catch (Exception e) {
            return new ExecuteScriptRes(e.toString());
        }
    }
}

