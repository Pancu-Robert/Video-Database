package actions;

import actor.ActorsAwards;
import common.Constants;
import database.Database;
import fileio.ActionInputData;
import objects.User;
import objects.Actor;
import objects.Show;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Comparator;

public final class QueryAction {

    private QueryAction() {
    }

    /**
     * in functie de obiectele pe care se realizeaza query-urile se apeleaza
     * metodele specifice.
     * @param action
     * @return mesajul de succes sau de eroare.
     */
    public static String query(final ActionInputData action) {
        if (action.getObjectType().equals("actors")) {
            return actorsQuery(action);
        }
        if (action.getObjectType().equals("movies")
                || action.getObjectType().equals("shows")) {
            return videosQuery(action);
        }
        if (action.getObjectType().equals("users")) {
            return usersQuery(action);
        }
        return null;
    }

    /**
     * in functie de criteriu elimin rezultatele care nu sunt bune, iar rezultatele
     * ramase le sortez cu metoda sort si creez o interfata noua pentru comparare.
     * La final afisez rezultatele dorite.
     * @param action
     * @return mesajul de succes (rezultatul Query-ului)
     */
    public static String actorsQuery(final ActionInputData action) {
        ArrayList<Actor> actorList = Database.getActorList();

        switch (action.getCriteria()) {
            case "average" -> actorList.removeIf(actor -> !actor.hasRatings());

            case "awards" -> {
                List<String> awardsList = action.getFilters().get(Constants.AWARDS_INDEX);
                for (String award : awardsList) {
                    actorList.removeIf(actor -> !actor.getAwards()
                            .containsKey(Utils.stringToAwards(award)));
                }
            }

            case "filter_description" -> {
                List<String> wordList = action.getFilters().get(Constants.WORDS_INDEX);
                for (String word : wordList) {
                    actorList.removeIf(actor -> !actor.getCareerDescription()
                            .toLowerCase().contains(word + " ")
                            && !actor.getCareerDescription()
                            .toLowerCase().contains(word + ",")
                            && !actor.getCareerDescription()
                            .toLowerCase().contains(word + "."));
                }
            }

            default -> {

            }
        }

        actorList.sort(new Comparator<Actor>() {
            @Override
            public int compare(final Actor o1, final Actor o2) {
                int result = 0;
                if (action.getCriteria().equals("average")) {
                    result = Double.compare(o1.calculateAverage(), o2.calculateAverage());
                } else if (action.getCriteria().equals("awards")) {
                    int awards1 = 0;
                    int awards2 = 0;
                    for (Map.Entry<ActorsAwards, Integer> entry : o1.getAwards()
                            .entrySet()) {
                        awards1 += entry.getValue();
                    }
                    for (Map.Entry<ActorsAwards, Integer> entry : o2.getAwards()
                            .entrySet()) {
                        awards2 += entry.getValue();
                    }
                    result = awards1 - awards2;
                }
                if (result == 0) {
                    result = o1.getName().compareTo(o2.getName());
                }
                if (action.getSortType().equals("desc")) {
                    result = -result;
                }
                return result;
            }
        });

        int actorListSize = actorList.size();

        for (int i = 0; i < actorListSize - action.getNumber(); i++) {
            actorList.remove(actorList.size() - 1);
        }
        return "Query result: " + actorList;
    }

    /**
     * La fel ca si la Query-ul trecut, aplic filtrele necesare si elimin tot
     * ce nu este bun, iar dupa, fac o selectie dupa criteriu, urmand ca la final
     * sa sortez Lista ramasa cu creearea unei noi interfete de comparare.
     * @param action
     * @return mesajul de succes (rezultatul Query-ului)
     */
    public static String videosQuery(final ActionInputData action) {

        // Creez un ArrayList de Show in care, in functie de obiect il initializez.
        ArrayList<Show> showList = new ArrayList<>(action.getObjectType().equals("movies")
                ? Database.getMovieList() : Database.getSerialList());
        String year = action.getFilters().get(Constants.YEAR_INDEX).get(0);
        String genre = action.getFilters().get(Constants.GENRE_INDEX).get(0);

        // Elimin filmele/serialele aplicand filtrele.
        showList.removeIf(show -> year != null && show.getYear() != Integer.parseInt(year));
        showList.removeIf(show -> genre != null && !show.getGenres().contains(genre));

        switch (action.getCriteria()) {
            case "ratings" -> showList.removeIf(show -> !show.hasRatings());

            case "favorite" -> showList.removeIf(show -> show.favoriteCount() == 0);

            case "most_viewed" -> showList.removeIf(show -> show.viewCount() == 0);

            default -> {

            }
        }

        showList.sort(new Comparator<Show>() {
            @Override
            public int compare(final Show o1, final Show o2) {
                int result = 0;
                String criteria = action.getCriteria();
                if (criteria.equals("favorite")) {
                    result = o1.favoriteCount() - o2.favoriteCount();
                } else if (criteria.equals("ratings")) {
                    result = Double.compare(o1.getAverageRating(), o2.getAverageRating());
                } else if (criteria.equals("most_viewed")) {
                    result = o1.viewCount() - o2.viewCount();
                } else {
                    result = o1.getDuration() - o2.getDuration();
                }
                if (result == 0) {
                    result = o1.getTitle().compareTo(o2.getTitle());
                }
                if (action.getSortType().equals("desc")) {
                    result = -result;
                }
                return result;
            }
        });

        int showListSize = showList.size();

        for (int i = 0; i < showListSize - action.getNumber(); i++) {
            showList.remove(showList.size() - 1);
        }

        return "Query result: " + showList;
    }

    /**
     * Elimin utilizatorii care nu au dat rating, dupa care sortez utilizatorii
     * ramasi cu o noua interfata de comparare, in functie de rating, apoi dupa
     * ordinea alfabetica.
     * @param action
     * @return mesajul de succes (rezultatul Query-ului)
     */
    public static String usersQuery(final ActionInputData action) {
        ArrayList<User> userList = Database.getUserList();

        userList.removeIf(user -> user.getRatings().isEmpty());

        userList.sort(new Comparator<User>() {
            @Override
            public int compare(final User o1, final User o2) {
                int result;
                result = o1.getRatings().size() - o2.getRatings().size();
                if (result == 0) {
                    result = o1.getUsername().compareTo(o2.getUsername());
                }
                if (action.getSortType().equals("desc")) {
                    result = -result;
                }
                return result;
            }
        });

        int userListSize = userList.size();

        for (int i = 0; i < userListSize - action.getNumber(); i++) {
            userList.remove(userList.size() - 1);
        }

        return "Query result: " + userList;
    }

}
