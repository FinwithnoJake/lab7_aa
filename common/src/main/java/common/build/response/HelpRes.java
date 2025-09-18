package common.build.response;

import common.model.User;
import common.util.Commands;

/**
 * The type Help res.
 */
public class HelpRes extends Response {
    /**
     * The Help message.
     */
    public final String helpMessage;

    /**
     * Instantiates a new Help res for authorized user.
     * @param helpMessage the help message
     * @param error       the error
     */
    public HelpRes(String helpMessage, String error) {
        super(Commands.HELP, error);
        this.helpMessage = helpMessage;
    }

    /**
     * Gets authorized help message.
     * @return the authorized help message
     */
    private static String getAuthorizedHelpMessage() {
        return """
                * add - добавить объект
                * clear - очистить список
                * execute_script - выполнить скрипт
                * exit - выйти из системы
                * head - показать первые элементы
                * help - показать справку
                * history - показать историю команд (нет.)
                * info - показать информацию
                * remove_by_id - удалить объект по ID
                * show - показать все объекты
                * tail - показать последние элементы
                * update - обновить объект
                """;
    }

    /**
     * Gets unauthorized help message.
     * @return the unauthorized help message
     */
    private static String getUnauthorizedHelpMessage() {
        return """
                Доступные команды для неавторизованного пользователя:
                * register - зарегистрироваться
                * auth - авторизоваться
                * help - показать справку
                * exit - выйти из системы
                """;
    }

    /**
     * Creates help response based on user authorization status.
     * @param user current user
     * @param error error message
     * @return help response
     */
    public static HelpRes create(User user, String error) {
        String message;
        if (user != null) {
            message = getAuthorizedHelpMessage();
        } else {
            message = getUnauthorizedHelpMessage();
        }
        return new HelpRes(message, error);
    }

    /**
     * Возвращает строковое представление ответа
     * @return форматированная строка
     */
    @Override
    public String toString() {
        return "Help Response:\n" + helpMessage;
    }
}
