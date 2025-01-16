package moonduck.server.enums;

public enum Category {

    MOVIE,
    BOOK,
    DRAMA,
    CONCERT,
    TEST;

    public static boolean contains(String value) {
        for (Category category : Category.values()) {
            if (category.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

}
