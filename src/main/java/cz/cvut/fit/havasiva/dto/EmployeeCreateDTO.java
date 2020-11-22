package cz.cvut.fit.havasiva.dto;


import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeCreateDTO that = (EmployeeCreateDTO) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(mail, that.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, mail);
    }
}
