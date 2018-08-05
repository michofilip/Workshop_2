package com.company.model;

import com.company.service.DbService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private int id;
    private String created;
    private String updated;
    private String description;
    private Exercise exercise;
    private User user;

    public Solution() {
    }

    public Solution(String created, String updated, String description, int exercise_id, int user_id) {
        this.id = 0;
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exercise = Exercise.loadById(exercise_id);
        this.user = User.loadById(user_id);
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", description='" + description + '\'' +
                ", exercise=" + exercise.getId() +
                ", user=" + user.getId() +
                '}';
    }

    // getters & setters
    public int getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public void setExercise(int exercise_id) {
        this.exercise = Exercise.loadById(exercise_id);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUser(int user_id) {
        this.user = User.loadById(user_id);
    }

    // crud
    public static ArrayList<Solution> loadAll() {
        ArrayList<Solution> list = new ArrayList<>();
        String query = "SELECT id, created, updated, description, excercise_id, user_id FROM solutions";

        try {
            return getSolutions(DbService.getData(query, null));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static Solution loadById(int id) {
        String query = "SELECT id, created, updated, description, excercise_id, user_id FROM solutions WHERE id = ?";

        try {
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(id));
            return getSolutions(DbService.getData(query, params)).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void save() {
        if (this.id == 0) {
            String query = "INSERT INTO solutions(created, updated, description, excercise_id, user_id) VALUES(?,?,?,?,?)";
            List<String> params = new ArrayList<>();
            params.add(this.created);
            params.add(this.updated);
            params.add(this.description);
            params.add(String.valueOf(this.exercise.getId()));
            params.add(String.valueOf(this.user.getId()));

            try {
                Integer id = DbService.insertIntoDatabase(query, params);
                if (id != null) {
                    this.id = id;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String query = "UPDATE solutions SET created = ?, updated = ?, description = ?, excercise_id = ?, user_id = ? WHERE id = ?";
            List<String> params = new ArrayList<>();
            params.add(this.created);
            params.add(this.updated);
            params.add(this.description);
            params.add(String.valueOf(this.exercise.getId()));
            params.add(String.valueOf(this.user.getId()));
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
            String query = "DELETE FROM solutions WHERE id = ?";
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

    // extra
    public static ArrayList<Solution> loadAllByUserId(int user_id) {
        ArrayList<Solution> list = new ArrayList<>();
        String query = "SELECT id, created, updated, description, excercise_id, user_id FROM solutions WHERE user_id = ?";

        try {
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(user_id));
            return getSolutions(DbService.getData(query, params));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static ArrayList<Solution> loadAllByExerciseId(int exercise_id) {
        ArrayList<Solution> list = new ArrayList<>();
        String query = "SELECT id, created, updated, description, exercise_id, user_id FROM solutions WHERE exercise_id = ?";

        try {
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(exercise_id));
            return getSolutions(DbService.getData(query, params));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private static ArrayList<Solution> getSolutions(List<String[]> strs) {
        ArrayList<Solution> list = new ArrayList<>();
        for (String[] row : strs) {
            Solution solution = new Solution();

            solution.id = Integer.parseInt(row[0]);
            solution.created = row[1];
            solution.updated = row[2];
            solution.description = row[3];
            solution.exercise = Exercise.loadById(Integer.parseInt(row[4]));
            solution.user = User.loadById(Integer.parseInt(row[5]));

            list.add(solution);
        }
        return list;
    }


}
