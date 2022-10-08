import java.util.ArrayList;

public class App {

    static Network network;
    static Stations stations;

    private static int editChoice() {
        int choice;
        Utility.clear();
        StdOut.println("What do you want to do?");
        StdOut.println("Press 1: Add a new line");
        StdOut.println("Press 2: Add a new station to an existing line");
        StdOut.println("Press 3: Remove a line");
        StdOut.println("Press 4: Remove a station from an existing line");
        StdOut.println("Press 5: Change the distance between two stations");
        StdOut.println("Press 6: Change the line number of a line");
        StdOut.println("Press 7: Change the station number of a station");
        StdOut.println("Press 8: Handle user information.");
        StdOut.println("Press 9: Return to main menu");
        StdOut.print("> ");
        choice = StdIn.readInt();
        return choice;
    }

    private static int editUserChoice() {
        int chioce;
        Utility.clear();
        StdOut.println("What do you want to do?");
        StdOut.println("Press 1: Add a new user");
        StdOut.println("Press 2: Remove a user");
        StdOut.println("Press 3: Change a user's password");
        StdOut.println("Press 4: Change a user's admin status");
        StdOut.println("Press 5: Return to main menu");
        StdOut.print("> ");
        chioce = StdIn.readInt();
        return chioce;
    }

    private static void handleUserEdit() {
        int choice = editUserChoice();
        switch (choice) {
            case 1:
                Utility.clear();
                StdOut.println("Enter the username of the new user");
                StdOut.print("> ");
                String username = StdIn.readLine();
                StdOut.println("Enter the password of the new user");
                StdOut.print("> ");
                String password = StdIn.readLine();
                StdOut.println("Is the new user an admin? (y/n)");
                StdOut.print("> ");
                boolean isAdmin = StdIn.readLine().equals("y");
                User user = new User(username, password, isAdmin);
                if (Utility.saveUser(user)) {
                    StdOut.println("User created successfully.");
                } else {
                    StdOut.println("User creation failed.");
                }
                break;
            case 2:
                Utility.clear();
                StdOut.println("Enter the username of the user to be removed");
                StdOut.print("> ");
                username = StdIn.readLine();
                if (Utility.removeUser(username)) {
                    StdOut.println("User removed successfully.");
                } else {
                    StdOut.println("User removal failed.");
                }
                break;
            case 3:
                Utility.clear();
                StdOut.println("Enter the username of the user whose password is to be changed");
                StdOut.print("> ");
                username = StdIn.readLine();
                StdOut.println("Enter the new password");
                StdOut.print("> ");
                password = StdIn.readLine();
                if (Utility.changePassword(username, password)) {
                    StdOut.println("Password changed successfully.");
                } else {
                    StdOut.println("Password change failed.");
                }
                break;
            case 4:
                Utility.clear();
                StdOut.println("Enter the username of the user whose admin status is to be changed");
                StdOut.print("> ");
                username = StdIn.readLine();
                StdOut.println("Is the user an admin? (y/n)");
                StdOut.print("> ");
                isAdmin = StdIn.readLine().equals("y");
                if (Utility.changeAdminStatus(username, isAdmin)) {
                    StdOut.println("Admin status changed successfully.");
                } else {
                    StdOut.println("Admin status change failed.");
                }
                break;
            case 5:
                return;
            default:
                StdOut.println("Invalid choice.");
                break;
        }
        Utility.clear();
        StdOut.println("Press any key to continue...");
        StdIn.readLine();
    }

