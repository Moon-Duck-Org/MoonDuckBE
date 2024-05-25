package moonduck.server.entity;

public enum Category {

    MOVIE,
    BOOK,
    DRAMA,
    CONCERT;

    public static boolean contains(String value) {
        for (Category category : Category.values()) {
            if (category.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
