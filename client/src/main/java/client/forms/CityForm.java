package client.forms;

import client.util.Interrogator;
import client.util.console.Console;
import common.exceptions.*;
import common.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;


public class CityForm extends Form<City> {
    private final Console console;
    public CityForm(Console console){this.console = console;}
    @Override
    public City build() throws IncorrectInputInScript, InvalidForm {
        var city = new City(
                1,
                getName(),
                getCoordinates(),
                getDate(),
                getArea(),
                getPopulation(),
                getMetersAboveSeaLevel(),
                getCarCode(),
                getAgglomeration(),
                getGovernment(),
                getHuman(),
                1
                //TODO ownerId;
        );
        if (!city.validate()) throw new InvalidForm("А еще че сказать? Пересобери эту бурду");
        return city;
    }
    private String getName() throws IncorrectInputInScript {
        String name;
        var fileMode = Interrogator.fileMode();
        while (true) {
            try {
                console.println("Введите имя City:");
                console.ps2();

                name = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(name);
                if (name.equals("")) throw new MustBeNotEmpty();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Имя не распознано!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (MustBeNotEmpty exception) {
                console.printError("Имя не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return name;
    }
    private Coordinates getCoordinates() throws IncorrectInputInScript, InvalidForm {
        return new CoordinatesForm(console).build();
    }

    public LocalDate getDate() throws IncorrectInputInScript, InvalidForm {
        var fileMode = Interrogator.fileMode();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Только дата

        while (true) {
            try {
                console.println("Введите дату создания города (формат: yyyy-MM-dd):");
                console.ps2();

                String input = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) {
                    console.println(input);
                }
                // Попытка парсинга даты
                LocalDate date = LocalDate.parse(input, formatter);
                // Дополнительная валидация (например, проверка на будущее время)
                if (date.isAfter(LocalDate.now().plusYears(1))) {
                    throw new InvalidForm("Ты еще и путешественник во времени!\nЛадно уж, год накинули, с расчетом на скорое открытие, но ты явно борщишь");
                }
                return date;

            } catch (DateTimeParseException e) {
                console.printError("Некорректный формат даты! Используйте формат yyyy-MM-dd");
                if (fileMode) {
                    throw new IncorrectInputInScript();
                }

            } catch (InvalidForm e) {
                console.printError(e.getMessage());
                if (fileMode) {
                    throw new IncorrectInputInScript();
                }

            } catch (NoSuchElementException e) {
                console.printError("Ответ не распознан!");
                if (fileMode) {
                    throw new IncorrectInputInScript();
                }

            } catch (Exception e) {
                console.printError("Произошла непредвиденная ошибка: " + e.getMessage());
                if (fileMode) {
                    throw new IncorrectInputInScript();
                }
            }
        }
    }



    public float getArea() throws IncorrectInputInScript, InvalidForm {
        var fileMode = Interrogator.fileMode();
        while (true) {
            try {
                console.println("Введите area:");
                console.ps2();

                String input = Interrogator.getUserScanner().nextLine().trim().toLowerCase();
                if (fileMode) {console.println(input);}
                float area = Float.parseFloat(input);
                if (area <= 0) {throw new InvalidForm("Че?");}
                return area;
            } catch (NoSuchElementException e) {
                console.printError("Ответ не распознан!");
                if (fileMode) {throw new IncorrectInputInScript();}
            } catch (NumberFormatException e) {
                console.printError("Некорректный формат числа!");
                if (fileMode) {throw new IncorrectInputInScript();}
            } catch (InvalidForm e) {
                console.printError(e.getMessage());
                if (fileMode) {throw new IncorrectInputInScript();}
            } catch (Exception e) {
                console.printError("Произошла непредвиденная ошибка: " + e.getMessage());
                if (fileMode) {throw new IncorrectInputInScript();}
            }
        }
    }


    private Long getPopulation() throws IncorrectInputInScript, InvalidForm {
        var fileMode = Interrogator.fileMode();
        while (true) {
            try {
                console.println("Какова популяция вашего City?");
                console.ps2();

                String input = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) {console.println(input);}
                long population = Long.parseLong(input);
                if (population <= 0) {throw new InvalidForm("Ты один?(((");}
                if (population <= 0) {throw new InvalidForm("Ты один или с трупами живешь, умник?");}
                return population;
            } catch (NoSuchElementException e) {
                console.printError("Ответ не распознан!");
                if (fileMode) {throw new IncorrectInputInScript();}
            } catch (NumberFormatException e) {
                console.printError("Некорректный формат числа!");
                if (fileMode) {throw new IncorrectInputInScript();}
            } catch (InvalidForm e) {
                console.printError(e.getMessage());
                if (fileMode) {throw new IncorrectInputInScript();}
            } catch (Exception e) {
                console.printError("Произошла непредвиденная ошибка: " + e.getMessage());
                if (fileMode) {throw new IncorrectInputInScript();}
            }
        }
    }

    private float getMetersAboveSeaLevel() throws IncorrectInputInScript, InvalidForm {
        var fileMode = Interrogator.fileMode();

        while (true) {
            try {
                console.println("Насколько вы высоко над уровнем моря? (в метрах)");
                console.ps2();

                String input = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) {console.println(input);}
                float meters = Float.parseFloat(input);
                if (meters < -11000 || meters > 9000) {
                    throw new InvalidForm("Значение должно быть в диапазоне от -11000 до 9000 метров\nOr maybe u r an alien?)");
                }

                return meters; // Возвращаем значение при успешном вводе

            } catch (NoSuchElementException e) {
                console.printError("Ответ не распознан!");
                if (fileMode) {
                    throw new IncorrectInputInScript();
                }

            } catch (NumberFormatException e) {
                console.printError("Некорректный формат числа! Введите числовое значение.");
                if (fileMode) {
                    throw new IncorrectInputInScript();
                }

            } catch (InvalidForm e) {
                console.printError(e.getMessage());
                if (fileMode) {
                    throw new IncorrectInputInScript();
                }

            } catch (Exception e) {
                console.printError("Произошла непредвиденная ошибка: " + e.getMessage());
                if (fileMode) {
                    throw new IncorrectInputInScript();
                }
            }
        }
    }

    private Long getCarCode() throws IncorrectInputInScript, InvalidForm {
        var fileMode = Interrogator.fileMode();

        while (true) {
            try {
                console.println("Введите код региона (например, 77, 78, 190):");
                console.ps2();

                String input = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) {console.println(input);}
                if (!input.matches("\\d+")) {throw new InvalidForm("Код региона должен содержать только цифры");}
                long code = Long.parseLong(input);
                if (code < 1 || code > 999) {throw new InvalidForm("Код региона должен быть от 1 до 999\nСерьезно, откуда ты братан?");}
                return code; // Возвращаем значение при успешном вводе
            } catch (NoSuchElementException e) {
                console.printError("Ответ не распознан!");
                if (fileMode) {throw new IncorrectInputInScript();}
            } catch (NumberFormatException e) {
                console.printError("Некорректный формат числа! Введите числовое значение.");
                if (fileMode) {throw new IncorrectInputInScript();}
            } catch (InvalidForm e) {
                console.printError(e.getMessage());
                if (fileMode) {throw new IncorrectInputInScript();}
            } catch (Exception e) {
                console.printError("Произошла непредвиденная ошибка: " + e.getMessage());
                if (fileMode) {throw new IncorrectInputInScript();}
            }
        }
    }

