package com.swiss;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Invalid path or no csv file provided");
            return;
        }

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CompanyAnalyzer analyzer = context.getBean(CompanyAnalyzer.class);

        try {
            analyzer.run(args[0]);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
