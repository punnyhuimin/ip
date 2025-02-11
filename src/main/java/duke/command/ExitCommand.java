package duke.command;

import duke.Storage;
import duke.Ui;
import duke.task.Task;
import duke.task.TaskList;

/**
 * Used to exit the Ducky chatbot when user calls "bye".
 */
public class ExitCommand extends Command {
    static final String BYE_RESPONSE = "This window will close in 3 seconds\n"
            + "Bye~ Hope to see you again soon! *✧･ﾟ:*"
            + "(*❦ω❦)*:･ﾟ✧*";

    /**
     * Constructor method for the ExitCommand.
     *
     * @param tasks   TaskList that is maintained in Ducky.
     * @param ui      Ui that is maintained in Ducky.
     * @param storage Storage that is maintained in Ducky.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        String output = "";
        for (int i = 0; i < tasks.getSize(); i++) {
            Task currentTask = tasks.getTask(i);
            String message = currentTask.getTask();
            output = output + (i + 1 + ". " + message) + "\n";
        }
        setExit();
        return ("Your tasks have been saved in " + storage.getFileName() + "\n" + BYE_RESPONSE);
    }
}
