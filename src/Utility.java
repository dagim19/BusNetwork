import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utility {

    private static User currentUser;

    public static String getHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static String getHash(String input, String salt) {
        return getHash(input + salt);
    }

    public static boolean checkHash(String input, String hash) {
        return getHash(input).equals(hash);
    }

    public static boolean checkHash(String input, String salt, String hash) {
        return getHash(input, salt).equals(hash);
    }

    public static String getSalt() {
        return getHash(String.valueOf(System.currentTimeMillis()));
    }

    public static boolean saveUser(User user) {
        String salt = getSalt();
        String hash = getHash(user.getPassword(), salt);
        Out out = new Out(".users.txt");
        // check if the user already exists
        In in = new In(".users.txt");
        while (!in.isEmpty()) {
            String[] line = in.readLine().split(" ");
            if (line[0].equals(user.getUsername())) {
                return false;
            }
        }
        out.println(user.getUsername() + " " + salt + " " + hash + " " + user.isAdmin());
        return true;
    }

    public static boolean initializeApp() {
        try {

            In users = new In(".users.txt");
            StdOut.println("Welcome to Addis Ababa Bus Network System");
            StdOut.println("Please enter your username");
            StdOut.print("> ");
            String username = StdIn.readLine();
            StdOut.println("Please enter your password.");
            StdOut.print("> ");
            String password = StdIn.readLine();

            while (users.hasNextLine()) {
                String[] user = users.readLine().split(" ");
                if (user[0].equals(username)) {
                    if (checkHash(password, user[1], user[2])) {
                        currentUser = new User(user[0], user[2], Boolean.parseBoolean(user[3]));
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }catch (IllegalArgumentException e) {
            Out out = new Out(".users.txt");
            StdOut.println("No users found. Creating new user file.");
            StdOut.print("Enter username: ");
            String username = StdIn.readLine();
            StdOut.println("\nEnter password:");
            String password = StdIn.readLine();
            User user = new User(username, password, true);
            saveUser(user);
            StdOut.println("User created successfully.");
            currentUser = user;
            Network.buildNetwork();
            Stations.buildStations();

            return true;
        }
        return false;
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static boolean isCurrentUserAdmin() {
        return currentUser.isAdmin();
    }

    public static boolean changeAdminStatus(String username, boolean isAdmin) {
        if (isCurrentUserAdmin()) {
            In in = new In(".users.txt");
            Out out = new Out(".users.txt");
            while (!in.isEmpty()) {
                String[] line = in.readLine().split(" ");
                if (line[0].equals(username)) {
                    out.println(line[0] + " " + line[1] + " " + line[2] + " " + isAdmin);
                } else {
                    out.println(line[0] + " " + line[1] + " " + line[2] + " " + line[3]);
                }
            }
            return true;
        }
        return false;
    }

    public static boolean changePassword(String username, String password) {
        if (isCurrentUserAdmin()) {
            In in = new In(".users.txt");
            Out out = new Out(".users.txt");
            while (!in.isEmpty()) {
                String[] line = in.readLine().split(" ");
                if (line[0].equals(username)) {
                    out.println(line[0] + " " + line[1] + " " + getHash(password, line[1]) + " " + line[3]);
                } else {
                    out.println(line[0] + " " + line[1] + " " + line[2] + " " + line[3]);
                }
            }
            return true;
        }
        return false;
    }

    public static boolean removeUser(String username) {
        if (isCurrentUserAdmin()) {
            In in = new In(".users.txt");
            Out out = new Out(".users.txt");
            while (!in.isEmpty()) {
                String[] line = in.readLine().split(" ");
                if (!line[0].equals(username)) {
                    out.println(line[0] + " " + line[1] + " " + line[2] + " " + line[3]);
                }
            }
            return true;
        }
        return false;
    }
}
