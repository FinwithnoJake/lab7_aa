package client.forms;

import client.util.Interrogator;
import common.exceptions.IncorrectInputInScript;
import common.model.Government;
import java.util.NoSuchElementException;

public class GovernmentForm extends Form<Government> {
    private final client.util.console.Console console;

    public GovernmentForm(client.util.console.Console console) {
        this.console = console;
    }

    @Override
    public Government build() throws IncorrectInputInScript {
        var fileMode = Interrogator.fileMode();

        String strGovernment;
        Government government;
        while (true) {
            try {
                console.println("У вас несколько вариантов власти в стране - \n" + Government.names());
                console.println("Введите свой:");
                console.ps2();

                strGovernment = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strGovernment);

                government = Government.valueOf(strGovernment.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Власть не распознана!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (IllegalArgumentException exception) {
                console.printError("Такого нет в списке!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return government;
    }
}