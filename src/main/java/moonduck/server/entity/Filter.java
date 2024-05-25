package moonduck.server.entity;

public enum Filter {

    LATEST,
    OLDEST,
    RATE;

    public static boolean isOneOf(String value) {
        for (Filter filter : values()) {
            if (filter.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
