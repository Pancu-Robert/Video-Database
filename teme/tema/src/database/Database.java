package database;

import objects.Serial;
import objects.Show;
import objects.Actor;
import objects.User;
import objects.Movie;
import fileio.Input;

import java.util.ArrayList;

public final class Database {
    private static Database database = new Database();
    private static ArrayList<Actor> actorList;
    private static ArrayList<Movie> movieList;
    private static ArrayList<Serial> serialList;
    private static ArrayList<User> userList;

    private Database() {
    }

    public static Database getDatabase() {
        return database;
    }

    public static ArrayList<Actor> getActorList() {
        return new ArrayList<>(actorList);
    }

    public static ArrayList<Movie> getMovieList() {
        return new ArrayList<>(movieList);
    }

    public static ArrayList<Serial> getSerialList() {
        return new ArrayList<>(serialList);
    }

    public static ArrayList<User> getUserList() {
        return new ArrayList<>(userList);
    }

    public static void initDatabase(final Input input) {
        actorList = new ArrayList<>();
        input.getActors().forEach(actorInputData -> actorList
                .add(new Actor(actorInputData)));

        movieList = new ArrayList<>();
        input.getMovies().forEach(movieInputData -> movieList
                .add(new Movie(movieInputData)));

        serialList = new ArrayList<>();
        input.getSerials().forEach(serialInputData -> serialList
                .add(new Serial(serialInputData)));

        userList = new ArrayList<>();
        input.getUsers().forEach(userInputData -> userList
                .add(new User(userInputData)));
    }

    public static ArrayList<Show> getShowList() {
        ArrayList<Show> showList = new ArrayList<>();
        showList.addAll(getMovieList());
        showList.addAll(getSerialList());

        return showList;
    }

    public static Actor findActor(String name) {
        for (Actor actor : getActorList()) {
            if (actor.getName().equals(name)) {
                return actor;
            }
        }
        return null;
    }

    public static Movie findMovie(String name) {
        for (Movie movie : getMovieList()) {
            if (movie.getTitle().equals(name)) {
                return movie;
            }
        }
        return null;
    }

    public static Serial findSerial(String name) {
        for (Serial serial : getSerialList()) {
            if (serial.getTitle().equals(name)) {
                return serial;
            }
        }
        return null;
    }

    public static User findUser(String name) {
        for (User user : getUserList()) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public static Show findShow(String name) {
        for (Show show : getShowList()) {
            if (show.getTitle().equals(name)) {
                return show;
            }
        }
        return null;
    }

}
