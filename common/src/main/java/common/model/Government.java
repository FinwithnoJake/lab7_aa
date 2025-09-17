package common.model;

import java.io.Serializable;

public enum Government implements Serializable {
    DESPOTISM,
    DEMOCRACY,
    DIARCHY,
    NOOCRACY,
    OLIGARCHY,
    PLUTOCRACY;

    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var dragonType : values()) {
            nameList.append(dragonType.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}