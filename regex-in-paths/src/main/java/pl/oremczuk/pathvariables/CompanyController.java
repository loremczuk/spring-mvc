package pl.oremczuk.pathvariables;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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

}
