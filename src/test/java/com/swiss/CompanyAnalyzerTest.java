package com.swiss;

import com.swiss.repository.EmployeeRepository;
import com.swiss.service.AnalysisStrategy;
import com.swiss.service.ReportingLineAnalysisStrategy;
import com.swiss.service.SalaryAnalysisStrategy;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CompanyAnalyzerTest {

    @Test
    void testAnalyze() throws IOException {
        // Mock Repository
        EmployeeRepository mockRepo = Mockito.mock(EmployeeRepository.class);
        
        List<Employee> mockEmployees = Arrays.asList(
            new Employee(123, "Joe", "Doe", 60000, null),
            new Employee(124, "Martin", "Chekov", 45000, 123),
            new Employee(125, "Bob", "Ronstad", 47000, 123),
            new Employee(300, "Alice", "Hasacat", 50000, 124),
            new Employee(305, "Brett", "Hardleaf", 34000, 300)
        );
        
        when(mockRepo.findAll(Mockito.anyString())).thenReturn(mockEmployees);

        List<AnalysisStrategy> strategies = Arrays.asList(
                new SalaryAnalysisStrategy(),
                new ReportingLineAnalysisStrategy()
        );

        CompanyAnalyzer analyzer = new CompanyAnalyzer(mockRepo, strategies);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        analyzer.run("dummy/path.csv");

        assertEquals(5, analyzer.getEmployees().size());

        String output = outContent.toString();
        
        assertTrue(output.contains("Manager Employee(id=124, firstName=Martin, lastName=Chekov, salary=45000.0, managerId=123) earns 15000.00 less than they should."));
        assertFalse(output.contains("reporting line which is too long"));
    }
}
