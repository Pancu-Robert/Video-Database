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
}
