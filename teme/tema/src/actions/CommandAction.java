package actions;

import database.Database;
import entertainment.Season;
import fileio.ActionInputData;
import objects.Movie;
import objects.Serial;
import objects.User;

public final class CommandAction {

    private CommandAction() {
    }

    /**
     * in functie de tipul comenzii se apeleaza metodele specifice
     * @param action
     * @return mesajul de succes sau de eroare
     */
    public static String command(final ActionInputData action) {
        if (action.getType().equals("favorite")) {
            return favoriteCommand(action);
        }

        if (action.getType().equals("view")) {
            return viewCommand(action);
        }

        if (action.getType().equals("rating")) {
            return ratingCommand(action);
        }
        return null;
    }

    /**
     * Caut in lista de favorite a userului daca filmul/serialul este deja la
     * favorite, iar in caz contrar cauta in istoric pentru a avea posibilitatea
     * de al adauga.
     * @param action
     * @return mesajul de succes sau de eroare.
     */
    public static String favoriteCommand(final ActionInputData action) {
        String username = action.getUsername();
        String title = action.getTitle();
        User user = Database.findUser(username);

        if (user.getFavoriteMovies().contains(title)) {
            return "error -> " + title + " is already in favourite list";
        }

        if (user.getHistory().containsKey(title)) {
            user.getFavoriteMovies().add(title);
            return "success -> " + title + " was added as favourite";
        } else {
            return "error -> " + title + " is not seen";
        }
    }

    /**
     * Caut in instoric, iar daca filmul/serialul a mai fost vazut incrementez numarul
     * de vizionari, iar daca nu, il adauga in istoric cu 1 vizionare.
     * @param action
     * @return mesajul de succes sau de eroare
     */
    public static String viewCommand(final ActionInputData action) {
        String username = action.getUsername();
        String title = action.getTitle();
        User user = Database.findUser(username);

        if (user.getHistory().containsKey(title)) {
            int views = user.getHistory().get(title);
            int newViews = views + 1;
            user.getHistory().put(title, newViews);
            return "success -> " + title + " was viewed with total views of " + newViews;
        } else {
            user.getHistory().put(title, 1);
            return "success -> " + title + " was viewed with total views of 1";
        }
    }

    /**
     * Pentru a face diferenta intre un serial si un film, serialele le adaug in
     * istoric de forma [numeSerial##numarSezon].
     * Pentru a da rating, caut in istoric daca a fost vizionat si aplic rating-ul
     * @param action
     * @return mesajul de succes sau de eroare
     */
    public static String ratingCommand(final ActionInputData action) {
        String username = action.getUsername();
        String title = action.getTitle();
        double grade = action.getGrade();
        int currSeason = action.getSeasonNumber();

        User user = Database.findUser(username);
        Movie movie = Database.findMovie(title);
        Serial serial = Database.findSerial(title);

        String key;

        if (!user.getHistory().containsKey(title)) {
            return "error -> " + title + " is not seen";
        }

        if (movie != null) {
            key = movie.getTitle();
        } else {
            key = serial.getTitle() + "##" + currSeason;
        }

        // Daca utilizatorul a dat deja rating la un film sau un sezon
        if (user.getRatings().containsKey(key)) {
            return "error -> " + title + " has been already rated";
        }

        // Daca actiunea se realizeaza pe un film
        if (currSeason == 0) {
            movie.getRatings().add(grade);
            user.getRatings().put(key, grade);
            return "success -> " + title + " was rated with " + grade
                    + " by " + username;
        } else {
            Season season = serial.getSeasons().get(currSeason - 1);
            season.getRatings().add(grade);
            user.getRatings().put(key, grade);
            return "success -> " + title + " was rated with " + grade
                    + " by " + username;
        }
    }
}
