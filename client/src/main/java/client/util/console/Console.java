package client.util.console;

import java.util.Scanner;

/**
 * Консоль для ввода команд и вывода результата
 *
 */
public interface Console {
    void print(Object obj);
    void println(Object obj);
    String readln();
    boolean isCanReadln();
    void printError(Object obj);
    void printTable(Object obj1, Object obj2);
    void prompt();
    String getPrompt();
    void selectFileScanner(Scanner obj);
    void selectConsoleScanner();
    void ps1();

    /**
     * Ps 2.
     */
    void ps2();

    /**
     * Gets ps 1.
     *
     * @return the ps 1
     */
    String getPS1();

    /**
     * Gets ps 2.
     *
     * @return the ps 2
     */
    String getPS2();
}