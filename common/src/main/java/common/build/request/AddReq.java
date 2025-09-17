package common.build.request;

import common.model.City;
import common.model.User;
import common.util.Commands;

/**
 * The type Add req.
 */
public class AddReq extends Request {
    public final City city;
    public AddReq(City city, User user) {
         super(Commands.ADD, user);
         this.city = city;
        }
    }

