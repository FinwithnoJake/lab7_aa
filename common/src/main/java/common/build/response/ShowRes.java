package common.build.response;

import common.model.City;
import common.util.Commands;

import java.util.List;

/**
 * The type Show res.
 */
public class ShowRes extends Response {
    /**
     * The Person.
     */
    public final List<City> city;

    /**
     * Instantiates a new Show res.
     *
     * @param city the city
     * @param error  the error
     */
    public ShowRes(List<City> city, String error) {
        super(Commands.SHOW, error);
        this.city = city;
    }
}