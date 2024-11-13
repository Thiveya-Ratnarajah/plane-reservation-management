import java.util.Scanner;
import java.util.InputMismatchException;

// Task 01: Created a class file named, 'PlaneManagement', and creation of main method.
public class PlaneManagement {
    // Task 01: Use of standard arrays.
    private static final int ROWS = 4;  // Indicating the number of rows in the plane constantly.
    private static final int[] seatCountPerRow = {14, 12, 12, 14};  // Number of seats allocated in each row constantly.
    private static int[][] SEATS = new int[ROWS][];    // Declaration of 2-dimensional array & track the seat status (0: available, 1: sold).
    private static final Ticket[] soldTickets = new Ticket[52]; // Record the details of the sold tickets.
    private static int soldTicketCount = 0; // Declaration of variable to track the count of tickets sold.


    /* A main method:
        * This is the entry point of this program.
        * This displays the welcome note and menu options list on the user interface.
        * Tasks: Prompt for user input, and check user selected option with the switch-case
        and run the relevant part of the program.
        * Handles error exception.
        * Exit point of the program also added here. By entering '0' user can quit the program.
     */
    public static void main(String[] args) {
        System.out.println();
        System.out.println("Welcome to the Plane Management application!");

        initializeSeats(seatCountPerRow);   // Task 01: Initialize all seats as available.

        //Create a Scanner object to read user input during the execution of the program.
        Scanner scanner = new Scanner(System.in);
        // Initializing a variable to save the user input.
        int option;

        // Task 02: Display menu options, Prompt user input, Error handling.
        do {
            //Display menu options.
            System.out.println();
            System.out.println("**************************************************");
            System.out.println("*                MENU OPTIONS                    *");
            System.out.println("**************************************************");
            System.out.println("    1) Buy a seat");
            System.out.println("    2) Cancel a seat");
            System.out.println("    3) Find first available seat");
            System.out.println("    4) Show seating plan");
            System.out.println("    5) Print tickets information and total sales");
            System.out.println("    6) Search ticket");
            System.out.println("    0) Quit");
            System.out.println("**************************************************");
            System.out.println();
            System.out.print("Please select an option (0-6): ");

            // Error handling and prompt user input.
            try {
                option = scanner.nextInt();    //Prompt for user input.
                // Check the user input with the below conditions to access the respective methods created below.
                if (option < 0 || option > 6) {
                    System.out.print("Invalid input! Please enter a number between 0 and 6.");
                    System.out.println();
                } else {
                    System.out.println();
                    switch (option) {
                        case 1:
                            buy_seat(scanner);         //Task 03 and Task 09.
                            break;
                        case 2:
                            cancel_seat(scanner);      //Task 04 and Task 09.
                            break;
                        case 3:
                            first_find_available();    //Task 05.
                            break;
                        case 4:
                            show_seating_plan();       //Task 06.
                            break;
                        case 5:
                            print_tickets_info();      //Task 10.
                            break;
                        case 6:
                            search_ticket(scanner);    //Task 11.
                            break;
                        case 0:
                            System.out.println("Thank you for using the Plane Management System!");   //Quit the program.
                            break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a numerical input.");
                scanner.next();   // Clear the input buffer.
                option = -1;    // Set option to invalid value to trigger loop iteration.
            }
        } while (option != 0);     // This loop iterates until the user input 0.
    }



    // Task 01: Method to initialize seats.
    /*
        * This method creates rows and columns needed based on static information.
        * Input parameters: 'seatCountPerRow' is an array created above.
        It indicates the number of seats allocated for each row.
        * At the beginning, marks all the seats as available using '0'.
     */
    private static void initializeSeats(int[] seatCountPerRow) {
        SEATS = new int[seatCountPerRow.length][];  // Initialize the outer array with the number of rows.
        for (int i = 0; i < seatCountPerRow.length; i++) {
            SEATS[i] = new int[seatCountPerRow[i]];  // Initialize each row with its respective seat count.
            for (int j = 0; j < seatCountPerRow[i]; j++) {
                SEATS[i][j] = 0;    // Marks all seats as available.
            }
        }
    }

    // Task 03: A method called 'buy_seat' used to reserve a seat.
    /*
        * Variable 'no_of_passengers' prompt user input to specify the number of tickets to be booked at once.
        * while loop iterates until the seats booked successfully as per user mentioned no_of_passengers.
        * Unsuccessful booking leads to exit the loop and user need to re-run the program to book further.
        * Prompt user input for passenger details for booking.
        * Stores Person and Ticket information to the text files and arrays.
     */
    private static void buy_seat(Scanner scanner) {
        int no_of_passengers = 0;   // Declaration and Initialization of a variable to store the input temporarily.

        boolean correct = false;
        // Utilising a while loop to manage error handling part.
        while (!correct) {
            try {
                System.out.print("Please specify the number of passengers for the seat reservation: ");
                no_of_passengers = scanner.nextInt();
                System.out.println();
                correct = true;
            } catch (InputMismatchException e) {
                System.out.println("Number of passengers must be a number (Do not enter the number in words).");   // Handling data type error.
                scanner.next();
            }
        }


        int x = 0;  // Declare and Initialize the variable to control the loop iteration.
        // This while loop iterates until it reaches the number of passengers specified by the user.
        while (no_of_passengers > x) {
            System.out.print("Enter a row letter (A-D): ");
            char row = scanner.next().toUpperCase().charAt(0);
            /*  * .toUpperCase() : Used to capitalize the user input to avoid the errors.
                * .charAt(0) : Used to covert the user input to 0-based index.  */
            int rowIndex = row - 'A';   // Convert letter to row index (A=0, B=1, C=2, D=3)

            // Check whether the user input is within the specified range. If not it will run the program from the beginning.
            if (rowIndex < 0 || rowIndex >= ROWS) {
                System.out.println("Invalid row! Please enter a row from A to D.");
                return;
            }

            System.out.print("Enter a seat number between 1 to " + seatCountPerRow[rowIndex] + ": ");
            int seat = scanner.nextInt() - 1;   // Adjust user input to 0-based index.

            // Check if seatNumber is within the bound for the selected row.
            if (seat >= 0 && seat < SEATS[rowIndex].length) {
                if (SEATS[rowIndex][seat] == 0) {
                    SEATS[rowIndex][seat] = 1;  // Mark the seat as sold.

                    System.out.println("\nPassenger " + (x + 1) + ":");   // Passenger number for reference.

                    /*
                           * Prompt the input for passenger details.
                           * While loop used to manage error handling.
                           * The firstname and the lastname should be in string type.
                           * e-mail should contain '@' and '.' symbols.
                     */
                    System.out.print("Enter passenger's first name: ");
                    String name = scanner.next();
                    while (!(name.matches("[a-zA-Z]+"))) {
                        System.out.println("Invalid first name! Please use only letters (a-z,A-Z).");
                        System.out.print("Enter passenger's first name: ");
                        name = scanner.next();
                    }

                    System.out.print("Enter passenger's surname: ");
                    String surname = scanner.next();
                    while (!(name.matches("[a-zA-Z]+"))) {
                        System.out.println("Invalid surname! Please use only letters (a-z,A-Z).");
                        System.out.print("Enter passenger's surname: ");
                        surname = scanner.next();
                    }

                    System.out.print("Enter passenger's email: ");
                    String email = scanner.next();
                    while (!(email.contains("@") && email.contains("."))) {
                        System.out.println("Invalid email! Please enter a valid email address.");
                        System.out.print("Enter passenger's email: ");
                        email = scanner.next();
                    }

                    // Declare the prices of seats under three categories.
                    double ticketPrice1 = 200;
                    double ticketPrice2 = 150;
                    double ticketPrice3 = 180;
                    // Condition checks the position of the seat and prints the applicable price of the selected seat.
                    if (seat <= 4) {
                        System.out.println("Price of the air ticket is £" + ticketPrice1);
                    } else if (seat >= 5 && seat <= 8) {
                        System.out.println("Price of the air ticket is £" + ticketPrice2);
                    } else if (seat >= 9 && seat <= 13) {
                        System.out.println("Price of the air ticket is £" + ticketPrice3);
                    } else {
                        System.out.println("Invalid seat number!");
                    }

                    // Create Person object.
                    Person person = new Person(name, surname, email);

                    person.setName(name);
                    person.setSurname(surname);
                    person.setEmail(email);

                    // Create Ticket object.
                    Ticket ticket = new Ticket(row, seat, person);

                    ticket.setSeat(seat+1); //Add 1, since it is 0-based index (Applicable only for filename).
                    ticket.setRow(row);
                    ticket.setPrice(ticketPrice1,ticketPrice2,ticketPrice3);

                    // Save sold ticket information to file.
                    ticket.save();

                    // Task 09: Add sold ticket and their passenger information to the array to track & add the count of sold tickets.
                    soldTickets[soldTicketCount++] = ticket;

                    System.out.println("\nSeat " + row + (seat + 1) + " has been reserved successfully!\n\n");
                } else {
                    System.out.println("Sorry, this seat is already occupied.\n");
                }
            } else {
                System.out.println("Invalid seat number for the selected row. Enter a seat number between 1 to " + seatCountPerRow[rowIndex] + ".");
            }
            x++;
        }
    }


    // Task 04: Method called 'cancel_seat' used to cancel the reserved seat.
    private static void cancel_seat (Scanner scanner) {
        // Declare and initialize the variables.
        char row = 0;
        int rowIndex = 0;
        int seat = 0;

        boolean correct = false;

        while (!correct) {
            try {
                System.out.print("Enter a row letter (A-D): ");
                row = scanner.next().toUpperCase().charAt(0);
                rowIndex = row - 'A';   // Convert letter to row index (A=0, B=1, C=2, D=3)

                if (rowIndex < 0 || rowIndex >= ROWS) {
                    System.out.println("Invalid row! Enter row between A to D");
                } else {
                    correct = true;
                    // If the user input satisfies all the expected conditions, then the boolean value will be converted as 'true' and terminates the loop.
                }
            } catch (InputMismatchException e) {    // Handles input type error.
                scanner.next();
            }
        }

        correct = false;
        while (!correct) {
            try {
                System.out.print("Enter a seat number between 1 to " + seatCountPerRow[rowIndex] + ": ");
                seat = scanner.nextInt() - 1;   // Adjust user input to 0-based index.
                // Check whether seatNumber is within the bound for the selected row.
                if (seat >= 0 && seat < SEATS[rowIndex].length) {
                    correct = true;
                } else {
                    System.out.println("Invalid seat number!");    // Handles out of range error.
                }
            } catch (InputMismatchException e) {    // Handles input type error.
                System.out.println("Invalid input! Please provide an integer value.");
                scanner.next();
            }
        }


        /*
            If the user mentioned seat is already reserved, then it runs further as;
            * Prompt the user to enter the e-mail address which is provided, during they're booking seat (for the verification purpose).
            * Verified seat will be cancelled and remove the ticket and passenger details from the array.
         */
        if (SEATS[rowIndex][seat] == 1) {
            for (int j = 0; j < soldTicketCount; j++) {
                if ((soldTickets[j].getRow() == row) && (soldTickets[j].getSeat() == seat + 1)) {
                    System.out.print("Please enter the email address you provided during reservation: ");
                    String email = scanner.next();
                    if (email.equals(soldTickets[j].getPerson().getEmail())) {
                        SEATS[rowIndex][seat] = 0;  // Mark the seat as available.


                        // Task 09: Remove passenger and ticket information from soldTickets array.
                        for (int x = 0; x < soldTicketCount; x++) {
                            if ((soldTickets[x].getRow() == row) && (soldTickets[x].getSeat() == seat + 1)) {
                                for (int y = x; y < soldTicketCount - 1; y++) {
                                    soldTickets[y] = soldTickets[y + 1];

                                }

                                soldTickets[soldTicketCount - 1] = null; //Clear the last element.
                                soldTicketCount--;
                                System.out.println("Seat " + row + (seat + 1) + " has been cancelled successfully!");
                                break;
                            }
                        }
                    } else {
                        System.out.println("Enter a valid email address.");
                        return;
                    }
                }
            }
        } else {
            System.out.println("This seat is available.");
        }
    }


    // Task 05: Method used to find the first available seat.
    private static void first_find_available() {
        char row = 'A';
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < SEATS[i].length; j++) {
                if (SEATS[i][j] == 0) {
                    System.out.println("First available seat: Row " + row + ", Seat " + (j + 1));
                    return;
                }
            }
            row++;
        }
        System.out.println("Sorry, All the seats has been taken.");
    }

