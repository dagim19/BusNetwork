import java.awt.*;

public class App {
    private static int networkMenu() {

//        Thread.dumpStack();

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
        Network ntwrk = new Network();
        Stations stns = new Stations();
        ntwrk.buildNetwork();
        stns.buildStations();
        boolean open = true;
        while(open) {
            switch (networkMenu()) {
                case 1: {
                    switch (networkInformation()) {
                        case 1: {ntwrk.displayLinesForward();
                            break;
                        }
                        case 2: {
                            boolean wrongLine = true;
                            while(wrongLine){
                                int lineNum;
                                StdOut.println("Please enter line number. Enter -1 to go back.\n>");
                                lineNum = StdIn.readInt();
                                if(lineNum == -1) {
                                    break;
                                }
                                if (ntwrk.lineNumbers.contains(lineNum)) {
                                    for (Line l : ntwrk.lines) {
                                        if (l.getLineNumber() == lineNum) {
                                            l.traverseForward();
                                        }
                                    }
                                    wrongLine = false;
                                } else {
                                    StdOut.println("The line number you entered doesn't exist.");
                                }
                            }
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
                            String name;
                            int id;
                            StdOut.println("Please enter the station name\n>");
                            name = StdIn.readString();
                            if(stns.mapStation(name) != -1) {
                                StdOut.println("Please enter the station id\n>");
                                id = StdIn.readInt();
                                if(stns.mapStation(id) != null) {
                                    if(stns.addStation(new Station(name, id)))
                                        StdOut.println("Successfully added the new station.");
                                    else
                                        StdOut.println("Something is wrong.");
                                }else {
                                    StdOut.println("The station id already exits");
                                }
                            }else{
                                StdOut.println("The station name already exits.");
                            }
                            break;
                        }
                        case 2: {
                            StdOut.println("Change Station Name");
                            String stnName;
                            StdOut.println("Please enter the station name.\n>");
                            stnName = StdIn.readString();
                            int stnId = stns.mapStation(stnName);
                            if( stnId == -1) {
                                StdOut.println("This station doesn't exist");
                                break;
                            }else {
                                StdOut.println("Please enter the new name");
                                String newName = StdIn.readString();
                                Station s = new Station(stnName, stnId);
                                if(stns.deleteStation(s))
                                    stns.addStation(new Station(newName, stnId));
                                else
                                    StdOut.println("Can't perform this operation.");
                            }
                            break;
                        }
                        case 3: {
                            StdOut.println("Change Station Number");
                            int stnId;
                            StdOut.println("Please enter the station number.\n>");
                            stnId = StdIn.readInt();
                            String stnName = stns.mapStation(stnId);
                            if( stnName == null) {
                                StdOut.println("This station doesn't exist");
                                break;
                            }else {
                                StdOut.println("Please enter the new number.\n>");
                                int newId = StdIn.readInt();
                                Station s = new Station(stnName, stnId);
                                if(stns.deleteStation(s))
                                    stns.addStation(new Station(stnName, newId));
                                else
                                    StdOut.println("Can't perform this operation.");
                            }
                            break;
                        }
                        case 4: {
                            StdOut.println("Insert A New Station On A Line");
                            int lineNumber;
                            StdOut.println("Please enter the line number.\n>");
                            lineNumber = StdIn.readInt();
                            Line l = ntwrk.getLine(lineNumber);
                            if(l != null) {
                                int choice;
                                StdOut.println("Press 1: To add an existing station.");
                                StdOut.println("Press 2: To create an new station to add.");
                                choice = StdIn.readInt();
                                if(choice == 1) {
                                    int stnNum;
                                    StdOut.println("Please enter the station number.");
                                    stnNum = StdIn.readInt();
                                    Station s = stns.(stnNum);

                                }
                            }
                            break;
                        }
                        case 5: {
                            StdOut.println("Delete Station From Line");
                            StdOut.println("Please enter the line number you want to edit.\n>");
                            int lineId = StdIn.readInt();
                            if(!ntwrk.lineNumbers.contains(lineId)) {
                                StdOut.println("The line number you entered doesn't exist");
                                break;
                            }
                            StdOut.println("Please enter the station number you want to delete.\n>");
                            int stnId = StdIn.readInt();
                            if(!stns.stnIds.contains(stnId)) {
                                StdOut.println("The station doesn't exist");
                                break;
                            }

                            Line l = ntwrk.getLine(lineId);
                            l.deleteStation(new StationData(stnId, l.))
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
        if(ntwrk.updateNetworkFile()) {
            StdOut.println("[!!] Can't update network file. some dependencies might be deleted")
        }
    }

}
