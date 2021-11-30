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

    public final ArrayList<Double> getRatings() {
        return ratings;
    }

    @Override
    public final double getAverageRating() {
        if (!hasRatings()) {
            return 0.0;
        }
        double sum = this.getRatings().stream().mapToDouble(n -> n).sum();
        return sum / this.getRatings().size();
    }

    /**
     *
     * @return
     */
    @Override
    public final boolean hasRatings() {
        return !this.getRatings().isEmpty();
    }
}
