package pl.panszelescik.basicmachines.api.common.util;

public class Numbers {

    private Numbers() {
    }

    public static int safeInt(long number) {
        return (int) Math.min(number, Integer.MAX_VALUE);
    }
}
