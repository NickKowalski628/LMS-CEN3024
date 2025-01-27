/*
 * Nicholas Kowalski
 * CEN3024C
 * 26Jan2025
 * Class: PatronInfo
 * This class holds the information needed for a Patron within the LMS. Each patron has their
 * own ID, Name, Address, and fee amount. This class also contains methods to retrieve and properly
 * store information from text files.
 */

public class PatronInfo {
    private String fullName;
    private int patronID;
    private String patronAddress;
    private double currentFees;

    public PatronInfo(int patronID, String fullName, String patronAddress, double currentFees) {
        this.patronID = patronID;
        this.fullName = fullName;
        this.patronAddress = patronAddress;
        this.currentFees = currentFees;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getPatronID() {
        return patronID;
    }

    public void setPatronID(int patronID) {
        this.patronID = patronID;
    }

    public String getPatronAddress() {
        return patronAddress;
    }

    public void setPatronAddress(String patronAddress) {
        this.patronAddress = patronAddress;
    }

    public double getCurrentFees() {
        return currentFees;
    }

    public void setCurrentFees(double currentFees) {
        this.currentFees = currentFees;
    }

    /*
     * Method: readFromFile
     * Parameters: String line
     * Return: none
     * Purpose: This method parses data from a selected file and updates the appropriate fields
     * within the class.
     */
    public void readFromFile(String line) {
        String[] fields = line.split("-");
        this.patronID = Integer.parseInt(fields[0]); // 7-digit ID
        this.fullName = fields[1];
        this.patronAddress = fields[2];
        this.currentFees = Double.parseDouble(fields[3]);
    }

    /*
     * Method: toString
     * Parameters: none
     * Return: String
     * Purpose: This method takes the data found on a file that the user selects to load and presents
     * it in a way that is readable in the specified formatting.
     */
    @Override
    public String toString() {
        return String.format("%07d-%s-%s-%.2f", patronID, fullName, patronAddress, currentFees);
    }
}
