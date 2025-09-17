package common.build.request;

import common.model.User;
import common.util.Commands;

public class SumOfPopulationReq extends Request {
    public SumOfPopulationReq(User user) {
        super(Commands.SUM_OF_POPULATION, user);
    }
}
