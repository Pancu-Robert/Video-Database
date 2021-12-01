package actions;

import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;

public final class ActionsManager {
    private int actionNo;
    private List<ActionInputData> actions;
    private Writer writer;

    public ActionsManager(final List<ActionInputData> actions, final Writer writer) {
        this.actionNo = 0;
        this.actions = actions;
        this.writer = writer;
    }

    /**
     * In functie de
     * @return un JSONobject pentru a fi adaugat in main in lista de rezultate
     * @throws IOException
     */
    public JSONObject executeNextAction() throws IOException {
        if (actionNo == actions.size()) {
            return null;
        } else {
            ActionInputData action = actions.get(actionNo);
            String message = switch (action.getActionType()) {
                case "command" -> CommandAction.command(action);

                case "query" -> QueryAction.query(action);

                case "recommendation" -> RecommendationAction.recommendation(action);

                default -> null;
            };

            JSONObject result = writer.writeFile(action.getActionId(),
                    null, message);

            actionNo++;

            return result;
        }
    }

    public int getActionNo() {
        return actionNo;
    }

    public List<ActionInputData> getActions() {
        return actions;
    }

    public Writer getWriter() {
        return writer;
    }
}
