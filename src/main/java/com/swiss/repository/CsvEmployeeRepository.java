package com.swiss.repository;

import com.opencsv.bean.CsvToBeanBuilder;
import com.swiss.Employee;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Repository
public class CsvEmployeeRepository implements EmployeeRepository {

    @Override
    public List<Employee> findAll(String source) throws IOException {
        return new CsvToBeanBuilder<Employee>(new FileReader(source))
                .withType(Employee.class)
                .build()
                .parse();
    }
}
