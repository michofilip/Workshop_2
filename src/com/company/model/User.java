package com.company.model;

import com.company.service.DbService;
import org.mindrot.BCrypt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private Group group;

    public User() {
    }

    public User(String username, String password, String email, int group_id) {
        this.id = 0;
        this.username = username;
        this.email = email;
        setPassword(password);
        this.group = Group.loadById(group_id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", group_id=" + group.getId() +
                ", group=" + group.getName() +
                '}';
    }

    // getters & setters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setGroup(int group_id) {
        this.group = Group.loadById(group_id);
    }

    // crud
    public static ArrayList<User> loadAll() {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT id, username, email, password, group_id FROM users";

        try {
            List<String[]> rows = DbService.getData(query, null);
            for (String[] row : rows) {
                User user = new User();
                user.id = Integer.parseInt(row[0]);
                user.username = row[1];
                user.email = row[2];
                user.password = row[3];
                user.group = Group.loadById(Integer.parseInt(row[4]));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static User loadById(int id) {
        String query = "SELECT id, username, email, password, group_id FROM users WHERE id = ?";

        try {
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(id));
            List<String[]> rows = DbService.getData(query, params);

            if (rows.isEmpty()) {
                return null;
            } else {
                String[] row = rows.get(0);
                User user = new User();
                user.id = Integer.parseInt(row[0]);
                user.username = row[1];
                user.email = row[2];
                user.password = row[3];
                user.group = Group.loadById(Integer.parseInt(row[4]));

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void save() {
        if (this.id == 0) {
            String query = "INSERT INTO users(username, email, password, group_id) VALUES(?,?,?,?)";
            List<String> params = new ArrayList<>();
            params.add(this.username);
            params.add(this.email);
            params.add(this.password);
            params.add(String.valueOf(this.group.getId()));

            try {
                Integer id = DbService.insertIntoDatabase(query, params);
                if (id != null) {
                    this.id = id;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String query = "UPDATE users SET username = ?, email = ?, password = ?, group_id = ? WHERE id = ?";
            List<String> params = new ArrayList<>();
            params.add(this.username);
            params.add(this.email);
            params.add(this.password);
            params.add(String.valueOf(this.group.getId()));
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
            String query = "DELETE FROM users WHERE id=?";
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(this.id));

            try {
                DbService.executeUpdate(query, params);
                this.id = 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // extra
    public static ArrayList<User> loadAllByGroupId(int groupId) {
        ArrayList<User> users = new ArrayList<>();
        //language=MySQL
        String query = "SELECT id, username, email, password, group_id\n" +
                "FROM users\n" +
                "WHERE group_id = ?;";

        try {
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(groupId));
            List<String[]> rows = DbService.getData(query, params);
            for (String[] row : rows) {
                User user = new User();
                user.id = Integer.parseInt(row[0]);
                user.username = row[1];
                user.email = row[2];
                user.password = row[3];
                user.group = Group.loadById(Integer.parseInt(row[4]));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

}
