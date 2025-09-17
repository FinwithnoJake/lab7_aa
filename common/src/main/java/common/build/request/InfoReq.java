package common.build.request;

import common.util.Commands;
import common.model.User;

/**
 * The type Info req.
 */
public class InfoReq extends Request {
    /**
     * Instantiates a new Info req.
     */
    public InfoReq(User user) {
        super(Commands.INFO, user);
    }
}
