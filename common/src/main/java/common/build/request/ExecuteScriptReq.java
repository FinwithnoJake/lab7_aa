package common.build.request;

import common.model.User;
import common.util.Commands;

/**
 * The type Remove by id req.
 */
public class ExecuteScriptReq extends Request {
    /**
     * The Id.
     */
    public final int id;

    /**
     * Instantiates a new Execute Script req.
     * @param id the id
     */
    public ExecuteScriptReq(int id, User user) {
        super(Commands.EXECUTE_SCRIPT, user);
        this.id = id;
    }

    public String getScriptPath() {
        return null;
    }
}
