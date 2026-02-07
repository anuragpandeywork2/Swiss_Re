package com.swiss.service;

import com.swiss.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SalaryAnalysisStrategy implements AnalysisStrategy {

    @Override
    public void analyze(Map<Integer, Employee> employees, Map<Integer, List<Employee>> subordinates) {
        for (Map.Entry<Integer, List<Employee>> entry : subordinates.entrySet()) {
            int managerId = entry.getKey();
            List<Employee> directSubordinates = entry.getValue();
            Employee manager = employees.get(managerId);

            if (manager == null) continue;

            double totalSalary = 0;
            for (Employee sub : directSubordinates) {
                totalSalary += sub.getSalary();
            }
            double avgSalary = totalSalary / directSubordinates.size();

            double minSalary = avgSalary * 1.20;
            double maxSalary = avgSalary * 1.50;

            if (manager.getSalary() < minSalary) {
                System.out.printf("Manager %s earns %.2f less than they should.%n",
                        manager, minSalary - manager.getSalary());
            } else if (manager.getSalary() > maxSalary) {
                System.out.printf("Manager %s earns %.2f more than they should.%n",
                        manager, manager.getSalary() - maxSalary);
            }
        }
    }
}
