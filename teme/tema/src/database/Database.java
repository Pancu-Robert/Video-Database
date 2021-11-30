package database;

import objects.Serial;
import objects.Show;
import objects.Actor;
import objects.User;
import objects.Movie;
import fileio.Input;
import java.util.HashMap;

public final class Database {
    private static Database database = new Database();
    private static HashMap<String, Actor> actorMap;
    private static HashMap<String, Movie> movieMap;
    private static HashMap<String, Serial> serialMap;
    private static HashMap<String, User> userMap;

    private Database() {
    }

    public static Database getDatabase() {
        return database;
    }

    public static HashMap<String, Actor> getActorMap() {
        return new HashMap<>(actorMap);
    }

    public static HashMap<String, Movie> getMovieMap() {
        return new HashMap<>(movieMap);
    }

    public static HashMap<String, Serial> getSerialMap() {
        return new HashMap<>(serialMap);
    }

    public static HashMap<String, User> getUserMap() {
        return new HashMap<>(userMap);
    }

    public static void initDatabase(final Input input) {
        actorMap = new HashMap<>();
        input.getActors().forEach(actorInputData -> actorMap
                .put(actorInputData.getName(), new Actor(actorInputData)));

        movieMap = new HashMap<>();
        input.getMovies().forEach(movieInputData -> movieMap
                .put(movieInputData.getTitle(), new Movie(movieInputData)));

        serialMap = new HashMap<>();
        input.getSerials().forEach(serialInputData -> serialMap
                .put(serialInputData.getTitle(), new Serial(serialInputData)));

        userMap = new HashMap<>();
        input.getUsers().forEach(userInputData -> userMap
                .put(userInputData.getUsername(), new User(userInputData)));
    }

    public static HashMap<String, Show> getShows() {
        HashMap<String, Show> showMap = new HashMap<>();
        showMap.putAll(movieMap);
        showMap.putAll(serialMap);
        return showMap;
    }
}
