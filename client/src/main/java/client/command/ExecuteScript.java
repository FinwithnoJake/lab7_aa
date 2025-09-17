package client.command;

import client.util.console.Console;

/**
 * Команда 'execute_script'. Выполнить скрипт из файла.
 */
public class ExecuteScript extends Command {
    private final Console console;

    /**
     * Instantiates a new Execute script.
     * @param console the console
     */
    public ExecuteScript(Console console) {
        super("execute_script <file_name>");
        this.console = console;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        if (arguments[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        console.println("Ща попробуем твой '" + arguments[1] + "'...");
        return true;
    }
}