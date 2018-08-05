package com.company.admin;

import com.company.model.Exercise;
import com.company.model.Solution;
import com.company.model.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SolutionControl {
    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        boolean run = true;
        while (run) {
            Solution.loadAll().forEach(System.out::println);
            System.out.println("Command");
            switch (sc.nextLine().toLowerCase()) {
                case "add":
                    add();
                    break;
                case "view":
                    view();
                    break;
                case "quit":
                    run = false;
                    break;
                default:
                    System.out.println("Unknown command");
            }
        }
        sc.close();
    }

    private static void add() {
        User.loadAll().forEach(System.out::println);
        int user_id = getPosInt("user_id");
        Exercise.loadAll().forEach(System.out::println);
        int exercise_id = getPosInt("exercise_id");

        String created = new Timestamp(new Date().getTime()).toString();
        Solution solution = new Solution(created, "", "", exercise_id, user_id);
        solution.save();
    }

    private static void view() {
        List<Solution> solutions = Solution.loadAllByUserId(getPosInt("user id"));
        solutions.forEach(System.out::println);
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
