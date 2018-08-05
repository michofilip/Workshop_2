package com.company.admin;

import com.company.model.User;

import java.util.Scanner;

public class UserControl {
    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        boolean run = true;
        while (run) {
            User.loadAll().forEach(System.out::println);
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
        String username = getString("Name and surname");
        String password = getString("Password");
        String email = getString("email");
        int group_id = getPosInt("group id");
        User user = new User(username, password, email, group_id);
        user.save();
    }

    private static void edit() {
        User user = User.loadById(getPosInt("id?"));
        if (user != null) {
            user.setUsername(getString("Name and surname"));
            user.setPassword(getString("Password"));
            user.setEmail(getString("email"));
            user.setGroup(getPosInt("group id"));
            user.save();
        } else {
            System.out.println("No such user");
        }
    }

    private static void delete() {
        User user = User.loadById(getPosInt("id?"));
        if (user != null) {
            user.delete();
        } else {
            System.out.println("No such user");
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
