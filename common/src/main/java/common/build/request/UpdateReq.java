package common.build.request;

import common.model.City;
import common.model.User;
import common.util.Commands;

public class UpdateReq extends Request {
    public final int id;
    public final City updatedCity;

    public UpdateReq(int id, City updatedCity, User user) {
        super(Commands.UPDATE, user);
        this.id = id;
        updatedCity.setId(id);
        this.updatedCity = updatedCity;
    }
}
