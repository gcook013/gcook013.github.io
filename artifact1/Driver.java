import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Driver {

    // class variables (add more as needed)
    private static ArrayList<Ship> shipList = new ArrayList();
    private static ArrayList<Cruise> cruiseList = new ArrayList();
    private static ArrayList<Passenger> passengerList = new ArrayList();


    public static void main(String[] args) {

        initializeShipList();       // initial ships
        initializeCruiseList();     // initial cruises
        initializePassengerList();  // initial passengers

        // add loop and code here that accepts and validates user input
        // and takes the appropriate action. include appropriate
        // user feedback and redisplay the menu as needed
        Scanner scnr = new Scanner(System.in);
        
        displayMenu();
        // System.out.println(systemMenu);
        // use method chaining to capture the
        // user input and convert it to upper case to render
        // menu selection case insensitive
        String userSelection = scnr.next().toUpperCase();
        scnr.nextLine();

        // loop until the user chooses to exit
        // a while loop is used because the number of iterations is unknown
        while (!userSelection.equalsIgnoreCase("X")) {
            // a switch statement is used due to the number of menu items
            // and is cleaner than if-else and can catch exceptions easier
            try {
                switch (userSelection) {
                    case "1":
                        addShip();
                        break;
                    case "2":
                        editShip();
                        break;
                    case "3":
                    	addCruise();
                        break;
                    case "4":
                        editCruise();
                        break;
                    case "5":
                        addPassenger();
                        break;
                    case "6":
                        editPassenger();
                        break;
                    case "A":
                        printShipList("name");
                        break;
                    case "B":
                        printShipList("active");
                        break;
                    case "C":
                        printShipList("full");
                        break;
                    case "D":
                        printCruiseList("list");
                        break;
                    case "E":
                        printCruiseList("details");
                        break;
                    case "F":
                        printPassengerList();
                        break;
                    case "X":
                        break;
                    default:
                        throw new Exception("Invalid Entry. Please try again");
                }
            }
            catch (Exception excpt){
                System.out.println(excpt.getMessage());
            }

            displayMenu();
            userSelection = scnr.next().toUpperCase();
            scnr.nextLine();
        }

        System.out.println("Goodbye.  Thanks for choosing Luxury Ocean Cruise Outings!");
        System.out.println("We hope to see you again soon!");
        scnr.close();   // close the Scanner object to prevent memory leak

        return;     // return even when return type is void to clear the stack frame
    }

    // hardcoded ship data for testing
    // Initialize ship list
    public static void initializeShipList() {
        add("Candy Cane", 20, 40, 10, 60, true);
        add("Peppermint Stick", 10, 20, 5, 40, true);
        add("Bon Bon", 12, 18, 2, 24, false);
        add("Candy Corn", 12, 18, 2, 24, false);
        return;
    }

    // hardcoded cruise data for testing
    // Initialize cruise list
    public static void initializeCruiseList() {
        Cruise newCruise = new Cruise("Southern Swirl", "Candy Cane", "Miami", "Cuba", "Miami");
        cruiseList.add(newCruise);
        return;
    }

    // hardcoded cruise data for testing
    // Initialize passenger list
    public static void initializePassengerList() {
        Passenger newPassenger1 = new Passenger("Neo Anderson", "Southern Swirl", "STE");
        passengerList.add(newPassenger1);

        Passenger newPassenger2 = new Passenger("Trinity", "Southern Swirl", "STE");
        passengerList.add(newPassenger2);

        Passenger newPassenger3 = new Passenger("Morpheus", "Southern Swirl", "BAL");
        passengerList.add(newPassenger3);
        return;
    }

    // custom method to add ships to the shipList ArrayList
    public static void add(String tName, int tBalcony, int tOceanView,
                           int tSuite, int tInterior, boolean tInService) {
        Ship newShip = new Ship(tName, tBalcony, tOceanView, tSuite, tInterior, tInService);
        shipList.add(newShip);
        return;
    }


    public static void printShipList(String listType) {
        // printShipList() method prints list of ships from the
        // shipList ArrayList. There are three different outputs
        // based on the listType String parameter:
        // name - prints a list of ship names only
        // active - prints a list of ship names that are "in service"
        // full - prints tabbed data on all ships

        if (shipList.size() < 1) {
            System.out.println("\nThere are no ships to print.");
            return;
        }
        if (listType == "name") {
            System.out.println("\n\nSHIP LIST - Name");
            for (int i = 0; i < shipList.size(); i++) {
                System.out.println(shipList.get(i));
            }
        } else if (listType == "active") {
        	// ENHANCEMENT ON ORIGINAL CODE
        	// determine if ships are active and print active ships
            System.out.println("\n\nSHIP LIST - Active");
            System.out.println("-----------------------------------------------");
            System.out.println("                    Number of Rooms\t\tIn");
            System.out.print("SHIP NAME           Bal\tOV\tSte\tInt\tService");
            System.out.println("\n-----------------------------------------------");
            for (Ship eachShip: shipList)
                if (eachShip.getInService()) {
                	eachShip.printShipData();
                }            
            
        } else if (listType == "full") {
            // print full ship listing
            System.out.println("\n\nSHIP LIST - Full");
            System.out.println("-----------------------------------------------");
            System.out.println("                    Number of Rooms\t\tIn");
            System.out.print("SHIP NAME           Bal\tOV\tSte\tInt\tService");
            System.out.println("\n-----------------------------------------------");
            for (Ship eachShip: shipList)
                eachShip.printShipData();

        } else {
        	// return message if string is not defined
            System.out.println("\n\nError: List type not defined.");
        }
        return;
    }

    public static void printCruiseList(String listType) {
        if (cruiseList.size() < 1) {
            System.out.println("\nThere are no cruises to print.");
            return;
        }
        if (listType == "list") {
            System.out.println("\n\nCRUISE LIST");
            for (int i=0; i < cruiseList.size(); i++) {
                System.out.println(cruiseList.get(i));
            }
        } else if (listType == "details") {
            System.out.println("\n\nCRUISE LIST - Details");
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println("                                      |----------------------PORTS-----------------------|");
            System.out.print("CRUISE NAME         SHIP NAME           DEPARTURE           DESTINATION         RETURN");
            System.out.println("\n-----------------------------------------------------------------------------------------");
            for (Cruise eachCruise: cruiseList)
                eachCruise.printCruiseDetails();
        } else {
            System.out.println("\n\nError: List type not defined.");
        }
        
        return;
    }

    public static void printPassengerList() {
        if (passengerList.size() < 1) {
            System.out.println("\nThere are no passengers to print.");
            return;
        }
        System.out.println("\n\nPASSENGER LIST");
        System.out.println("-----------------------------------------------------");
        System.out.print("PASSENGER NAME      CRUISE              ROOM TYPE");
        System.out.println("\n-----------------------------------------------------");
        for (Passenger eachPassenger: passengerList)
            eachPassenger.printPassenger();
        
        return;
    }

    // display text-based menu
    public static void displayMenu() {

        System.out.println("\n\n");
        System.out.println("\t\t\tLuxury Ocean Cruise Outings");
        System.out.println("\t\t\t\t\tSystem Menu\n");
        System.out.println("[1] Add Ship            [A] Print Ship Names");
        System.out.println("[2] Edit Ship           [B] Print Ship In Service List");
        System.out.println("[3] Add Cruise          [C] Print Ship Full List");
        System.out.println("[4] Edit Cruise         [D] Print Cruise List");
        System.out.println("[5] Add Passenger       [E] Print Cruise Details");
        System.out.println("[6] Edit Passenger      [F] Print Passenger List");
        System.out.println("[x] Exit System");
        System.out.println("\nEnter a menu selection: ");
        
        return;
    }

    // Add a New Ship
    public static void addShip() {

        // complete this method

        Scanner newShipInput = new Scanner(System.in);
        System.out.println("Enter the new ship's name: ");
        String newShipName = newShipInput.nextLine();

        // ensure new ship name does not already exist
        for (Ship eachShip: shipList) {
            if (eachShip.getShipName().equalsIgnoreCase(newShipName)) {
                System.out.println("That ship is already in the system. Exiting to menu...");
                return; // quits addShip() method processing
            } 
        }

        // get number of balcony rooms ensuring input is valid
        
        int newBalconyNumber;
        int newOceanNumber;
        int newSuiteNumber;
        int newInteriorNumber;
        try {
        	System.out.println("Enter number of balcony rooms: ");
        	newBalconyNumber = parseInt(newShipInput.nextLine());
        	System.out.println("Enter number of ocean view rooms: ");
        	newOceanNumber = parseInt(newShipInput.nextLine());
        	System.out.println("Enter number of suites: ");
        	newSuiteNumber = parseInt(newShipInput.nextLine());
        	System.out.println("Enter number of interior rooms: ");
        	newInteriorNumber = parseInt(newShipInput.nextLine());
        }
        catch(NumberFormatException nFE) {
        	System.out.println("Room types must be entered with a number. Exiting to menu...");
            return; // quits addShip() method processing
        }
        
        System.out.println("Is the ship currently in serviece (Y/N)?");
        boolean inService;
        if (newShipInput.nextLine().equalsIgnoreCase("y")) {
        	inService = true;
        } else if (newShipInput.nextLine().equalsIgnoreCase("n")) {
        	inService = false;
        } else {
        	System.out.println("Service status must be 'y' or 'n'. Exiting to menu...");
            return; // quits addShip() method processing
        }

    	Ship newShip = new Ship(newShipName, newBalconyNumber, newOceanNumber, newSuiteNumber, newInteriorNumber, inService);
        shipList.add(newShip);
        
        return;
        
    }

    // ENHANCEMENT ON ORIGINAL CODE
    // Edit an existing ship
    public static void editShip() {
    	// create and set flag for ship existence
    	boolean exists = false;
        
        Scanner editShipInput = new Scanner(System.in);
        System.out.println("Enter the ship's name you wish to edit: ");
        String editShipName = editShipInput.nextLine();

        // ensure ship name already exists and raise flag
        System.out.println(editShipName);
        for (Ship eachShip: shipList) {
            if (eachShip.getShipName().equalsIgnoreCase(editShipName)) {
                exists = true;
            } 
        }

        // check flag and respond if false
        if (!exists) {
        	System.out.println("That ship does not exist in the system. Add the ship from the main menu. Exiting to menu...");
        	return; // quits editShip() method processing
        }
        
    	// Print current information for ship
        System.out.println("\n\nSHIP LIST - " + editShipName);
        System.out.println("-----------------------------------------------");
        System.out.println("                    Number of Rooms\t\tIn");
        System.out.print("SHIP NAME           Bal\tOV\tSte\tInt\tService");
        System.out.println("\n-----------------------------------------------");
        for (Ship eachShip: shipList) {
            if (eachShip.getShipName().equalsIgnoreCase(editShipName)) {
            	eachShip.printShipData();
            } 
        }
        
        // display menu to user to provide property for changes
        System.out.println("Which property do you want to change?");
        System.out.println("1: Number of Balcony Rooms");
        System.out.println("2: Number of Ocean View Rooms");
        System.out.println("3: Number of Suites");
        System.out.println("4: Number of Interior Rooms");
        System.out.println("5: Service Status");
        
        // check user input and then use set property to change appropriate field
        int input = parseInt(editShipInput.nextLine());
        if (input == 1) {
        	System.out.println("Enter new number of Balcony Rooms");
            for (Ship eachShip: shipList) {
                if (eachShip.getShipName().equalsIgnoreCase(editShipName)) {
                	eachShip.setRoomBalcony(parseInt(editShipInput.nextLine()));
                } 
            }
        } else if (input == 2) {
        	System.out.println("Enter new number of Ocean View Rooms");
            for (Ship eachShip: shipList) {
                if (eachShip.getShipName().equalsIgnoreCase(editShipName)) {
                	eachShip.setRoomOceanView(parseInt(editShipInput.nextLine()));
                } 
            }
        } else if (input == 3) {
        	System.out.println("Enter new number of Suites");
            for (Ship eachShip: shipList) {
                if (eachShip.getShipName().equalsIgnoreCase(editShipName)) {
                	eachShip.setRoomSuite(parseInt(editShipInput.nextLine()));
                } 
            }
        } else if (input == 4) {
        	System.out.println("Enter new number of Interior Rooms");
            for (Ship eachShip: shipList) {
                if (eachShip.getShipName().equalsIgnoreCase(editShipName)) {
                	eachShip.setRoomInterior(parseInt(editShipInput.nextLine()));
                } 
            }
        } else if (input == 5) {
        	System.out.println("Enter new in-service status (Y/N)?");
        	boolean inService;
        	String inServiceInput = editShipInput.nextLine();
            if (inServiceInput.equalsIgnoreCase("y")) {
            	inService = true;
            } else if (inServiceInput.equalsIgnoreCase("n")) {
            	inService = false;
            } else {
            	System.out.println("Service status must be 'y' or 'n'. Exiting to menu...");
                return; // quits editShip() method processing
            }
        	for (Ship eachShip: shipList) {
                if (eachShip.getShipName().equalsIgnoreCase(editShipName)) {
                	eachShip.setInService(inService);
                } 
            }
        } else {
        	System.out.println("Not a valid entry");
        	return;
        }
        
        return;
    }

    // Add a New Cruise
    public static void addCruise() {

        Scanner newCruiseInput = new Scanner(System.in);
        System.out.println("Enter the new cruise name: ");
        String newCruiseName = newCruiseInput.nextLine();

        // ensure new cruise name does not already exist
        for (Cruise eachCruise: cruiseList) {
            if (eachCruise.getCruiseName().equalsIgnoreCase(newCruiseName)) {
                System.out.println("That cruise is already in the system. Exiting to menu...");
                return; // quits addCruise() method processing
            }
        }

        // get ship name for cruise
        System.out.println("Enter ship name: ");
        String newCruiseShipName = newCruiseInput.nextLine();

        // ensure ship exists
        for (Ship eachShip: shipList) {
            if (eachShip.getShipName().equalsIgnoreCase(newCruiseShipName)) {
                // ship does exist
            	break;
            } else {
                System.out.println("That ship does not exist in the system. Exiting to menu...");
                return; // quits addCruise() method processing
            }
        }

        // get departure port
        System.out.println("Enter departure port for the cruise: ");
        String departurePort = newCruiseInput.nextLine();
        
        // get destination
        System.out.println("Enter destination for the cruise: ");
        String destination = newCruiseInput.nextLine();
        
        // get return port
        System.out.println("Enter return port for the cruise: ");
        String returnPort = newCruiseInput.nextLine();

        Cruise newCruise= new Cruise(newCruiseName, newCruiseShipName, departurePort, destination, returnPort);
        cruiseList.add(newCruise);
        
        return;
        
    }

    // ENHANCEMENT ON ORIGINAL CODE
    // Edit an existing cruise
    public static void editCruise() {

    	// create and set flag for cruise existence
    	boolean exists = false;
        
        Scanner editCruiseInput = new Scanner(System.in);
        System.out.println("Enter the ship's name you wish to edit: ");
        String editCruiseName = editCruiseInput.nextLine();

        // ensure cruise name already exists and raise flag
        System.out.println(editCruiseName);
        for (Cruise eachCruise: cruiseList) {
            if (eachCruise.getCruiseName().equalsIgnoreCase(editCruiseName)) {
                exists = true;
            } 
        }

        // check flag and respond if false
        if (!exists) {
        	System.out.println("That cruise does not exist in the system. Add the cruise from the main menu. Exiting to menu...");
        	return; // quits editShip() method processing
        }
        
    	// Print current information for Cruise
        System.out.println("\n\nCRUISE - " + editCruiseName);
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("                                      |----------------------PORTS-----------------------|");
        System.out.print("CRUISE NAME         SHIP NAME           DEPARTURE           DESTINATION         RETURN");
        System.out.println("\n-----------------------------------------------------------------------------------------");
        for (Cruise eachCruise: cruiseList)
            if (eachCruise.getCruiseName().equalsIgnoreCase(editCruiseName)) {
            	eachCruise.printCruiseDetails();
            } 
        
        // display menu to user to provide property for changes
        System.out.println("Which property do you want to change?");
        System.out.println("1: Ship Name");
        System.out.println("2: Departure Port");
        System.out.println("3: Destination");
        System.out.println("4: Return Port");
                
        // check user input and then use set property to change appropriate field
        int input = parseInt(editCruiseInput.nextLine());
        if (input == 1) {
        	System.out.println("Enter new Ship Name");
            for (Cruise eachCruise: cruiseList)
                if (eachCruise.getCruiseName().equalsIgnoreCase(editCruiseName)) {
                	eachCruise.setCruiseShipName(editCruiseInput.nextLine());
                } 
        } else if (input == 2) {
        	System.out.println("Enter new Departure Port");
            for (Cruise eachCruise: cruiseList)
                if (eachCruise.getCruiseName().equalsIgnoreCase(editCruiseName)) {
                	eachCruise.setDeparturePort(editCruiseInput.nextLine());
                } 
        } else if (input == 3) {
        	System.out.println("Enter new Destination");
            for (Cruise eachCruise: cruiseList)
                if (eachCruise.getCruiseName().equalsIgnoreCase(editCruiseName)) {
                	eachCruise.setDestination(editCruiseInput.nextLine());
                } 
        } else if (input == 4) {
        	System.out.println("Enter new Return Port");
            for (Cruise eachCruise: cruiseList)
                if (eachCruise.getCruiseName().equalsIgnoreCase(editCruiseName)) {
                	eachCruise.setReturnPort(editCruiseInput.nextLine());
                }
        	System.out.println("Not a valid entry");
        	return;
        }
        
        return;

    }

    // Add a New Passenger
    public static void addPassenger() {

        Scanner newPassengerInput = new Scanner(System.in);
        System.out.println("Enter the new passenger's name: ");
        String newPassengerName = newPassengerInput.nextLine();

        // ensure new passenger name does not already exist
        for (Passenger eachPassenger: passengerList) {
            if (eachPassenger.getPassengerName().equalsIgnoreCase(newPassengerName)) {
                System.out.println("That passenger is already in the system. Exiting to menu...");
                return; // quits addPassenger() method processing
            }
        }

        // get cruise name for passenger
        System.out.println("Enter cruise name: ");
        String newCruiseName = newPassengerInput.nextLine();

        // ensure cruise exists
        for (Cruise eachCruise: cruiseList) {
            if (eachCruise.getCruiseName().equalsIgnoreCase(newCruiseName)) {
                // cruise does exist
            	break;
            } else {
                System.out.println("That cruise does not exist in the system. Exiting to menu...");
                return; // quits addPassenger() method processing
            }
        }

        // get room type
        System.out.println("Enter Room Type (BAL, OV, STE, or INT: ");
        String room = newPassengerInput.nextLine();
        // validate room type
        if ((room.equalsIgnoreCase("BAL")) || (room.equalsIgnoreCase("OV")) ||
                (room.equalsIgnoreCase("STE")) || (room.equalsIgnoreCase("INT"))) {
            // validation passed - add passenger
            Passenger newPassenger = new Passenger(newPassengerName, newCruiseName, room.toUpperCase());
            passengerList.add(newPassenger);
        } else {
            System.out.println("Invalid input. Exiting to menu...");
            return; // quits addPassenger() method processing
        }
        
        return;
    }

    // ENHANCEMENT ON ORIGINAL CODE
    // Edit an existing passenger
    public static void editPassenger() {

    	// create and set flag for passenger existence
    	boolean exists = false;
        
        Scanner editPassengerInput = new Scanner(System.in);
        System.out.println("Enter the ship's name you wish to edit: ");
        String editPassengerName = editPassengerInput.nextLine();

        // ensure cruise name already exists and raise flag
        System.out.println(editPassengerName);
        for (Passenger eachPassenger: passengerList) {
            if (eachPassenger.getPassengerName().equalsIgnoreCase(editPassengerName)) {
                exists = true;
            } 
        }

        // check flag and respond if false
        if (!exists) {
        	System.out.println("That passenger does not exist in the system. Add the passenger from the main menu. Exiting to menu...");
        	return; // quits editShip() method processing
        }
        
    	// Print current information for passenger
        System.out.println("\n\nPASSENGER - " + editPassengerName);
        System.out.println("-----------------------------------------------------");
        System.out.print("PASSENGER NAME      CRUISE              ROOM TYPE");
        System.out.println("\n-----------------------------------------------------");
        for (Passenger eachPassenger: passengerList) {
            if (eachPassenger.getPassengerName().equalsIgnoreCase(editPassengerName)) {
            	eachPassenger.printPassenger();
            }
        }
        
        // display menu to user to provide property for changes
        System.out.println("Which property do you want to change?");
        System.out.println("1: Cruise Name");
        System.out.println("2: Room type");
        
        // check user input and then use set property to change appropriate field
        int input = parseInt(editPassengerInput.nextLine());
        if (input == 1) {
        	System.out.println("Enter new Cruise Name");
            for (Passenger eachPassenger: passengerList) {
                if (eachPassenger.getPassengerName().equalsIgnoreCase(editPassengerName)) {
                	eachPassenger.setPassengerCruise(editPassengerInput.nextLine());;
                }
            }
        } else if (input == 2) {
        	System.out.println("Enter new Room Type (BAL, OV, STE, or INT: ");
            for (Passenger eachPassenger: passengerList) {
                if (eachPassenger.getPassengerName().equalsIgnoreCase(editPassengerName)) {
                	eachPassenger.setPassengerRoomType(editPassengerInput.nextLine().toUpperCase());;
                }
            }
        } else {
        	System.out.println("Not a valid entry");
        	return;
        }
        
        return;
    	
    }

    // Method to check if input is a number
    public static boolean isANumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i)) == false)
                return false;
        }
        return true;
    }

}
