package objects;

import fileio.MovieInputData;

import java.util.ArrayList;

public final class Movie extends Show {
    private final ArrayList<Double> ratings;
    private final int duration;

    public Movie(final MovieInputData movieInputData) {
        super(movieInputData.getTitle(),
                movieInputData.getYear(),
                movieInputData.getCast(),
                movieInputData.getGenres());
        this.ratings = new ArrayList<>();
        this.duration = movieInputData.getDuration();
    }

    public ArrayList<Double> getRatings() {
        return ratings;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public double getAverageRating() {
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
    public boolean hasRatings() {
        return !this.getRatings().isEmpty();
    }
}
