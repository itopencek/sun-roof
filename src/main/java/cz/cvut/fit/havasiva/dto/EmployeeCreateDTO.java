package cz.cvut.fit.havasiva.dto;


public class EmployeeCreateDTO {

    private final String firstName;
    private final String lastName;
    private final String mail;

    public EmployeeCreateDTO(String firstName, String lastName, String mail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
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
