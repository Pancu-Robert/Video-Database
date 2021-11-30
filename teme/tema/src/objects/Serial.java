package objects;

import entertainment.Season;
import fileio.SerialInputData;

import java.util.ArrayList;

public final class Serial extends Show {
    private final int numberOfSeasons;
    private final ArrayList<Season> seasons;

    public Serial(final SerialInputData serialInputData) {
        super(serialInputData.getTitle(),
                serialInputData.getYear(),
                serialInputData.getCast(),
                serialInputData.getGenres());
        this.numberOfSeasons = serialInputData.getNumberSeason();
        this.seasons = serialInputData.getSeasons();
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    @Override
    public double getAverageRating() {
        if (!hasRatings()) {
            return 0;
        }
        double avg = 0;
        for (Season season : this.seasons) {
            double seasonAvg = 0;
            if (!season.getRatings().isEmpty()) {
                seasonAvg += season.getRatings().stream().mapToDouble(n -> n).sum();
                avg += seasonAvg / season.getRatings().size();
            }
        }
        return avg / getNumberOfSeasons();
    }

    @Override
    public boolean hasRatings() {
        for (Season season : seasons) {
            if (season.getRatings().size() != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getDuration() {
        return this.getSeasons().stream().mapToInt(Season::getDuration).sum();
    }
}
