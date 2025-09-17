package common.build.request;

import common.util.Commands;
import common.model.User;

/**
 * The type Head req.
 */
public class HeadReq extends Request {
    /**
     * Instantiates a new Head req.
     */
    public HeadReq(User user) {
        super(Commands.HEAD, user);
    }
}