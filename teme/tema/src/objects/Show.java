package objects;

import database.Database;

import java.util.ArrayList;

public abstract class Show {
    private final String title;
    private final int year;
    private final ArrayList<String> cast;
    private final ArrayList<String> genres;

    public Show(final String title, final int year,
                     final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * am implementat metoda abstracta, pentru calcularea mediei ratingurilor, pentru a o
     * putea suprascrie in clasele derivate.
     * @return
     */
    public abstract double getAverageRating();

    /**
     * am implementat metoda abstracta, pentru vedea, pentru a o
     * putea suprascrie in clasele derivate.
     * @return
     */
    public abstract boolean hasRatings();

    /**
     * Contorizez de cate ori apare numele unui show in lista de favorite a utilizatorilor
     * @return nr de favorite
     */
    public final int favoriteCount() {
        int count = 0;

        for (User user : Database.getUserList()) {
            count += user.getFavoriteMovies().contains(this.title) ? 1 : 0;
        }

        return count;
    }

    /**
     * Contorizez de cate ori este vazut un show de toti utilizatorii.
     * (Implementare este sugerata de IDE)
     * @return numarul de vizualizari
     */
    public final int viewCount() {
        int count = 0;

        for (User user : Database.getUserList()) {
            count += user.getHistory().getOrDefault(this.title, 0);
        }

        return count;
    }

    /**
     * metoda abstracta de calculare a duratei unui film/serial pe care am implementat-o in
     * clasele derivate acestei clase.
     * @return durata
     */
    public abstract int getDuration();

    @Override
    public final String toString() {
        return title;
    }
}
