package com.company;

import com.company.model.Exercise;
import com.company.model.Solution;
import com.company.service.DbService;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner sc;

    public static void main(String[] args) {
        try {
            int userId = Integer.parseInt(args[0]);
            sc = new Scanner(System.in);
            boolean run = true;
            while (run) {
//                Exercise.loadAll().forEach(System.out::println);
                System.out.println("Wybierz jedną z opcji:");
                System.out.println("    add – dodawanie rozwiązania,");
                System.out.println("    view – przeglądanie swoich rozwiązań.");

                switch (sc.nextLine().toLowerCase()) {
                    case "add":
                        add(userId);
                        break;
                    case "view":
                        view(userId);
                        break;
                    case "quit":
                        run = false;
                        break;
                    default:
                        System.out.println("Unknown command");
                }
            }
            sc.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private static void add(int userId) {
        Exercise.loadAllUnsolvedByUserId(userId).forEach(System.out::println);
        int exercizeId = getPosInt("exercize id");
        List<String> param = new ArrayList<>();
        param.add(String.valueOf(userId));
        param.add(String.valueOf(exercizeId));
        //language=MySQL
        String query = "SELECT id\n" +
                "FROM solutions\n" +
                "WHERE user_id = ?\n" +
                "  AND exercise_id = ?";
        try {
            if (DbService.getData(query, param).isEmpty()) {
                String description = getString("description");
                Solution solution = new Solution(new Timestamp(new Date().getTime()).toString(), "", description, exercizeId, userId);
                solution.save();
            } else {
                System.out.println("Solution already exists");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void view(int userId) {
        Solution.loadAllByUserId(userId).forEach(System.out::println);
    }

    private static String getString(String prompt) {
        System.out.println(prompt);
        return sc.nextLine();
    }

    private static int getPosInt(String prompt) {
        System.out.println(prompt);
        String str;
        while (!(str = sc.nextLine()).matches("[1-9]\\d*")) {
            System.out.println("Input error");
        }
        return Integer.parseInt(str);
    }
}
