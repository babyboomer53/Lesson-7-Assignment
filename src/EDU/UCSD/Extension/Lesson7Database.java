package EDU.UCSD.Extension;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lesson7Database {

    private String filename = null;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/corejava";

    // Database credentials
    static final String USERNAME = "babyboomer";
    static final String PASSWORD = "Tl8L2^7GwIoo";

    Scanner scanner = null;

    Connection connection = null;
    Statement statement = null;

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
                "# Process the SQL statements in <filename>");
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

    public boolean connectToTheDatabase() throws SQLException {
        try {
            //STEP 2: Register JDBC driver
            // N O P E !
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean populateTheTable() throws IOException, SQLException {
        System.out.println("Populating the table…");
        scanner = new Scanner(Paths.get(filename), StandardCharsets.UTF_8);
        statement = connection.createStatement();
        while (true) {
            if (!scanner.hasNextLine()) return false;
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) return true;
            if (line.endsWith(";")) // remove trailing semicolon
                line = line.substring(0, line.length() - 1);
            try {
                if (statement.execute(line)) {
                    try (ResultSet resultSet = statement.getResultSet()) {
                        continue;
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else {
                    int updateCount = statement.getUpdateCount();
                }
            } catch (SQLException sqlException) {
                for (Throwable throwable : sqlException)
                    throwable.printStackTrace();
            }
        }
    }

    public void dumpTheDatabase() throws SQLException {
        System.out.println("Dumping the table…\n");
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Lessons");
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int index = 1; index <= columnCount; index++) {
            System.out.printf("%-12s",metaData.getColumnLabel(index));
        }

        System.out.printf("%n%s%n","-".repeat(40));

        while (resultSet.next()) {
            for (int index = 1; index <= columnCount; index++) {
                System.out.printf("%-12s",resultSet.getString(index));
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return String.format("%nInstance of:   %s%nFilename:      %s%n",
                this.getClass().getSimpleName(),
                this.getFilename());
    }

    public static void main(String[] arguments) throws SQLException, IOException {
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
                        lesson7Database.connectToTheDatabase();
                        lesson7Database.populateTheTable();
                        lesson7Database.dumpTheDatabase();
                    } else {
                        System.err.printf("%n\"%s\" is not a valid file name!%n", arguments[index]);
                        Lesson7Database.syntaxSummary();
                        System.exit(1);
                    }
            }
        }
    }
}
