import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

// Task 08: Create a class file called Ticket.
class Ticket {
    private char row;
    private int seat;
    private double price;
    private Person person;

    public Ticket (char row, int seat, Person person) {
        this.row = row;
        this.seat = seat;
        this.person = person;

        // Set price based on 0-based index seat number.
        if (seat >= 0 && seat <= 4) {
            this.price = 200;
        } else if (seat >= 5 && seat <= 8) {
            this.price = 150;
        } else if (seat >= 9 && seat <= 13) {
            this.price = 180;
        }
    }

    // Task 09: Added getters and setters of class Ticket.
    public char getRow() {
        return row;
    }

    public void setRow(char row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price, double ticketPrice2, double ticketPrice3) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    // Task 10: Method to print ticket information.
    public Object print_tickets_info() {
        System.out.println("Row: " + row);
        System.out.println("Seat: " + seat);
        System.out.println("Price of the air ticket is £" + price);
        System.out.println("Passenger Details: ");
        person.printPersonInfo();
        return(" ");
    }

    // Task 12: Method to save Ticket and Person information to a text file.
    public void save() {
        // Creating a file.
        String fileName = row + "" + seat + "txt";
        File file = new File(fileName);
        try {
            // Remove any existing file before saving the new ticket information, after cancellation of that particular seat.
            if (file.exists()) {
                file.delete();
            }
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write("Ticket Information:\n");
            fileWriter.write("Row: " + row + ", Seat: " + seat + "\n");
            fileWriter.write("Price: £" + price + "\n");
            fileWriter.write("Person Information:\n");
            fileWriter.write("Name: " + person.getName() + "\n");
            fileWriter.write("Surname: " + person.getSurname() + "\n");
            fileWriter.write("Email: " + person.getEmail() + "\n");
            fileWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Oops! I could not find the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the ticket information.");
            e.printStackTrace();
        }
    }
}
