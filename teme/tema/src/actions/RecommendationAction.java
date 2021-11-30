package actions;

import database.Database;
import fileio.ActionInputData;
import objects.Movie;
import objects.Show;
import objects.User;

import java.util.Map;

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
        return null;
    }

    public static String standardRecommendation(final ActionInputData action) {
        String username = action.getUsername();
        User user = Database.getUserMap().get(username);

        for (Map.Entry<String, Show> entryShow: Database.getShows().entrySet()) {
            String dataBaseTitle = entryShow.getKey();
            if (!user.getHistory().containsKey(dataBaseTitle)) {
                return "StandardRecommendation result: " + dataBaseTitle;
            }
        }
        return "StandardRecommendation cannot be applied!";
    }

    public static String bestUnseenRecommendation(final ActionInputData action) {

        return null;
    }

    public static String popularRecommendation(final ActionInputData action) {
        String username = action.getUsername();
        User user = Database.getUserMap().get(username);


        return null;
    }
}
