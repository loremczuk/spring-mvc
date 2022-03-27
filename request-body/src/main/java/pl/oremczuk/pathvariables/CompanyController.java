package pl.oremczuk.pathvariables;


import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("companies")
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

    @GetMapping("/{company}")
    Company findOne (@PathVariable("company") String companyName) {

        return companyRepository.findOne(companyName);

    }

    @GetMapping("{companyName}/employees/{employeeName:[a-zA-Z]+}")
    List <Employee> getCompanyEmployeeByFirstName (@PathVariable String companyName,
                                                   @PathVariable String employeeName) {
        return findOne(companyName)
                .getEmployees()
                .stream()
                .filter(employee -> Objects.equals(employee.getFirstName(), employeeName))
                .collect(Collectors.toList());
    }

    @GetMapping("{companyName}/employees/{employeeId:\\d+}")
    Employee getEmployeeById (@PathVariable String companyName,
                              @PathVariable long employeeId) {

        return findOne(companyName)
                .getEmployees()
                .stream()
                .filter(employee -> employee.getId() == employeeId)
                .findAny()
                .orElse(null);

    }


    @GetMapping("{companyName}/employees/{firstName}/{lastName}")
    List<Employee> getCompanyEmployeeByFirstAndLastName (@PathVariable String companyName,
                                                         @PathVariable Map<String, String> firstAndLastNameMap) {

        return findOne(companyName)
                .getEmployees()
                .stream()
                .filter(employee -> Objects.equals(employee.getFirstName(), firstAndLastNameMap.get("firstName")))
                .filter(employee -> Objects.equals(employee.getLastName(), firstAndLastNameMap.get("lastName")))
                .collect(Collectors.toList());

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
