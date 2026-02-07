package com.swiss;

import com.swiss.repository.EmployeeRepository;
import com.swiss.service.AnalysisStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class CompanyAnalyzer {

    private final EmployeeRepository employeeRepository;
    private final List<AnalysisStrategy> analysisStrategies;

    private Map<Integer, Employee> employees = new HashMap<>();
    private Map<Integer, List<Employee>> subordinates = new HashMap<>();

    @Autowired
    public CompanyAnalyzer(EmployeeRepository employeeRepository, List<AnalysisStrategy> analysisStrategies) {
        this.employeeRepository = employeeRepository;
        this.analysisStrategies = analysisStrategies;
    }

    public void run(String csvFile) throws IOException {
        loadEmployees(csvFile);
        for (AnalysisStrategy strategy : analysisStrategies) {
            strategy.analyze(employees, subordinates);
        }
    }

    private void loadEmployees(String csvFile) throws IOException {
        List<Employee> employeeList = employeeRepository.findAll(csvFile);

        for (Employee employee : employeeList) {
            employees.put(employee.getId(), employee);
            if (employee.getManagerId() != null) {
                subordinates.computeIfAbsent(employee.getManagerId(), k -> new ArrayList<>()).add(employee);
            }
        }
    }
    
    // For testing
    public Map<Integer, Employee> getEmployees() {
        return employees;
    }
}
