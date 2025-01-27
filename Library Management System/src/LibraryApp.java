import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/*
* Nicholas Kowalski
* CEN3024C
* 26Jan2025
* Class: LibraryApp
* This class holds the main method as well as the menu method and each method that executes based on user input.
* It allows the user to load and store user data, display all patron information, and to add/remove patrons individually
* based on the selected option through the on-screen menu
 */



public class LibraryApp {
    private ArrayList<PatronInfo> patrons = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    /*
     * Method: loadFromFile
     * Parameters:none
     * Return: none
     * Purpose: Allows user to specify location of file to import patron data
     * and stores it to ArrayList and displays it in the required formatting with
     * input validation to ensure the file is found a0d can be read.
     */
    public void loadFromFile() {
        System.out.print("Enter the file path to load patron data: ");
        String filepath = scanner.nextLine();

        File file = new File(filepath);
        if (!file.exists() || !file.isFile()) {
            System.out.println("File not found or invalid file format. Please try again.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            patrons.clear();
            String line;
            while ((line = br.readLine()) != null) {
                PatronInfo patron = new PatronInfo(0, "", "", 0);
                patron.readFromFile(line);
                patrons.add(patron);
            }
            System.out.println("Patrons loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        displayPatrons();
    }

    /*
     * Method: displayPatrons
     * Parameters: none
     * Return: none
     * Purpose: Allows the user to display all patrons currently within the ArrayList.
     * this method also will display the appropriate message if no patrons are found.
     */
    public void displayPatrons() {
        if (patrons.isEmpty()) {
            System.out.println("No patron data available. Please load frome a file or enter patron data.");
            return;
        }

        System.out.println("\nPatron List:");
        for (PatronInfo patron : patrons) {
            System.out.println(patron.toString());
        }
    }

    /*
     * Method: addPatron
     * Parameters: none
     * Return: none
     * Purpose: This method allows the user to enter a patron manually. This is primarily
     * used when the library has a new patron or does not need to load an entire textfile
     * to input information.
     */
    public void addPatron() {
        System.out.print("Enter Patron ID (7 digits): ");
        int patronID = 0;
        boolean validID = false;
        while (!validID) {
            String idInput = scanner.nextLine();

            //Ensures a 7-digit ID is entered
            if (idInput.length() == 7 && idInput.chars().allMatch(Character::isDigit)) {
                patronID = Integer.parseInt(idInput);

                //Check if the patron ID already exists
                boolean duplicatePatron = false;
                for (PatronInfo patron : patrons) {
                    if (patron.getPatronID() == patronID) {
                        duplicatePatron = true;
                        break;
                    }
                }

                if (duplicatePatron) {
                    System.out.print("Patron ID already exists. Please enter a unique ID. ");
                } else {
                    validID = true;
                }
            } else {
                System.out.print("Invalid entry. Please enter a valid 7-digit ID. ");
            }
        }

        System.out.print("Enter Patron Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Patron Address: ");
        String address = scanner.nextLine();

        //this function will ensure fees range between the specified 0-250
        System.out.print("Enter Overdue Fees: ");
        double fees = 0.0;
        boolean validfees = false;
        while (!validfees) {
            String feesInput = scanner.nextLine();
            try {
                fees = Double.parseDouble(feesInput);
                if (fees < 0 || fees > 250) {
                    System.out.print("Invalid entry. Please enter values between 0 and 250. ");
                } else {
                    validfees = true;
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid entry. Please enter a valid fee ammount. ");
            }
        }


        PatronInfo patron = new PatronInfo(patronID, name, address, fees);
        patrons.add(patron);
        System.out.println("Patron added successfully!");
        displayPatrons();
    }

    /*
     * Method: removePatron
     * Parameters: none
     * Return: none
     * Purpose: Allows the user to continuously remove patrons from the list. This method also allows the user
     * to display the updated list after removal.
     */
    public void removePatron() {
        boolean removeMore = true;
        //loop that allows for the user to remove more than one patron in succession
        while (removeMore) {
            try{
            System.out.print("Enter Patron ID to remove: ");
            int patronID = Integer.parseInt(scanner.nextLine());

            PatronInfo deletePatron = null;
            for (PatronInfo patron : patrons) {
                if (patron.getPatronID() == patronID) {
                    deletePatron = patron;
                    break;
                }
            }
            if (deletePatron != null) {
                patrons.remove(deletePatron);
                System.out.println("Patron " + patronID + " removed successfully!");
            } else {
                System.out.println("Patron with ID " + patronID + " not found.");
            }

            //handles input validation for yes/no or otherwise illegal entries
                boolean validEntry = false;
                while (!validEntry) {
                    System.out.print("Would you like to remove another patron? (yes/no): ");
                    String response = scanner.nextLine();
                    if (response.equalsIgnoreCase("no")) {
                        removeMore = false;
                        validEntry = true;
                    } else if (response.equalsIgnoreCase("yes")) {
                        validEntry = true;
                    } else {
                        System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid entry. Please enter a valid patron ID.");
            }
        }
        displayPatrons();
    }



    /*
     * Method: showMainMenu
     * Parameters: none
     * Return: none
     * Purpose: This method displays a method that allows the user of the LMS to interact
     * with each option and its corresponding function.
     */
        public void showMainMenu () {
            while (true) {
                try {
                    System.out.println("\nLibrary Management System");
                    System.out.println("1. Load Patron Data from File");
                    System.out.println("2. Display Patrons");
                    System.out.println("3. Add Patron");
                    System.out.println("4. Remove Patron");
                    System.out.println("0. Exit");
                    System.out.print("Enter your choice: ");

                    int choice = Integer.parseInt(scanner.nextLine());

                    switch (choice) {
                        case 1:
                            loadFromFile();
                            break;
                        case 2:
                            displayPatrons();
                            break;
                        case 3:
                            addPatron();
                            break;
                        case 4:
                            removePatron();
                            break;
                        case 0:
                            System.out.println("Exiting the program. Goodbye!");
                            return;
                            default:
                            System.out.println("Invalid choice! Please enter a number between 0 and 4.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a number.");
                }
            }
        }

    /*
     * Method: main
     * Parameters:none
     * Return: none
     * Purpose: This method starts the LMS
     */
        public static void main (String[]args){
            LibraryApp app = new LibraryApp();
            app.showMainMenu();
        }
    }