    private static void handleEdit() {
        int choice;

        do {
            choice = editChoice();
            if(choice < 1 || choice > 8) {
                StdOut.println("You entered an invalid choice...");
                continue;
            }

            switch (choice) {
                case 1:
                {
                   Utility.clear();
                   StdOut.println("Enter New Line");
                   int lineNumber;
                   do {
                       StdOut.println("Please enter the new line number. (Enter -1 to abort)\n>");
                       lineNumber = StdIn.readInt();
                       if(network.lineNumbers.contains(lineNumber))
                           StdOut.println("Line number already exists. Please enter a new line number.");
                       else break;
                     }while(network.lineNumbers.contains(lineNumber));

                   if(lineNumber == -1)
                       break;
                   StdOut.println("Enter the number of stations in the line.\n>");
                   int numberOfStations = StdIn.readInt();
                   Line line = new Line(lineNumber, numberOfStations);
                   StdOut.println("Do you want to add stations to the line? (y/n)\n>");
                   String choice2 = StdIn.readString();
                   if(choice2.equals("y")) {
                       for (int i = 0; i < numberOfStations; i++) {
                           StdOut.println("Enter the station number.\n>");
                           int stnId = StdIn.readInt();
                           if(!stations.stnIds.contains(stnId)) {
                               StdOut.println("The station you entered is new");
                               StdOut.println("Please enter the name for the station.\n>");
                               String name = StdIn.readString();
                               Station s = new Station(name, stnId);
                               stations.addStation(s);
                           }
                           StdOut.println("Enter the distance between the station and the previous station.\n>");
                           float distance = StdIn.readFloat();
                           StationData stn = new StationData(stnId, distance);
                           line.addStation(stn);
                           if(i == 0) line.setStart(stn);
                       }
                   }
                   if (network.lines.add(line)) {
                       network.lineNumbers.add(lineNumber);
                       StdOut.println("Successfully added the new line in the network");
                   }else{
                          StdOut.println("Failed to add the new line in the network");
                   }
                    StdOut.println("Press any key to continue...");
                    StdIn.readString();
                }
                break;
                case 2:
                {
                    Utility.clear();
                    StdOut.println("Enter a new station to an existing line.");
                    StdOut.println("Enter the line number. (Enter -1 to abort)\n>");
                    int lineNumber = StdIn.readInt();
                    do {
                        if(!network.lineNumbers.contains(lineNumber)) {
                            StdOut.println("The line number you entered does not exist. Please enter a valid line number.\n>");
                            lineNumber = StdIn.readInt();
                        }
                        else break;
                    }while(!network.lineNumbers.contains(lineNumber));

                    if(lineNumber == -1)
                        break;

                    StdOut.println("Enter the station number.\n>");
                    int stnId = StdIn.readInt();
                    if(!stations.stnIds.contains(stnId)) {
                        StdOut.println("The station you entered is new");
                        StdOut.println("Please enter the name for the station.\n>");
                        String name = StdIn.readString();
                        Station s = new Station(name, stnId);
                        stations.addStation(s);
                    }
                    StdOut.println("Enter the distance between the station and the previous station.\n>");
                    float distance = StdIn.readFloat();
                    StationData stn = new StationData(stnId, distance);
                    if(network.lines.get(network.lineIndex(lineNumber)).addStation(stn)) {
                        StdOut.println("Successfully added the new station");
                    }else{
                        StdOut.println("Failed to add the new station");
                    }
                    StdOut.println("Press any key to continue...");
                    StdIn.readString();
                }
                break;
                case 3:
                {
                    Utility.clear();
                    StdOut.println("Remove a line from the network");
                    StdOut.println("Enter the line number to remove. (Enter -1 to abort)\n>");
                    int lineNumber = StdIn.readInt();
                    do {
                        if(!network.lineNumbers.contains(lineNumber)) {
                            StdOut.println("The line number you entered does not exist. Please enter a valid line number.\n>");
                            lineNumber = StdIn.readInt();
                        }
                        else break;
                    }while(!network.lineNumbers.contains(lineNumber));

                    if(lineNumber == -1)
                        break;

                    if(network.lines.remove(network.lines.get(network.lineIndex(lineNumber)))) {
                        network.lineNumbers.remove(network.lineIndex(lineNumber));
                        StdOut.println("Successfully removed the line");
                    }else{
                        StdOut.println("Failed to remove the line");
                    }
                    StdOut.println("Press any key to continue...");
                    StdIn.readString();
                }
                break;
                case 4:
                {
                    Utility.clear();
                    StdOut.println("Remove a station from a line");
                    StdOut.println("Enter the line number. (Enter -1 to abort)\n>");
                    int lineNumber = StdIn.readInt();
                    do {
                        if(!network.lineNumbers.contains(lineNumber)) {
                            StdOut.println("The line number you entered does not exist. Please enter a valid line number.\n>");
                            lineNumber = StdIn.readInt();
                        }
                        else break;
                    }while(!network.lineNumbers.contains(lineNumber));

                    if(lineNumber == -1)
                        break;

                    int stnId;
                    do {
                        StdOut.println("Please enter the station number you want do delete. (Enter -1 to abort)");
                        stnId = StdIn.readInt();

                        if(!stations.stnIds.contains(stnId)) {
                            StdOut.println("The station number you entered is invalid. Please enter a valid station number.\n>");
                        }
                    }while(!stations.stnIds.contains(stnId) && stnId != -1);

                    if(stnId == -1)
                        break;

                    StdOut.println("Enter the distance between the station and the previous station.\n>");
                    float distance = StdIn.readFloat();
                    StationData stn = new StationData(stnId, distance);
                    if(network.lines.get(network.lineIndex(lineNumber)).deleteStation(stn)) {
                       StdOut.println("Successfully removed the station");
                    }else{
                        StdOut.println("Failed to delete the station");
                    }
                    StdOut.println("Press any key to continue...");
                    StdIn.readString();
                }
                break;
                case 5:
                {
                    Utility.clear();
                    StdOut.println("Change the distance between two stations");
                    StdOut.println("Enter the line number. (Enter -1 to abort)\n>");
                    int lineNumber = StdIn.readInt();
                    do {
                        if(!network.lineNumbers.contains(lineNumber)) {
                            StdOut.println("The line number you entered does not exist. Please enter a valid line number.\n>");
                            lineNumber = StdIn.readInt();
                        }
                        else break;
                    }while(!network.lineNumbers.contains(lineNumber));

                    if(lineNumber == -1)
                        break;

                    int stnId;
                    do {
                        StdOut.println("Please enter the station number you want do delete. (Enter -1 to abort)");
                        stnId = StdIn.readInt();

                        if(!stations.stnIds.contains(stnId)) {
                            StdOut.println("The station number you entered is invalid. Please enter a valid station number.\n>");
                        }
                    }while(!stations.stnIds.contains(stnId) && stnId != -1);

                    if(stnId == -1)
                        break;

                    StdOut.println("Enter the distance between the station and the previous station.\n>");

                    float distance = StdIn.readFloat();
                    StationData stn = new StationData(stnId, distance);
                    if(network.lines.get(network.lineIndex(lineNumber)).changeDistance(stn)) {
                       StdOut.println("Successfully changed the distance");
                    }else {
                        StdOut.println("Failed to change the distance");
                    }
                    StdOut.println("Press any key to continue...");
                    StdIn.readString();
                }
                break;
                case 6:
                {
                    Utility.clear();
                    StdOut.println("Change the line number of a line");
                    StdOut.println("Enter the line number. (Enter -1 to abort)\n>");
                    int lineNumber = StdIn.readInt();

                    do {
                        if(!network.lineNumbers.contains(lineNumber)) {
                            StdOut.println("The line number you entered does not exist. Please enter a valid line number.\n>");
                            lineNumber = StdIn.readInt();
                        }
                        else break;
                    }while(!network.lineNumbers.contains(lineNumber));

                    if(lineNumber == -1)
                        break;

                    StdOut.println("Enter the new line number.\n>");
                    int newLineNumber = StdIn.readInt();

                    if(network.changeLineNumber(lineNumber, newLineNumber)) {
                        StdOut.println("Successfully changed the line number");
                    }else {
                        StdOut.println("Failed to change the line number");
                    }
                    StdOut.println("Press any key to continue...");
                    StdIn.readString();
                }
                break;
                case 7:
                {
                    Utility.clear();
                    StdOut.println("Change the station number of a station");
                    StdOut.println("Enter the station number. (Enter -1 to abort)\n>");
                    int stnId = StdIn.readInt();

                    do {
                        if(!stations.stnIds.contains(stnId)) {
                            StdOut.println("The station number you entered does not exist. Please enter a valid station number. (Enter -1 to abort)\n>");
                            stnId = StdIn.readInt();
                        }
                        else break;
                    }while(!stations.stnIds.contains(stnId) && stnId != -1);

                    if(stnId == -1)
                        break;

                    StdOut.println("Enter the new station number.\n>");
                    int newStnId = StdIn.readInt();

                    if(stations.changeStnId(stnId, newStnId)) {
                        StdOut.println("Successfully changed the station number");
                    }else{
                        StdOut.println("Failed to change the station number");
                    }
                    StdOut.println("Press any key to continue...");
                    StdIn.readString();
                }
                break;
                case 8:
                {
                    Utility.clear();
                    handleUserEdit();
                }
                break;
                case 9:
                {
                    Utility.clear();
                    break;
                }
            }
        }while(choice < 1 || choice > 9 || choice != 9);
    }

