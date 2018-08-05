package com.company.model;

import com.company.service.DbService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Group {
    private int id;
    private String name;

    public Group() {
    }

    public Group(String name) {
        this.id = 0;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    // getters & setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // crud
    public static ArrayList<Group> loadAll() {
        ArrayList<Group> list = new ArrayList<>();
        String query = "SELECT id, name FROM groups";

        try {
            List<String[]> rows = DbService.getData(query, null);
            for (String[] row : rows) {
                Group group = new Group();
                group.id = Integer.parseInt(row[0]);
                group.name = row[1];
                list.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static Group loadById(int id) {
        String query = "SELECT id, name FROM groups WHERE id = ?";

        try {
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(id));
            List<String[]> rows = DbService.getData(query, params);

            String[] row = rows.get(0);
            Group group = new Group();
            group.id = Integer.parseInt(row[0]);
            group.name = row[1];

            return group;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void save() {
        if (this.id == 0) {
            String query = "INSERT INTO groups(name) VALUES(?)";
            List<String> params = new ArrayList<>();
            params.add(this.name);

            try {
                Integer id = DbService.insertIntoDatabase(query, params);
                if (id != null) {
                    this.id = id;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String query = "UPDATE groups SET name = ? WHERE id = ?";
            List<String> params = new ArrayList<>();
            params.add(this.name);
            params.add(String.valueOf(this.id));

            try {
                DbService.executeUpdate(query, params);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete() {
        if (this.id != 0) {
            String query = "DELETE FROM groups WHERE id=?";
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(id));

            try {
                DbService.executeUpdate(query, params);
                this.id = 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
