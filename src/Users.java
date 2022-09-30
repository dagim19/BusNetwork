import java.util.HashSet;

public class Users {
    private HashSet<User> users = new HashSet<>();
    private final String USER_FILE = "users.txt";
    private boolean changed = false;

    public void loadUsers() {
        In in = new In(USER_FILE);
        while(!in.isEmpty()) {
            String uname = in.readString();
            String password = in.readString();
            boolean isAdmin = in.readBoolean();
            users.add(new User(uname, password, isAdmin));
        }
    }


}