    // Task 06: Method used to show seating plan. This shows the booked and available seats.
    private static void show_seating_plan() {
        System.out.println("Seating plan:");

        int maxSeatsInRow = 0;
        for (int[] row : SEATS) {
            if (row.length > maxSeatsInRow) {
                maxSeatsInRow = row.length;
            }
        }

        System.out.println();
        System.out.println("  1  2  3  4  5  6  7  8  9  10 11 12 13 14 ");

        // Display seat availability.
        for (int i = 0; i < ROWS; i++) {
            System.out.print((char) ('A' + i) + " ");   // Row label.
            for (int j = 0; j < SEATS[i].length; j++) {
                System.out.print(SEATS[i][j] == 1 ? "X  " : "O  ");  // Mark sold seats with X and available ones with O.
            }
            System.out.println();   // Create each row in a new line.
        }
    }

    // Task 10: Method to print ticket information and total sales.
    private static void print_tickets_info() {
        // Declaration and initializing the variable to get the sum of total sales.
        double totalSalesAmount = 0;
        // Declaration of variable to get the price of each seat.
        double seatAmount;

        for (int i = 0; i < soldTicketCount; i++) {
            System.out.println(soldTickets[i].print_tickets_info());    // Print ticket information.
            seatAmount = soldTickets[i].getPrice();     // Retrieve the price of each reserved seat.
            totalSalesAmount += seatAmount;     // Calculation of sum of total sales.
        }
        System.out.println("\nTotal sales: £" + totalSalesAmount);
    }

