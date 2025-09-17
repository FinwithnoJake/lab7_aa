package common.build.response;

import common.util.Commands;

public class ExecuteScriptRes extends Response{
    /**
     * Instantiates a new ExecuteScript res.
     * @param error the error
     */
    public ExecuteScriptRes(String error) {
        super(Commands.EXECUTE_SCRIPT, error);
    }
}
