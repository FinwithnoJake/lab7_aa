package common.build.request;

import common.model.User;
import common.util.Commands;

/**
 * The type Head req.
 */
public class TailReq extends Request {
    /**
     * Instantiates a new Head req.
     */
    public TailReq(User user) {
        super(Commands.TAIL, user);
    }
}