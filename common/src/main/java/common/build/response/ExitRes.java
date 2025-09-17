package common.build.response;

import common.util.Commands;

/**
 * The type Clear res.
 */
public class ExitRes extends Response {
    /**
     * Instantiates a new Exit res.
     * @param error the error
     */
    public ExitRes(String error) {
        super(Commands.EXIT, error);
    }
}