package common.build.request;

import common.util.Commands;
import common.model.User;

/**
 * The type Clear req.
 */
public class ClearReq extends Request {
    /**
     * Instantiates a new Clear req.
     */
    public ClearReq(User user) {
        super(Commands.CLEAR, user);
    }
}