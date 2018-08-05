package com.company.model;

import com.company.service.DbService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Exercise {
    private int id;
    private String title;
    private String description;

    public Exercise() {
    }

    public Exercise(String title, String description) {
        this.id = 0;
        this.title = title;
        this.description = description;
    }

    // getters & setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // crud
    public static ArrayList<Exercise> loadAll() {
        ArrayList<Exercise> list = new ArrayList<>();
        String query = "SELECT id, title, description FROM exercises";

        try {
            List<String[]> rows = DbService.getData(query, null);
            for (String[] row : rows) {
                Exercise exercise = new Exercise();
                exercise.id = Integer.parseInt(row[0]);
                exercise.title = row[1];
                exercise.description = row[2];
                list.add(exercise);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static Exercise loadById(int id) {
        String query = "SELECT id, title, description FROM exercises WHERE id = ?";

        try {
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(id));
            List<String[]> rows = DbService.getData(query, params);

            String[] row = rows.get(0);
            Exercise exercise = new Exercise();
            exercise.id = Integer.parseInt(row[0]);
            exercise.title = row[1];
            exercise.description = row[2];

            return exercise;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void save() {
        if (this.id == 0) {
            String query = "INSERT INTO exercises(title, description) VALUES(?,?)";
            List<String> params = new ArrayList<>();
            params.add(this.title);
            params.add(this.description);

            try {
                Integer id = DbService.insertIntoDatabase(query, params);
                if (id != null) {
                    this.id = id;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String query = "UPDATE exercises SET title = ?, description = ? WHERE id = ?";
            List<String> params = new ArrayList<>();
            params.add(this.title);
            params.add(this.description);
            params.add(String.valueOf(this.id));

            try {
                DbService.executeUpdate(query, params);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(int id) throws SQLException {
        if (this.id != 0) {
            String query = "DELETE FROM exercises WHERE id = ?";
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