    private static int networkInfoChoice() {
        int choice;
        Utility.clear();
        StdOut.println("Network Information");
        StdOut.println("Press 1: All Network Information");
        StdOut.println("Press 2: Line Information");
        StdOut.println("Press 3: Station Information");
        StdOut.println("Press 4: Shortest path Information");
        StdOut.println("Press 5: Back to menu");
        StdOut.print(">");
        return StdIn.readInt();
    }

    private static void handleInfo() {
        int choice;

        do {
            choice = networkInfoChoice();
            if(choice < 1 || choice > 6) {
                StdOut.println("You entered an invalid choice...");
                continue;
            }

            switch (choice) {
                case 1:
                {
                    Utility.clear();
                    StdOut.println("Network Information");
                    StdOut.println("There are " + network.nubLines + " lines in the network.");
                    StdOut.println("========================================");
                    StdOut.println("Line-ID\tNumber of Stations");
                    StdOut.println("========================================");
                    for (Line line: network.lines) {
                        StdOut.println(line.getLineNumber() + "\t\t" + line.getNumberOfStations());
                    }
                    StdOut.println("Press any key to continue...");
                    StdIn.readChar();
                }
                break;
                case 2:
                {
                    Utility.clear();
                    StdOut.println("Line Information");
                    StdOut.println("Enter the Line-ID. (Enter -1 to abort)\n>");
                    int lineNumber = StdIn.readInt();

                    do {
                        if(!network.lineNumbers.contains(lineNumber)) {
                            StdOut.println("The line number you entered does not exist. Please enter a valid line number.\n>");
                            lineNumber = StdIn.readInt();
                        }
                        else break;
                    }while(!network.lineNumbers.contains(lineNumber));

                    if(lineNumber == -1)
                        break;

                    StdOut.println("========================================");
                    StdOut.println("Line Number\tNumber of Stations");
                    StdOut.println("========================================");
                    StdOut.println(network.lines.get(network.lineIndex(lineNumber)).getLineNumber() + "\t\t" + network.lines.get(network.lineIndex(lineNumber)).getNumberOfStations());
                    StdOut.println("Press any key to continue...");
                    StdIn.readChar();
                }
                break;
                case 3:
                {
                    Utility.clear();
                    StdOut.println("Station Information");
                    StdOut.println("Enter the Station-ID. (Enter -1 to abort)\n>");
                    int stnId = StdIn.readInt();

                    do {
                        if(!stations.stnIds.contains(stnId)) {
                            StdOut.println("The station number you entered does not exist. Please enter a valid station number. (Enter -1 to abort)\n>");
                            stnId = StdIn.readInt();
                        }
                        else break;
                    }while(!stations.stnIds.contains(stnId) && stnId != -1);

                    if(stnId == -1)
                        break;

                    StdOut.println("========================================");
                    StdOut.println("Station Number\tStation Name");
                    StdOut.println("========================================");
                    StdOut.println(stnId + "\t\t" + stations.mapStation(stnId));
                    StdOut.println("Press any key to continue...");
                    StdIn.readChar();
                }
                break;
                case 4:
                {
                    Utility.clear();
                    StdOut.println("Shortest Path Information");
                    StdOut.println("Enter the Station-ID of the source station. (Enter -1 to abort)\n>");
                    int sourceStnId = StdIn.readInt();

                    do {
                        if(!stations.stnIds.contains(sourceStnId)) {
                            StdOut.println("The Station-ID you entered does not exist. Please enter a valid Station-ID. (Enter -1 to abort)\n>");
                            sourceStnId = StdIn.readInt();
                        }
                        else break;
                    }while(!stations.stnIds.contains(sourceStnId) && sourceStnId != -1);

                    if(sourceStnId == -1)
                        break;

                    StdOut.println("Enter the Station-ID of the destination station. (Enter -1 to abort)\n>");
                    int destStnId = StdIn.readInt();

                    do {
                        if(!stations.stnIds.contains(destStnId)) {
                            StdOut.println("The Station-ID you entered does not exist. Please enter a valid Station-ID. (Enter -1 to abort)\n>");
                            destStnId = StdIn.readInt();
                        }
                        else break;
                    }while(!stations.stnIds.contains(destStnId) && destStnId != -1);

                    if(destStnId == -1)
                        break;

                    StdOut.println("========================================");
                    StdOut.println("Shortest Path");
                    StdOut.println("========================================");
                    ArrayList<StationData> path = (ArrayList<StationData>) network.shortestPath(sourceStnId, destStnId);
                    for(StationData stationData: path) {
                        StdOut.print(stations.mapStation(stationData.getStnId()) + " (" + stationData.getStnId() + ") -> ");
                    }
                    StdOut.println("END");
                    StdOut.println("Press any key to continue...");
                    StdIn.readChar();
                }
                break;
                case 5:
                {
                    Utility.clear();
                    return;
                }
                case 6:
                {
                    Utility.clear();
                    System.exit(0);
                }
                break;
            }
        }while(choice < 1 || choice > 6 || choice != 6);
    }


