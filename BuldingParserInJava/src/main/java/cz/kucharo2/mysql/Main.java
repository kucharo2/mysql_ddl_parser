package cz.kucharo2.mysql;

import cz.kucharo2.mysql.parser.AlterTableParser;

import java.util.Scanner;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);;

    public static void main(String[] args) {
        String text = getNewQuery();
        while(!text.equals("exit")) {
            new AlterTableParser().parse(text);
            text = getNewQuery();
        }
    }

    private static String getNewQuery() {
        System.out.println("\nEnter a query to parse:");
        return scanner.nextLine();
    }
}
