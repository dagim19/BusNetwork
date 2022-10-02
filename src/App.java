import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class App {

    static Network network;
    static Stations stations;

    private static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }



    private static int editChoice() {
        int choice;
        clear();
        StdOut.println("What do you want to do?");
        StdOut.println("Press 1: Add a new line");
        StdOut.println("Press 2: Add a new station to an existing line");
        StdOut.println("Press 3: Remove a line");
        StdOut.println("Press 4: Remove a station from an existing line");
        StdOut.println("Press 5: Change the distance between two stations");
        StdOut.println("Press 6: Change the line number of a line");
        StdOut.println("Press 7: Change the station number of a station");
        StdOut.println("Press 8: Return to main menu");
        StdOut.print(">");
        choice = StdIn.readInt();
        return choice;
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
                   clear();
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
                       StdOut.println("Press any key to continue...");
                       StdIn.readString();
                   }else{
                          StdOut.println("Failed to add the new line in the network");
                          StdOut.println("Press any key to continue...");
                          StdIn.readString();
                   }
                }
                break;
                case 2:
                {
                    // add new station to an existing station.
                    clear();
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
                        StdOut.println("Press any key to continue...");
                        StdIn.readString();
                    }else{
                        StdOut.println("Failed to add the new station");
                        StdOut.println("Press any key to continue...");
                        StdIn.readString();
                    }
                }
                break;
                case 3:
                {
                    // remove a line
                    clear();
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
                        StdOut.println("Press any key to continue...");
                        StdIn.readString();
                    }else{
                        StdOut.println("Failed to remove the line");
                        StdOut.println("Press any key to continue...");
                        StdIn.readString();
                    }
                }
                break;
                case 4:
                {
                    clear();
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
                       StdOut.println("Press any key to continue...");
                       StdIn.readString();
                    }else{
                        StdOut.println("Failed to delete the station");
                        StdOut.println("Press any key to continue...");
                        StdIn.readString();
                    }
                }
                break;
                case 5:
                {
                    // change the distance between two stations
                    clear();
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
                       StdOut.println("Press any key to continue...");
                       StdIn.readString();
                    }else {
                        StdOut.println("Failed to change the distance");
                        StdOut.println("Press any key to continue...");
                        StdIn.readString();
                    }
                }
                break;
                case 6:
                {
                    // Change the line number of a line
                    clear();
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
                        StdOut.println("Press any key to continue...");
                        StdIn.readString();
                    }else {
                        StdOut.println("Failed to change the line number");
                        StdOut.println("Press any key to continue...");
                        StdIn.readString();
                    }
                }
                break;
                case 7:
                {
                    // Change the station number of a station
                    clear();

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
                        StdOut.println("Press any key to continue...");
                        StdIn.readString();
                    }else{
                        StdOut.println("Failed to change the station number");
                        StdOut.println("Press any key to continue...");
                        StdIn.readString();
                    }
                }
                break;
                case 8:
                {
                    // back to main menu
                    clear();
                    return;
                }
            }
        }while(choice < 1 || choice > 8 || choice != 8);
    }

    private static int networkInfoChoice() {
        int choice;
        clear();
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
                    clear();
                    StdOut.println("Network Information");
                    StdOut.println("There are " + network.nubLines + " lines in the network.");
                    StdOut.println("========================================");
                    StdOut.println("Line Number\tNumber of Stations");
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
                    clear();
                    StdOut.println("Line Information");
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
                    clear();
                    StdOut.println("Station Information");
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
                    clear();
                    StdOut.println("Shortest Path Information");
                    StdOut.println("Enter the station number of the source station. (Enter -1 to abort)\n>");
                    int sourceStnId = StdIn.readInt();

                    do {
                        if(!stations.stnIds.contains(sourceStnId)) {
                            StdOut.println("The station number you entered does not exist. Please enter a valid station number. (Enter -1 to abort)\n>");
                            sourceStnId = StdIn.readInt();
                        }
                        else break;
                    }while(!stations.stnIds.contains(sourceStnId) && sourceStnId != -1);

                    if(sourceStnId == -1)
                        break;

                    StdOut.println("Enter the station number of the destination station. (Enter -1 to abort)\n>");
                    int destStnId = StdIn.readInt();

                    do {
                        if(!stations.stnIds.contains(destStnId)) {
                            StdOut.println("The station number you entered does not exist. Please enter a valid station number. (Enter -1 to abort)\n>");
                            destStnId = StdIn.readInt();
                        }
                        else break;
                    }while(!stations.stnIds.contains(destStnId) && destStnId != -1);

                    if(destStnId == -1)
                        break;

                    StdOut.println("========================================");
                    StdOut.println("Shortest Path");
                    StdOut.println("========================================");
//                    StdOut.println(sourceStnId + "\t\t" + destStnId + "\t\t" + network.shortestPath(sourceStnId, destStnId));
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
                    clear();
                    return;
                }
                case 6:
                {
                    clear();
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
                    clear();
                    handleInfo();
                }
                break;
                case 2:
                {
                    clear();
                    handleEdit();
                }
                break;
                case 3:
                {
                    clear();
                    StdOut.println("Saving changes...");
                    if (network.updateNetworkFile() && stations.updateStnFile()){
                        StdOut.println("Successfully saved changes");
                        StdOut.println("Press any key to continue...");
                        StdIn.readString();
                    }else {
                        StdOut.println("Failed to save changes");
                        StdOut.println("Press any key to continue...");
                        StdIn.readString();
                    }
                }
                break;
                case 4:
                {
                    clear();
                    StdOut.println("Undoing all changes...");
                    if(network.undoChanges() && stations.undoChanges()) {
                        StdOut.println("Successfully undone all changes");
                        StdOut.println("Press any key to continue...");
                        StdIn.readString();
                    }else {
                        StdOut.println("Failed to undo all changes");
                        StdOut.println("Press any key to continue...");
                        StdIn.readString();
                    }
                }
                break;
                case 5:
                {
                    clear();
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

        try {
            network.buildNetwork();
            stations.buildStations();

            mainMenu();


        }catch (FileNotFoundException e) {
            StdOut.println("Can't find the important files exiting...");
        }
    }

}
