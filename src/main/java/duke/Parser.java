package duke;

import duke.command.Command;
import duke.command.ExitCommand;
import duke.command.HelpCommand;
import duke.command.ListTaskCommand;
import duke.command.FindCommand;
import duke.command.AddEventCommand;
import duke.command.DeleteCommand;
import duke.command.AddDeadlineCommand;
import duke.command.AddTodoCommand;
import duke.command.UnmarkCommand;
import duke.command.MarkCommand;

/**
 * Parses the user input and triggers commands to run the input information.
 */
public class Parser {
    private String userInput;
    private String[] textEntered;
    private String command;
    private Checker checker;
    private Storage storage;
    private Command parsedCommand;

    private static final String DESC_RESPONSE = "Oops! \\(@.@)/ You have not keyed in a description for the duke" +
            ".task!\n" +
            "Let's try again ~(^.^)~\n" +
            "Type 'help' if you need to know how to use this duke.command";

    /**
     * Constructor method for Parser.
     *
     * @param input User's input into CLI (e.g. event jumping /at 6 January 2023).
     */
    public Parser(String input) {
        userInput = input;
    }

    /**
     * Parses the user input to determine the commands to be used.
     *
     * @throws DukeException Throws a DukeException if there is missing or incorrect information from the user input.
     * @return Command that is to be called based on the user's input.
     */
    public Command parse() throws DukeException {
        textEntered = userInput.split(" ", 2);
        command = textEntered[0];
        checker = new Checker(command);

        switch (checker.getStatus()) {
        case BYE:
            parsedCommand = new ExitCommand();
            break;
        case HELP:
            parsedCommand = new HelpCommand();
            break;
        case LIST:
            parsedCommand = new ListTaskCommand();
            break;
        case DELETE:
            try {
                String deleteId = textEntered[1];
                parsedCommand = new DeleteCommand(deleteId);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Oops! \\(@.@)/ You have not keyed in an ID!\n" +
                        "Let's try again ~(^.^)~\n" +
                        "Type 'help' if you need to know how to use this duke.command");
            }
            break;
        case TODO:
            try {
                String description = textEntered[1];
                parsedCommand = new AddTodoCommand(description);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DukeException(DESC_RESPONSE);
            }
            break;
        case DEADLINE:
            try {
                String text = textEntered[1];
                if (!text.contains("/by")) {
                    throw new DukeException("Please use \"/by\"");
                }
                String[] textArr = text.split("/by");
                String description = textArr[0].trim();
                if (textArr.length == 1) {
                    throw new DukeException("Oops, please specify a date!");
                }
                String time = textArr[1].trim();
                parsedCommand = new AddDeadlineCommand(description, time);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DukeException(DESC_RESPONSE);
            }
            break;
        case EVENT:
            try {
                String text = textEntered[1];
                if (!text.contains("/at")) {
                    throw new DukeException("Please use \"/at\"");
                }
                String[] textArr = text.split("/at ");
                String description = textArr[0].trim();
                if (textArr.length == 1) {
                    throw new DukeException("Oops, please specify a date!");
                }
                String time = textArr[1];
                parsedCommand = new AddEventCommand(description, time);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(DESC_RESPONSE);
            }
            break;
        case UNMARK:
            String unmarkId = textEntered[1];
            parsedCommand = new UnmarkCommand(unmarkId);
            break;
        case MARK:
            String markId = textEntered[1];
            parsedCommand = new MarkCommand(markId);
            break;
        case FIND:
            String findString = textEntered[1];
            parsedCommand = new FindCommand(findString);
        }
        return parsedCommand;
    }
}
