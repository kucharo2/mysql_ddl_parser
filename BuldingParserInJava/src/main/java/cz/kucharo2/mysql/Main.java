package cz.kucharo2.mysql;

import cz.kucharo2.mysql.parser.AlterTableParser;

import java.util.Scanner;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int benchmarkIteration = 10;
        String[] queries = {
                "query",
                "incorrect query",
                "alter table query",
                "alter table person drop hand",
                "alter table person drop column hand",
                "alter table person please drop column hand",
                "alter table person add column brain text",
                "alter table person add brain word",
                "alter table person add column brain integer null",
                "alter table person add brain integer not null",
                "alter table person change column hair dreadlocks text not null",
                "alter table person change column hair to dreadlocks text not null",
        };

        for (int i = 0; i < benchmarkIteration; i++) {
            System.out.println("\n--------------- Iteration " + i + " ---------------");
            for (String query : queries) {
                System.out.println("Parsing: " + query);
                long start = System.nanoTime();
                new AlterTableParser().parse(query);
                long end = System.nanoTime();
                System.out.println("Computation time: " + (end - start) + "ns\n");
            }
        }

//
//        String text = getNewQuery();
//        while (!text.equals("exit")) {
//            new AlterTableParser().parse(text);
//            text = getNewQuery();
//        }
    }

    private static String getNewQuery() {
        System.out.println("\nEnter a query to parse:");
        return scanner.nextLine();
    }
}
