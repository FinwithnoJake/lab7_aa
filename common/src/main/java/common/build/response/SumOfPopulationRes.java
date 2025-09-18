package common.build.response;

import common.util.Commands;

public class SumOfPopulationRes extends Response {
    public final long sum;

    public SumOfPopulationRes(long sum, String error) {
        super(Commands.SUM_OF_POPULATION, error);
        this.sum = sum;
    }
}