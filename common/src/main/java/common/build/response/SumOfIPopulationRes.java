package common.build.response;

import common.util.Commands;

public class SumOfIPopulationRes extends Response {
    public final long sum;

    public SumOfIPopulationRes(long sum, String error) {
        super(Commands.SUM_OF_POPULATION, error);
        this.sum = sum;
    }
}