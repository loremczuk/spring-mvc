package pl.oremczuk.pathvariables;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping(value = "companies")
@RestController
public class CompanyController {

    private final CompanyRepository companyRepository;

//    @Autowired
    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping
    List<Company> getCompanies () {
        return companyRepository.findAll();
    }

    @GetMapping(value = "/{company}", produces = MediaType.APPLICATION_JSON_VALUE)
    HttpEntity<Company> findOne (@PathVariable("company") String companyName) {


        Company company = companyRepository.findOne(companyName);

        if (company != null) {
            return ResponseEntity.ok(company);
       } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

    @PostMapping("{companyName}/employees")
    Employee addNewEmployee (@PathVariable String companyName,
                             @RequestParam /*("firstName")*/ String firstName,
                             @RequestParam /*(required = false)*/ String lastName,
                             /*@RequestParam*/ BigDecimal salary) {

        Company originalCompany = companyRepository.findOne(companyName);
        List<Employee> employeeList = new ArrayList<>(originalCompany.getEmployees());

        Employee employee = new Employee(Employee.getNextEmployeeId(), firstName, lastName, salary);
        employeeList.add(employee);

        Company newCompany = new Company(originalCompany.getName(), employeeList);
        companyRepository.save(newCompany);

        return employee;

    }

    @PostMapping("{companyName}/employees/create")
    List<Employee> addManyEmployees (@PathVariable String companyName,
                                     @RequestBody AddEmployeesRequest request) {

        Company originalCompany = companyRepository.findOne(companyName);
        List<Employee> employeeList = new ArrayList<>(originalCompany.getEmployees());
        List<Employee> newEmployeeList = createEmployees(request.getEmployees());
        employeeList.addAll(newEmployeeList);

        Company newCompany = new Company (originalCompany.getName(), employeeList);
        companyRepository.save(newCompany);

        return newEmployeeList;


    }

    private List<Employee> createEmployees(List<Employee> employees) {

        return employees
                .stream()
                .map(employee -> new Employee(Employee.getNextEmployeeId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary()))
                .collect(Collectors.toList());
    }

}
