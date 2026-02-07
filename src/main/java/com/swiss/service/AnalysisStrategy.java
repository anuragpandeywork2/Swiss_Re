package com.swiss.service;

import com.swiss.Employee;
import java.util.List;
import java.util.Map;

public interface AnalysisStrategy {
    void analyze(Map<Integer, Employee> employees, Map<Integer, List<Employee>> subordinates);
}
