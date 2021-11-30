package objects;

import fileio.MovieInputData;

import java.util.ArrayList;

public class Movie extends Show {
    private final ArrayList<Double> ratings;

    public Movie(final MovieInputData movieInputData) {
        super(movieInputData.getTitle(),
                movieInputData.getYear(),
                movieInputData.getCast(),
                movieInputData.getGenres());
        this.ratings = new ArrayList<>();
    }

    public ArrayList<Double> getRatings() {
        return ratings;
    }
}
