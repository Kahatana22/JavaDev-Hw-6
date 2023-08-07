package org.example.service;

import org.example.entities.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ClientService {
    public long create(String name) {
        validateName(name);

        try (Connection connection = Database.getInstance().getConnection()) {
            String sql = "INSERT INTO client (name) VALUES (?)";
            PreparedStatement pst = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, name);
            pst.executeUpdate();

            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getById(long id) {
        validateId(id);

        try (Connection connection = Database.getInstance().getConnection()) {
            String sql = "SELECT name FROM client WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setLong(1, id);
            ResultSet resultSet = pst.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setName(long id, String name) {
        validateId(id);
        validateName(name);

        try (Connection connection = Database.getInstance().getConnection()) {
            String sql = "UPDATE client SET name = ? WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, name);
            pst.setLong(2, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(long id) {
        validateId(id);

        try (Connection connection = Database.getInstance().getConnection()) {
            String deletePWQ = "DELETE FROM project_worker WHERE project_id IN (SELECT id FROM project WHERE client_id = ?)";
            String deletePQ = "DELETE FROM project WHERE client_id = ?";
            String deleteCQ = "DELETE FROM client WHERE id = ?";

            deleteRecords(connection, deletePWQ, id);
            deleteRecords(connection, deletePQ, id);
            deleteRecords(connection, deleteCQ, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteRecords(Connection connection, String delete, long id) throws SQLException {
        PreparedStatement deletePST = connection.prepareStatement(delete);
        deletePST.setLong(1, id);
        deletePST.executeUpdate();
        deletePST.close();
    }

    public List<Client> listAll() {
        List<Client> allClients = new ArrayList<>();

        try (Connection connection = Database.getInstance().getConnection()) {
            String sql = "SELECT * FROM client";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                long clientId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                allClients.add(new Client(clientId, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allClients;
    }

    private void validateName(String name) {
        if (name == null || name.length() > 25) {
            throw new IllegalArgumentException("The name did not pass validation!");
        }
    }

    private void validateId(Long id) {
        if (id < 1) {
            throw new IllegalArgumentException("ID has not been validated!");
        }
    }
}
