package client.command;

import common.build.response.Response;

import java.util.Objects;

/**
 * Абстрактная команда с именем и описанием
 */
public abstract class Command {
    private final String name;

    public Command(String name) {
        this.name = name;
    }

    public boolean resolve(String name) {
        return name.equals(this.name);
    }

    public String getName() {
        return name;
    }

    /**
     * Выполнить что-либо.
     *
     * @param arguments запрос с данными для выполнения команды
     * @return результат выполнения
     */
    public abstract boolean apply(String[] arguments);

    public Class<? extends Response> getTargetClassCastOrErrorResponse(Class<? extends Command> initCommandClazz) {
        try {
            // Формируем имя пакета и класса
            String packageName = "common.build.response";
            String className = initCommandClazz.getSimpleName() + "Res";
            String fullClassName = packageName + "." + className;

            // Получаем класс и проверяем его наследование от Response
            Class<?> clazz = Class.forName(fullClassName);
            if (!Response.class.isAssignableFrom(clazz)) {
                throw new IllegalArgumentException("Класс " + fullClassName + " не наследуется от Response");
            }

            // Безопасное приведение типа
            return clazz.asSubclass(Response.class);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Класс не найден: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Ошибка приведения типа: " + e.getMessage(), e);
        }

    }

    public boolean isNeedAuth() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(name, command.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                '}';
    }
}