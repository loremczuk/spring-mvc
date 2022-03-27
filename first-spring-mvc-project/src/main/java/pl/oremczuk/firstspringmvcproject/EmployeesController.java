package pl.oremczuk.firstspringmvcproject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
//@Controller
//@ResponseBody
@RequestMapping("employees")
public class EmployeesController {

    @RequestMapping("getEmployees")
    public List<Employee> getEmployees() {
//    public @ResponseBody List<Employee> getEmployees() {

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("Jaromir", "Fabian", new BigDecimal(50000.00)));
        employeeList.add(new Employee("Marek", "Motyka", new BigDecimal(10000.00)));
        employeeList.add(new Employee("Antoni", "Buc", new BigDecimal(20000.00)));

        return employeeList;

    }

//    @RequestMapping(value = "getOneEmployee")
//    @RequestMapping (value = "getOneEmployee", method = RequestMethod.GET)
//    @GetMapping("/getOneEmployee")
//    @RequestMapping(value = "getOneEmployee", method = {RequestMethod.GET, RequestMethod.POST})
    @GetMapping("getOneEmployee")
    public Employee getOneEmployee() {
        return new Employee("Jaromir", "Fabian", new BigDecimal(50000.00));
    }


}
