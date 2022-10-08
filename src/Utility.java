import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utility {

    // the current logged in user
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

    public static void saveUser(User user) {
        String salt = getSalt();
        String hash = getHash(user.getPassword(), salt);
        Out out = new Out(".users.txt");
        out.println(user.getUsername() + " " + salt + " " + hash + " " + user.isAdmin());
    }

    public static boolean initializeApp() {
        try {
            File file = new File(".users.txt");
            if(!file.setWritable(true))
                return false;

            In users = new In(".users.txt");
            StdOut.println("Welcome to Addis Ababa Bus Network System");
            StdOut.println("Please enter your username\n>");
            String username = StdIn.readLine();
            StdOut.println("Please enter your password\n>");
            String password = StdIn.readLine();

            while (users.hasNextLine()) {
                String[] user = users.readLine().split(" ");
                if (user[0].equals(username)) {
                    if (checkHash(password, user[1], user[2])) {
                        currentUser = new User(user[0], user[2], Boolean.parseBoolean(user[3]));
                        if (new File("stations.txt").exists() && new File("users.txt").exists()) {
                            return true;
                        } else {
                            return false;
                        }
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
            File file = new File(".users.txt");

            if(!file.setReadOnly())
                StdOut.println("The file can't be set to read only, please do it manually. To prevent unauthorized access to the file.");
            return true;
        }
        return false;
    }

}