    //Task 11: Method for search whether a particular seat is booked or not.
    private static void search_ticket(Scanner scanner) {
        char row = 0;
        int rowIndex = 0;
        int seat = 0;

        boolean correct = false;
        while (!correct) {
            try {
                System.out.print("Enter a row letter (A-D): ");
                row = scanner.next().toUpperCase().charAt(0);
                rowIndex = row - 'A';   // Convert letter to row index (A=0, B=1, C=2, D=3)

                if (rowIndex < 0 || rowIndex >= ROWS) {
                    System.out.println("Invalid row! Enter row between A to D.");
                } else {
                    correct = true;
                }
            } catch (InputMismatchException e) {    // Handling input type error.
                scanner.next();
            }

        }

        correct = false;
        while (!correct) {
            try {
                System.out.print("Enter a seat number between 1 to " + seatCountPerRow[rowIndex] + ": ");
                seat = scanner.nextInt() - 1;   // Adjust user input to 0-based index.
                correct = true;
            } catch (InputMismatchException e) {    // Handling input type error.
                System.out.println("Invalid input! Please provide an integer value.");
                scanner.next();
            }
        }

        if (SEATS[rowIndex][seat] == 1) {
            for (int i = 0; i < soldTicketCount; i++) {
                if (soldTickets[i].getRow() == row && soldTickets[i].getSeat() == seat + 1) {
                    System.out.println("\n" + soldTickets[i].print_tickets_info());
                }
            }
        } else {
            System.out.println("This seat is available.");
        }
    }
}