    private long getAgglomeration() throws IncorrectInputInScript, InvalidForm {
        var fileMode = Interrogator.fileMode();

        while (true) {
            try {
                console.println("Введите численность агломерации (в тысячах жителей):");
                console.ps2();

                String input = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) {console.println(input);}
                long agglomeration = Long.parseLong(input);
                if (agglomeration <= 0) {throw new InvalidForm("Численность агломерации должна быть положительным числом");}
                return agglomeration; // Возвращаем значение при успешном вводе
            } catch (NoSuchElementException e) {
                console.printError("Ответ не распознан!");
                if (fileMode) {throw new IncorrectInputInScript();}
            } catch (NumberFormatException e) {
                console.printError("Некорректный формат числа! Введите целое число.");
                if (fileMode) {throw new IncorrectInputInScript();}
            } catch (InvalidForm e) {
                console.printError(e.getMessage());
                if (fileMode) {throw new IncorrectInputInScript();}
            } catch (Exception e) {
                console.printError("Произошла непредвиденная ошибка: " + e.getMessage());
                if (fileMode) {throw new IncorrectInputInScript();}
            }
        }
    }

    private Government getGovernment() throws IncorrectInputInScript, InvalidForm{
        return new GovernmentForm(console).build();
    }
    private String getHuman() throws IncorrectInputInScript, InvalidForm{
        String human;
        var fileMode = Interrogator.fileMode();
        while (true) {
            try {
                console.println("Введите личность:");
                console.ps2();

                human = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(human);
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Имя не распознано!");
                if (fileMode) throw new IncorrectInputInScript();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return human;
    }
}