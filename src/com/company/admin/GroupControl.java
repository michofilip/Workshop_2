package com.company.admin;

import com.company.model.Exercise;
import com.company.model.Group;
import com.company.model.User;

import java.util.Scanner;

public class GroupControl {
    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        boolean run = true;
        while (run) {
            Group.loadAll().forEach(System.out::println);
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
        String name = getString("name");
        Group group = new Group(name);
        group.save();
    }

    private static void edit() {
        Group group = Group.loadById(getPosInt("id?"));
        if (group != null) {
            group.setName(getString("name"));
            group.save();
        } else {
            System.out.println("No such group");
        }
    }

    private static void delete() {
        Group group = Group.loadById(getPosInt("id?"));
        if (group != null) {
            group.delete();
        } else {
            System.out.println("No such group");
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
