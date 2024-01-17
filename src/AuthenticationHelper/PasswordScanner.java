package AuthenticationHelper;

import java.io.Console;

public class PasswordScanner {

    public static String readPassword() {
        Console console = System.console();

        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }

        console.printf("Enter password: ");

        char[] passwordArray = console.readPassword();

        StringBuilder password = new StringBuilder();
        for (char c : passwordArray) {
            System.out.print("#");
            password.append(c);
        }

        System.out.println();

        return password.toString();
    }
}
