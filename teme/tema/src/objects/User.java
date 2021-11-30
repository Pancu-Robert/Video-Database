package objects;

import fileio.UserInputData;

import java.util.ArrayList;
import java.util.HashMap;

public final class User {
    private final String username;
    private final String subscriptionType;
    private final HashMap<String, Integer> history;
    private final ArrayList<String> favoriteMovies;
    private final HashMap<String, Double> ratings;

    public User(final UserInputData userInputData) {
        this.username = userInputData.getUsername();
        this.subscriptionType = userInputData.getSubscriptionType();
        this.history = new HashMap<>(userInputData.getHistory());
        this.favoriteMovies = new ArrayList<>(userInputData.getFavoriteMovies());
        this.ratings = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public HashMap<String, Integer> getHistory() {
        return history;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public HashMap<String, Double> getRatings() {
        return ratings;
    }

    @Override
    public String toString() {
        return username;
    }
}
