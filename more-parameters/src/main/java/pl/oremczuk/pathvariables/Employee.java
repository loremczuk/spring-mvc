package pl.oremczuk.pathvariables;

import java.math.BigDecimal;

class Employee {

    private Long Id;
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private static long lastEmployeeId = 0;

//    public Employee() {
//    }

    Employee(Long Id, String firstName, String lastName, BigDecimal salary) {
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

//    public void setId(Long id) {
//        Id = id;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public void setSalary(BigDecimal salary) {
//        this.salary = salary;
//    }
//
//    public static void setLastEmployeeId(long lastEmployeeId) {
//        Employee.lastEmployeeId = lastEmployeeId;
//    }

    static long getNextEmployeeId() {
        return lastEmployeeId++;
    }

}