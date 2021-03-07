package EDU.UCSD.Extension;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lesson7Database {
    private String filename = null;
    private final String databaseName = "COREJAVA";

    public Lesson7Database(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        if (theFileNameIsValid(filename)) {
            this.filename = filename;
        } else {
            System.err.printf("%n\"%s\" is not a valid filename!%n", filename);
        }
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public static void syntaxSummary() {
        var commandName = "Lesson7Database";
        System.out.printf("%n%-7s%-16s%-11s%s%n%-7s%-16s%-11s%s%n",
                "Usage:",
                commandName,
                "[--help]",
                "# Displays this command syntax summary",
                "",
                commandName,
                "<filename>",
                "# Listen for connections on port <number>");
    }

    public static boolean theFileNameIsValid(String filename) {
        var filenamePattern = "^[\\p{L}\\p{N}_\\-.~]+$";
        Pattern pattern = Pattern.compile(filenamePattern);
        Matcher matcher = pattern.matcher(filename);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean startTheDatabase(String filename) {
        return true;
    }

    public boolean connectToTheDatabase(String filename) {
        return true;
    }

    public boolean populateTheTable(String tableName) {
        return true;
    }

    public boolean dumpTheDatabase(String tableName) {
        return true;
    }

    @Override
    public String toString() {
        return String.format("%nInstance of:   %s%nDatabase name: %s%nFilename:      %s%n",
                this.getClass().getSimpleName(),
                this.getDatabaseName(),
                this.getFilename());
    }

    public static void main(String[] arguments) {
        Lesson7Database lesson7Database;
        try {
            var argument = arguments[0];
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            System.err.println("\nOops, a required argument is missing!");
            Lesson7Database.syntaxSummary();
            System.exit(1);
        }
        // Process the commandline option
        for (int index = 0; index < arguments.length; index++) {
            switch (arguments[index]) {
                case "--help":
                    Lesson7Database.syntaxSummary();
                    System.exit(0);
                default:
                    if (theFileNameIsValid(arguments[index])) {
                        lesson7Database = new Lesson7Database(arguments[index]);
                        System.out.println(lesson7Database);
                        lesson7Database.setFilename("Edgar//Cole");
                    } else {
                        System.err.printf("%n\"%s\" is not a valid file name!%n", arguments[index]);
                        Lesson7Database.syntaxSummary();
                        System.exit(1);
                    }
            }
        }
    }
}
