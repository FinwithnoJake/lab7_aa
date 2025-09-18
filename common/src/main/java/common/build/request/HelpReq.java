package common.build.request;

import common.model.User;
import common.util.Commands;

/**
 * Запрос на получение справки
 */
public class HelpReq extends Request {
    private User user;

    /**
     * Конструктор для неавторизованного пользователя
     */
    public HelpReq() {
        super(Commands.HELP, new User());
        this.user = null;
    }

    /**
     * Конструктор для авторизованного пользователя
     * @param user текущий пользователь
     */
    public HelpReq(User user) {
        super(Commands.HELP, user);
        this.user = user;
    }

    /**
     * Возвращает информацию о пользователе
     * @return объект User или null
     */
    public User getUser() {
        return user;
    }

    /**
     * Проверяет необходимость авторизации
     * @return true, если требуется авторизация
     */
    @Override
    public boolean isAuth() {
        return user != null;
    }

    /**
     * Проверка на null для пользователя
     * @return true, если пользователь не null
     */
    public boolean hasUser() {
        return user != null;
    }
}
