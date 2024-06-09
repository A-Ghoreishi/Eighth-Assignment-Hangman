package hangman;

public class UserSession {
    static String currentUsername;

    public static void setUsername(String username) {
        currentUsername = username;
    }

    public static String getUsername() {
        return currentUsername;
    }
}
