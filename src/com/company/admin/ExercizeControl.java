package com.company.admin;

import com.company.model.Exercise;
import com.company.model.User;

import java.util.Scanner;

public class ExercizeControl {
    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        boolean run = true;
        while (run) {
            Exercise.loadAll().forEach(System.out::println);
            System.out.println("Command");
            switch (sc.nextLine().toLowerCase()) {
                case "add":
                    add();
                    break;
                case "edit":
                    edit();
                    break;
                case "delete":
                    delete();
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
        String title = getString("title");
        String description = getString("description");
        Exercise exercise = new Exercise(title, description);
        exercise.save();
    }

    private static void edit() {
        Exercise exercise = Exercise.loadById(getPosInt("id?"));
        if (exercise != null) {
            exercise.setTitle(getString("Name and surname"));
            exercise.setDescription(getString("Password"));
            exercise.save();
        } else {
            System.out.println("No such exercise");
        }
    }

    private static void delete() {
        Exercise exercise = Exercise.loadById(getPosInt("id?"));
        if (exercise != null) {
            exercise.delete();
        } else {
            System.out.println("No such exercise");
        }
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
