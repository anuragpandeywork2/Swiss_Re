package com.swiss.repository;

import com.swiss.Employee;
import java.io.IOException;
import java.util.List;

public interface EmployeeRepository {
    List<Employee> findAll(String source) throws IOException;
}
