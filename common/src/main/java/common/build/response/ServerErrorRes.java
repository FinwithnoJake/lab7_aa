package common.build.response;

public class ServerErrorRes extends Response{
    public ServerErrorRes(String name) {
        super(name, "Внутренняя ошибка сервера");
    }
}
