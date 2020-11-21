package cz.cvut.fit.havasiva.dto;


public class EmployeeDTO {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String mail;

    public EmployeeDTO(int id, String firstName, String lastName, String mail) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }
}
