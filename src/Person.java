// Task 07: Created a class file called Person.
class Person {
    private String name;
    private String surname;
    private String email;

    // Task 07: Added a constructor.
    public Person (String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    // Task 07: Added getters and setters of the class Person.
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Task 07: A method to print passenger's personal information.
    public void printPersonInfo() {
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("Email: " + email);
    }
}
