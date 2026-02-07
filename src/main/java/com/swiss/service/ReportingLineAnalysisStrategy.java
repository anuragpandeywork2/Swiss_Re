package com.swiss.service;

import com.swiss.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ReportingLineAnalysisStrategy implements AnalysisStrategy {

    @Override
    public void analyze(Map<Integer, Employee> employees, Map<Integer, List<Employee>> subordinates) {
        for (Employee employee : employees.values()) {
            int depth = calculateDepth(employee, employees);
            if (depth > 5) {
                System.out.printf("Employee %s has a reporting line which is too long",
                        employee, depth - 5);
            }
        }
    }

    private int calculateDepth(Employee employee, Map<Integer, Employee> employees) {
        int depth = 0;
        Integer currentManagerId = employee.getManagerId();
        while (currentManagerId != null) {
            depth++;
            Employee manager = employees.get(currentManagerId);
            if (manager == null) break;
            currentManagerId = manager.getManagerId();
        }
        return depth;
    }
}
