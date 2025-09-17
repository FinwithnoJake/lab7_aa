package client.command;

import client.netw.TCP;
import client.util.console.Console;
import common.util.Commands;

/**
 * Команда 'exit'. Завершает выполнение.
 */
public class Exit extends Command {
    private final Console console;
    private final TCP client;

    /**
     * Instantiates a new Exit.
     * @param console the console
     * @param client  the client
     */
    public Exit(Console console, TCP client) {
        super("exit");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        console.println("Завершение выполнения...");
        console.println("""
                (здесь могла быть ваша реклама)
                ░░░░░░░██████╗░███████╗██████╗░░
                ░░██╗░░██╔══██╗██╔════╝██╔══██╗░
                ██████╗██████╔╝█████╗░░██████╔╝░
                ╚═██╔═╝██╔══██╗██╔══╝░░██╔═══╝░░
                ░░╚═╝░░██║░░██║███████╗██║░░░░░░
                ░░░░░░░╚═╝░░╚═╝╚══════╝╚═╝░░░░░░\s
                
                Так, на этом, пожалуй, закончим,
                хватит бедолагу мучить""".indent(1));
        return true;
    }
}