package objects;

import actor.ActorsAwards;
import database.Database;
import fileio.ActorInputData;

import java.util.ArrayList;
import java.util.HashMap;

public final class Actor {
    private final String name;
    private final String careerDescription;
    private final ArrayList<String> filmography;
    private final HashMap<ActorsAwards, Integer> awards;

    public Actor(final ActorInputData actorInputData) {
        this.name = actorInputData.getName();
        this.careerDescription = actorInputData.getCareerDescription();
        this.filmography = new ArrayList<>(actorInputData.getFilmography());
        this.awards = new HashMap<>(actorInputData.getAwards());
    }

    public String getName() {
        return name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public HashMap<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * cauta in lista de filme/seriale in care a jucat un actor daca acel film/serial
     * are rating.
     * @return true daca show-ul are rating si false in caz contrar.
     */
    public boolean hasRatings() {
        for (String title : getFilmography()) {
            Show show = Database.findShow(title);
            if (show != null && show.hasRatings()) {
                return true;
            }
        }
        return false;
    }

    /**
     * calculeaza media unui show.
     * @return media
     */
    public double calculateAverage() {
        if (!hasRatings()) {
            return 0;
        }
        double avg = 0;
        int count = 0;
        for (String title : getFilmography()) {
            Show show = Database.findShow(title);
            if (show != null && show.hasRatings()) {
                avg += show.getAverageRating();
                count++;
            }
        }
        return avg / count;
    }

}
