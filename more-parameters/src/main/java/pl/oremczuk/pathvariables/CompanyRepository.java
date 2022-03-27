package pl.oremczuk.pathvariables;

import java.util.List;

interface CompanyRepository {
    List<Company> findAll();
    Company findOne(String name);
    Company save(Company company);
}