// ---------- END OF THE PROGRAM.-------------



/* REFERENCE LIST:
01.	Data Types and Control Structures (Variable declaration and initialization, Type casting, Logical operators, Looping(Switch statement, Do while loop), Conditional statements, Comparing strings using ‘.equals()’, Ternary operator (?:)).

•	University of Westminster.(2023). Lecture slides for Week 02: Data Types and Control Structure. 4COSC010C.2: Software Development II. Available at: https://learning.westminster.ac.uk/ultra/courses/_95464_1/outline/edit/document/_4589316_1?courseId=_95464_1&view=content. [Accessed: March 15, 2024].

•	www.oreilly.com. (n.d.). CHAPTER 2: FUNDAMENTAL DATA TYPES - Java For Everyone: Compatible with Java 5, 6, and 7, 2nd Edition [Book]. [online] Available at: https://learning.oreilly.com/library/view/java-for-everyone/9781118063316/07_chapter-02.html. [Accessed: March 18, 2024].

•	www.oreilly.com. (n.d.). CHAPTER 3: DECISIONS - Java For Everyone: Compatible with Java 5, 6, and 7, 2nd Edition [Book]. [online] Available at: https://learning.oreilly.com/library/view/java-for-everyone/9781118063316/08_chapter-03.html [Accessed: March 18, 2024].
•	www.oreilly.com. (n.d.). CHAPTER 4: LOOPS - Java For Everyone: Compatible with Java 5, 6, and 7, 2nd Edition [Book]. [online] Available at: https://learning.oreilly.com/library/view/java-for-everyone/9781118063316/09_chapter-04.html [Accessed: March 18, 2024].

02.	Looping and Debugging (Data Input Validation and Exception Handling).
•	University of Westminster.(2023). Lecture slides for Week 03: Looping and Debugging. 4COSC010C.2: Software Development II. Available at: https://learning.westminster.ac.uk/ultra/courses/_95464_1/outline/file/_4589322_1.   [Accessed: March 15, 2024].
•	www.oreilly.com. (n.d.). CHAPTER 7: INPUT/OUTPUT AND EXCEPTION HANDLING - Java For Everyone: Compatible with Java 5, 6, and 7, 2nd Edition [Book]. [online] Available at: https://learning.oreilly.com/library/view/java-for-everyone/9781118063316/12_chapter-07.html#ch007-sec002.  [Accessed: March 20, 2024].

03.	 Arrays, Sort and Search (Two-dimensional arrays, Search).
•	University of Westminster.(2023). Lecture slides for Week 04:  Arrays, Sort and Search. 4COSC010C.2: Software Development II. Available at: https://learning.westminster.ac.uk/ultra/courses/_95464_1/outline/file/_4589328_1.    [Accessed: March 15, 2024].
•	www.oreilly.com. (n.d.). CHAPTER 6: ARRAYS AND ARRAY LISTS - Java For Everyone: Compatible with Java 5, 6, and 7, 2nd Edition [Book]. [online] Available at: https://learning.oreilly.com/library/view/java-for-everyone/9781118063316/11_chapter-06.html.  [Accessed: March 20, 2024].

04.	 Methods and Recursion (Methods).
•	University of Westminster.(2023). Lecture slides for Week 5: Methods and Recursion. 4COSC010C.2: Software Development II. Available at: https://learning.westminster.ac.uk/ultra/courses/_95464_1/outline/file/_4589335_1.     [Accessed: March 15, 2024].
•	www.oreilly.com. (n.d.). CHAPTER 5: METHODS - Java For Everyone: Compatible with Java 5, 6, and 7, 2nd Edition [Book]. [online] Available at: https://learning.oreilly.com/library/view/java-for-everyone/9781118063316/10_chapter-05.html.  [Accessed: March 18, 2024]

05.	 Objects and Classes (Objects, Getters and Setters, Constructor, Access Modifiers, Class).
•	University of Westminster.(2023). Lecture slides for Week 07: Objects and Classes. 4COSC010C.2: Software Development II. Available at: https://learning.westminster.ac.uk/ultra/courses/_95464_1/outline/file/_4589347_1.      [Accessed: March 15, 2024].

•	www.oreilly.com. (n.d.). CHAPTER 8: OBJECTS AND CLASSES - Java For Everyone: Compatible with Java 5, 6, and 7, 2nd Edition [Book]. [online] Available at: https://learning.oreilly.com/library/view/java-for-everyone/9781118063316/13_chapter-08..  [Accessed: March 18, 2024].

06.	 Files and Testing (Class File, Create a file, Write to a file, Delete a file, File Exceptions, Black box testing).
•	University of Westminster.(2023). Lecture slides for Week 08: Files and Testing. 4COSC010C.2: Software Development II. Available at: https://learning.westminster.ac.uk/ultra/courses/_95464_1/outline/file/_4589352_1.       [Accessed: March 20, 2024].

•	www.oreilly.com. (n.d.). CHAPTER 7: INPUT/OUTPUT AND EXCEPTION HANDLING - Java For Everyone: Compatible with Java 5, 6, and 7, 2nd Edition [Book]. [online] Available at: https://learning.oreilly.com/library/view/java-for-everyone/9781118063316/12_chapter-07.html.  [Accessed: March 22, 2024].
 */

