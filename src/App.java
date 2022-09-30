public class App {
    private static int networkMenu() {

        Thread.dumpStack();

        int choice;
        do {
            StdOut.println("Welcome to the main switchboard");
            StdOut.println("1. Network Information");
            StdOut.println("2. Edit Network");
            StdOut.print("Please select an option: ");
            choice = StdIn.readInt();
            if(choice != 1 && choice != 2) {
                StdOut.println("Invalid choice, please try again");
                StdOut.print("\033[H\033[2J");
            }
        }while(choice != 1 && choice != 2);
        return choice;
    }

    private static  int networkInformation() {
        int choice;
        do {
            StdOut.println("1. All Network Information");
            StdOut.println("2. Line Information");
            StdOut.println("3. Station Information");
            StdOut.println("4. Shortest Path Information");
            StdOut.println("5. Return to Main Menu");
            StdOut.println("6. Exit");
            StdOut.print("Please select an option: ");
            choice = StdIn.readInt();

            if(choice == 5)
                networkMenu();

            if(choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5 && choice != 6) {
                StdOut.println("Invalid choice, please try again");
                StdOut.print("\033[H\033[2J");
            }
        }while(choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5 && choice != 6);
        return choice;
    }

    private static int editNetwork() {

        int choice;
        do {
            StdOut.println("1. Introduce new station");
            StdOut.println("2. Change Station Name");
            StdOut.println("3. Change Station Number");
            StdOut.println("4. Insert A New Station On A Line");
            StdOut.println("5. Delete Station From Line");
            StdOut.println("6. Delete Station From Network");
            StdOut.println("7. Introduce a new line");
            StdOut.println("8. Change Line Number");
            StdOut.println("9. Delete A line");
            StdOut.println("10. Network Changed. Save Changes.");
            StdOut.println("11. Undo All Changes.");
            StdOut.println("12. Back To Main Switch Board");
            StdOut.println("13. Exit");
            StdOut.print("Please select an option: ");
            choice = StdIn.readInt();

            if(choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5 && choice != 6 && choice != 7 && choice != 8 && choice != 9 && choice != 10 && choice != 11 && choice != 12 && choice != 13) {
                StdOut.println("Invalid choice, please try again");
                StdOut.print("\033[H\033[2J");
            }
        }while(choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5 && choice != 6 && choice != 7 && choice != 8 && choice != 9 && choice != 10 && choice != 11 && choice != 12 && choice != 13);
        return choice;
    }

    public static void main(String[] args) {
        switch (networkMenu()) {
            case 1: {
                switch (networkInformation()) {
                    case 1: {
                        StdOut.println("All Network Information");
                        break;
                    }
                    case 2: {
                        StdOut.println("Line Information");
                        break;
                    }
                    case 3: {
                        StdOut.println("Station Information");
                        break;
                    }
                    case 4: {
                        StdOut.println("Shortest Path Information");
                        break;
                    }
                    case 5: {
                        StdOut.println("Return to Main Menu");
                        break;
                    }
                    case 6: {
                        StdOut.println("Exit");
                        break;
                    }
                }
            }
            case 2: {
                switch (editNetwork()) {
                    case 1: {
                        StdOut.println("Introduce new station");
                        break;
                    }
                    case 2: {
                        StdOut.println("Change Station Name");
                        break;
                    }
                    case 3: {
                        StdOut.println("Change Station Number");
                        break;
                    }
                    case 4: {
                        StdOut.println("Insert A New Station On A Line");
                        break;
                    }
                    case 5: {
                        StdOut.println("Delete Station From Line");
                        break;
                    }
                    case 6: {
                        StdOut.println("Delete Station From Network");
                        break;
                    }
                    case 7: {
                        StdOut.println("Introduce a new line");
                        break;
                    }
                    case 8: {
                        StdOut.println("Change Line Number");
                        break;
                    }
                    case 9: {
                        StdOut.println("Delete A line");
                        break;
                    }
                    case 10: {
                        StdOut.println("Network Changed. Save Changes.");
                        break;
                    }
                    case 11: {
                        StdOut.println("Undo All Changes.");
                        break;
                    }
                    case 12: {
                        StdOut.println("Back To Main Switch Board");
                        break;
                    }
                    case 13: {
                        StdOut.println("Exit");
                        break;
                    }
                }
            }

        }
    }

}
