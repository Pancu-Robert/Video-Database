package actions;

import com.sun.source.doctree.SeeTree;
import database.Database;
import fileio.ActionInputData;
import objects.Movie;
import objects.Show;
import objects.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public final class RecommendationAction {

    private RecommendationAction() {
    }
    public static String recommendation(final ActionInputData action) {
        if (action.getType().equals("standard")) {
            return standardRecommendation(action);
        }
        if (action.getType().equals("best_unseen")) {
            return bestUnseenRecommendation(action);
        }
        if (action.getType().equals("popular")) {
            return popularRecommendation(action);
        }
        if (action.getType().equals("search")) {
            return searchRecommendation(action);
        }
        if (action.getType().equals("favorite")) {
            return favoriteRecommendation(action);
        }
        return null;
    }

    public static String standardRecommendation(final ActionInputData action) {
        String username = action.getUsername();
        User user = Database.findUser(username);

        for (Show show : Database.getShowList()) {
            if (!user.getHistory().containsKey(show.getTitle())) {
                return "StandardRecommendation result: " + show.getTitle();
            }
        }
        return "StandardRecommendation cannot be applied!";
    }

    public static String bestUnseenRecommendation(final ActionInputData action) {
        String username = action.getUsername();
        User user = Database.findUser(username);

        ArrayList<Show> showList = Database.getShowList();

        showList.sort(new Comparator<Show>() {
            @Override
            public int compare(Show o1, Show o2) {
                return Double.compare(o2.getAverageRating(), o1.getAverageRating());
            }
        });

        for (Show show : showList) {
            if (!user.getHistory().containsKey(show.getTitle())) {
                return "BestRatedUnseenRecommendation result: " + show.getTitle();
            }
        }
        return "BestRatedUnseenRecommendation cannot be applied!";
    }

    public static String popularRecommendation(final ActionInputData action) {
        return null;
    }

    public static String searchRecommendation(final ActionInputData action) {
        User user = Database.findUser(action.getUsername());
        if (user.getSubscriptionType().equals("BASIC")) {
            return "SearchRecommendation cannot be applied!";
        }

        ArrayList<Show> showList = Database.getShowList();

        showList.removeIf(show -> !show.getGenres().contains(action.getGenre()));
        showList.removeIf(show -> user.getHistory().containsKey(show.getTitle()));

        showList.sort(new Comparator<Show>() {
            @Override
            public int compare(final Show o1, final Show o2) {
                if (Double.compare(o1.getAverageRating(), o2.getAverageRating()) != 0) {
                    return Double.compare(o1.getAverageRating(), o2.getAverageRating());
                }
                return String.CASE_INSENSITIVE_ORDER.compare(o1.getTitle(), o2.getTitle());
            }
        });
        return "SearchRecommendation result: " + showList;
    }

    public static String favoriteRecommendation(final ActionInputData action) {
        User user = Database.findUser(action.getUsername());
        if (user.getSubscriptionType().equals("BASIC")) {
            return "FavoriteRecommendation cannot be applied!";
        }

        ArrayList<Show> showList = Database.getShowList();

        showList.removeIf(show -> user.getHistory().containsKey(show.getTitle()));
        showList.removeIf(show -> show.favoriteCount() == 0);

        if (showList.isEmpty()) {
            return "FavoriteRecommendation cannot be applied!";
        }

        showList.sort(new Comparator<Show>() {
            @Override
            public int compare(Show o1, Show o2) {
                return o2.favoriteCount() - o1.favoriteCount();
            }
        });

        return "FavoriteRecommendation result: " + showList.get(0);
    }
}
