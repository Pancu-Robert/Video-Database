package objects;

import actor.ActorsAwards;
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
}
