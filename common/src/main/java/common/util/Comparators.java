package common.util;

import common.model.City;

import java.util.Comparator;

/**
 * Ко(р)мпарат(ивы)ор.
 */
public class Comparators implements Comparator<City> {
    public int compare(City a, City b) {
        return a.getPopulation().compareTo(b.getPopulation());
    }
}
