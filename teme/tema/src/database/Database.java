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

    /**
     * Creez un ArrayList pentru fiecare camp din clasa Database.
     * Initializez fiecare ArrayList cu inputul specific dat ca parametru.
     * @param input
     */
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

    /**
     * Creez un now ArrayList in care adaug toate filmele si serialele (in aceasta ordine)
     * din baza de date
     * @return ArrayList cu show-uri.
     */
    public static ArrayList<Show> getShowList() {
        ArrayList<Show> showList = new ArrayList<>();
        showList.addAll(getMovieList());
        showList.addAll(getSerialList());

        return showList;
    }

    /**
     * Cauta actorul cu numele dat ca parametru din lista de actori din baza de date.
     * @param name
     * @return Daca se gaseste actorul se returneaza acel actor si null in caz contrar.
     */
    public static Actor findActor(final String name) {
        for (Actor actor : getActorList()) {
            if (actor.getName().equals(name)) {
                return actor;
            }
        }
        return null;
    }

    /**
     * Cauta filmul cu numele dat ca parametru din lista de filme din baza de date.
     * @param name
     * @return Daca se gaseste filmul se returneaza acel film si null in caz contrar.
     */
    public static Movie findMovie(final String name) {
        for (Movie movie : getMovieList()) {
            if (movie.getTitle().equals(name)) {
                return movie;
            }
        }
        return null;
    }

    /**
     * Cauta serialul cu numele dat ca parametru din lista de serialele din baza de date.
     * @param name
     * @return Daca se gaseste serialul se returneaza acel serial si null in caz contrar.
     */
    public static Serial findSerial(final String name) {
        for (Serial serial : getSerialList()) {
            if (serial.getTitle().equals(name)) {
                return serial;
            }
        }
        return null;
    }

    /**
     * Cauta utilizatorul cu numele dat ca parametru din lista de utilizatori din baza de date.
     * @param name
     * @return Daca se gaseste utilizatorul se returneaza acel utilizator si null in caz contrar.
     */
    public static User findUser(final String name) {
        for (User user : getUserList()) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Cauta show-ul cu numele dat ca parametru din lista de show-uri din baza de date.
     * @param name
     * @return Daca se gaseste show-ul se returneaza acel show si null in caz contrar.
     */
    public static Show findShow(final String name) {
        for (Show show : getShowList()) {
            if (show.getTitle().equals(name)) {
                return show;
            }
        }
        return null;
    }
}
