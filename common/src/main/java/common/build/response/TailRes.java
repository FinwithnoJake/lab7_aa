package common.build.response;

import common.model.City;
import common.util.Commands;

/**
 * The type Head res.
 */
public class TailRes extends Response {
    /**
     * The Person.
     */
    public final City city;

    /**
     * Instantiates a new Tail res.
     * @param city the city
     * @param error  the error
     */
    public TailRes(City city, String error) {
        super(Commands.TAIL, error);
        this.city = city;
    }
}