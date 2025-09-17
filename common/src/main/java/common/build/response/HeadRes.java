package common.build.response;

import common.model.City;
import common.util.Commands;

/**
 * The type Head res.
 */
public class HeadRes extends Response {
    /**
     * The Person.
     */
    public final City city;

    /**
     * Instantiates a new Head res.
     *
     * @param city the city
     * @param error  the error
     */
    public HeadRes(City city, String error) {
        super(Commands.HEAD, error);
        this.city = city;
    }
}