    private static int menuChoice() {
        int choice;
        // clear the screen
        System.out.print("\033[H\033[2J");
        StdOut.println("MAIN SWITCH BOARD");
        StdOut.println("Press 1: Network Information");
        StdOut.println("Press 2: Edit Network");
        StdOut.println("Press 3: Network Changed. Save Changes");
        StdOut.println("Press 4: Undo All Changes");
        StdOut.println("Press 5: Exit");
        StdOut.print(">");
        choice = StdIn.readInt();
        return choice;
    }

    private static void mainMenu() {
        int choice;
        do {
            choice = menuChoice();
            if(choice < 1 || choice > 5) {
                StdOut.println("Invalid choice. Please Enter again.");
                StdDraw.pause(1000);
                continue;
            }

            switch (choice) {
                case 1:
                {
                    Utility.clear();
                    handleInfo();
                }
                break;
                case 2:
                {
                    if(!Utility.isCurrentUserAdmin()) {
                        Utility.clear();
                        StdOut.println("You are not authorized to perform this operation.");
                        StdOut.println("Press any key to continue...");
                        StdIn.readChar();
                        break;
                    }
                    Utility.clear();
                    handleEdit();
                }
                break;
                case 3:
                {
                    Utility.clear();
                    StdOut.println("Saving changes...");
                    if (network.updateNetworkFile() && stations.updateStnFile()){
                        StdOut.println("Successfully saved changes");
                    }else {
                        StdOut.println("Failed to save changes");
                    }
                    StdOut.println("Press any key to continue...");
                    StdIn.readString();
                }
                break;
                case 4:
                {
                    Utility.clear();
                    StdOut.println("Undoing all changes...");
                    if(network.undoChanges() && stations.undoChanges()) {
                        StdOut.println("Successfully undone all changes");
                    }else {
                        StdOut.println("Failed to undo all changes");
                    }
                    StdOut.println("Press any key to continue...");
                    StdIn.readString();
                }
                break;
                case 5:
                {
                    Utility.clear();
                    StdOut.println("Exiting...");
                    StdOut.println("Press any key to continue...");
                    StdIn.readString();
                    System.exit(0);
                }
                break;
            }
        }while (choice < 1 || choice > 5 || choice != 5);
    }


    public static void main(String[] args) {
        network = new Network();
        stations = new Stations();

        Utility.clear();
        if(Utility.initializeApp()) {
            Utility.clear();
            mainMenu();
        }else{
            Utility.clear();
            StdOut.println("Failed to initialize application. Exiting...");
            StdOut.println("Press any key to continue...");
            StdIn.readString();
            System.exit(0);
        }
    }

}
