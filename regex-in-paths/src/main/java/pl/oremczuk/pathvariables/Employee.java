package pl.oremczuk.pathvariables;

import java.math.BigDecimal;

class Employee {

    private Long Id;
    private String firstName;
    private String lastName;
    private BigDecimal salary;

    Employee(Long Id,String firstName, String lastName, BigDecimal salary) {
        this.Id = Id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public Long getId() {
        return Id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

}