#Lesson 7 Assignment

The Lesson7Database program expects a single argument specifying the name of the file containing the SQL statements that it will process. Invoking Lesson7Database without this argument will generate an error. In response to the error, Lesson7Database displays the following:

```text
Oops, a required argument is missing!

Usage: Lesson7Database [--help]   # Displays this command syntax summary
       Lesson7Database <filename> # Process the SQL statements in <filename>

```
The only other "argument" that Lesson7Database accepts is the "--help" option. When Lesson7Database is invoked with the "--help" option, the following command syntax summary is displayed:

```text

Usage: Lesson7Database [--help]   # Displays this command syntax summary
       Lesson7Database <filename> # Process the SQL statements in <filename>
       
```
A feeble attempt was made to build a modicum of validity checking into the program. Toward that end, Lesson7Database 
validates both the filenames passed on the commandline and those passed as arguments to the Lesson7Database.setFileName() method.  Filenames provided on the command line or as arguments to the setFileName() method are compared to a regular expression (regex) pattern. Those filenames found to be noncompliant will generate an error. In response to the error, Lesson7Database generates the following:
```text
"Edgar//Cole" is not a valid file name!

Usage: Lesson7Database [--help]   # Displays this command syntax summary
       Lesson7Database <filename> # Process the SQL statements in <filename>
```
The file name is also validated when it is passed as an argument to the Lesson7Database's constructor.

The Lesson7Database program connects to a database engine, then submits the SQL statements it reads from the file named in the commandline argument. The result is a table named "lessons", populated with several rows of data. Thereafter, Lesson7Database retrieves all of the rows in the table, and displays that information on the console.

After trying to use Java's built-in database, followed by PostgreSQL, I managed to get MySQL to work. I had the most 
difficulty getting the drivers to work. I was beleaguered by the "class not found" error. In pursuit of a solution, I 
downloaded both the PostgreSQL and the MySQL jar files. Although I settled on MySQL for my solution, I retained both jar files.


Another factor in my decision to use MySQL over PostgreSQL was the formers graphical user interface. On the management side, MySQL's Workbench application had a couple more of the features I needed. It certainly seemed easier to find them. This was important since I frequently found myself dropping and creating tables. In particular, MySQL's refresh button enhanced the development cycle.

Another decision I had to make was whether I was going to create the database from the client-side (i.e., the Java program) or from the server-side console. Given the number of times during the development cycle that I had to create and delete the database, this became problematic. Eventually I decided to manage it from the server-side, were a simple button click made it easy.

In order to accommodate some of the eccentricities of the database I chose, I did make some tweaks to the input file. Regardless, I can't see why the solution I developed wouldn't work with any file containing SQL statements that are compliant. The caveat is that anyone running the program must first create the database. Lesson7Database won't do that.

I never managed to figure out how to prevent MySQL from inserting duplicate rows. I investigated the "IGNORE" option of the "INSERT" command, but it didn't really work the way I had hoped. Although it suppressed the error message, it didn't seem to prevent insertion of duplicate rows.
