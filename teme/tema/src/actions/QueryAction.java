package actions;

import database.Database;
import fileio.ActionInputData;
import objects.Actor;
import objects.Movie;
import objects.Serial;
import objects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

public final class QueryAction {

    private QueryAction() {
    }

    public static String query(final ActionInputData action) {
        if (action.getObjectType().equals("actors")) {
            return actorsQuery(action);
        }
        if (action.getObjectType().equals("movies")) {
            return moviesQuery(action);
        }
        if (action.getObjectType().equals("shows")) {
            return showsQuery(action);
        }
        if (action.getObjectType().equals("users")) {
            return usersQuery(action);
        }
        return null;
    }

    public static String actorsQuery(final ActionInputData action) {
        if (action.getCriteria().equals("average")) {
            return averageActorsQuery(action);
        }
        if (action.getCriteria().equals("awards")) {
            return awardsActorsQuery(action);
        }
        if (action.getCriteria().equals("filter_description")) {
            return filterDescriptionActorsQuery(action);
        }
        return null;
    }

    public static String moviesQuery(final ActionInputData action) {
        if (action.getCriteria().equals("rating")) {
            return ratingMoviesQuery(action);
        }
        if (action.getCriteria().equals("favorite")) {
            return favoriteMoviesQuery(action);
        }
        if (action.getCriteria().equals("longest")) {
            return longestMoviesQuery(action);
        }
        if (action.getCriteria().equals("most_viewed")) {
            return mostViewedMoviesQuery(action);
        }
        return null;
    }

    public static String showsQuery(final ActionInputData action) {
        if (action.getCriteria().equals("ratings")) {
            return ratingShowsQuery(action);
        }
        if (action.getCriteria().equals("favorite")) {
            return favoriteShowsQuery(action);
        }
        if (action.getCriteria().equals("longest")) {
            return longestShowsQuery(action);
        }
        if (action.getCriteria().equals("most_viewed")) {
            return mostViewedShowsQuery(action);
        }
        return null;
    }

    public static String usersQuery(final ActionInputData action) {
        int number = action.getNumber();
        String sortType = action.getSortType();
        String criteria = action.getCriteria();
        HashMap<String, User> userMap = Database.getUserMap();

        if (number > userMap.size()) {
            number = userMap.size();
        }

        ArrayList<Integer> ratings = new ArrayList<>();

        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            User user = userMap.get(entry.getKey());
            int ratingNumber = user.getRatings().size();
            ratings.add(ratingNumber);
        }

        return null;
    }


    public static String averageActorsQuery(final ActionInputData action) {
        int number = action.getNumber();
        String sortType = action.getSortType();
        String criteria = action.getCriteria();

        return null;
    }

    public static double calcAverage() {
        return 0;
    }

    public static String awardsActorsQuery(final ActionInputData action) {
        return null;
    }

    public static String filterDescriptionActorsQuery(final ActionInputData action) {

        return null;
    }


    public static String ratingMoviesQuery(final ActionInputData action) {
        int number = action.getNumber();
        List<List<String>> filters = action.getFilters();
        String year = filters.get(0).get(0);
        String genre = filters.get(1).get(0);
        String sortType = action.getSortType();
        HashMap<String, Movie> movieMap = Database.getMovieMap();

        if (number > Database.getMovieMap().size()) {
            number = Database.getMovieMap().size();
        }

        ArrayList<Double> ratings = new ArrayList<>();
        for (Map.Entry<String, Movie> entry: movieMap.entrySet()) {
            Movie movie = movieMap.get(entry.getKey());

            if (movie.getGenres().contains(genre)) {

                double movieRating = calcMovieRating(movie);
                if (movieRating != 0) {
                    ratings.add(movieRating);
                }
            }
        }

        if (sortType.equals("asc")) {
            Collections.sort(ratings, new Comparator<Double>() {
                @Override
                public int compare(Double o1, Double o2) {
                    if (o1 < o2) {
                        return -1;
                    } else if (o1 > o2) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
        } else if (sortType.equals("desc")) {
            Collections.sort(ratings, new Comparator<Double>() {
                @Override
                public int compare(Double o1, Double o2) {
                    if (o1 < o2) {
                        return 1;
                    } else if (o1 > o2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }

        return null;
    }

    public static double calcMovieRating(Movie movie) {
        ArrayList<Double> ratings = movie.getRatings();
        int sum = 0;

        if (ratings.isEmpty()) {
            return 0;
        }
        for (Double elem : ratings) {
            sum += elem;
        }

        return (double)sum / ratings.size();
    }

    public static String favoriteMoviesQuery(final ActionInputData action) {
        return null;
    }

    public static String longestMoviesQuery(final ActionInputData action) {
        return null;
    }

    public static String mostViewedMoviesQuery(final ActionInputData action) {
        return null;
    }


    public static String ratingShowsQuery(final ActionInputData action) {
        int number = action.getNumber();
        List<List<String>> filters = action.getFilters();
        String year = filters.get(0).get(0);
        String sortType = action.getSortType();

        HashMap<String, Serial> serialMap = Database.getSerialMap();

        if (number > Database.getSerialMap().size()) {
            number = Database.getSerialMap().size();
        }

        ArrayList<Double> ratings = new ArrayList<>();

        for (Map.Entry<String, Serial> entry : serialMap.entrySet()) {
            Serial serial = serialMap.get(entry.getKey());
        }

        return null;
    }



    public static String favoriteShowsQuery(final ActionInputData action) {
        return null;
    }

    public static String longestShowsQuery(final ActionInputData action) {
        return null;
    }

    public static String mostViewedShowsQuery(final ActionInputData action) {
        return null;
    }

}
