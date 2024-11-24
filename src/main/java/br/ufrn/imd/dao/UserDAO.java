package br.ufrn.imd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.ufrn.imd.exception.DatabaseException;
import br.ufrn.imd.model.User;

/**
 * Classe responsável por gerenciar as operações de CRUD de usuários no banco de
 * dados.
 * 
 * @autor Gabrielly Freire
 * @version 1.0
 */
public class UserDAO {

    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Insere um usuário no banco de dados
     * 
     * @param user usuário a ser inserido
     * @throws SQLException caso ocorra um erro ao inserir o usuário ou ao fechar os
     *                      recursos
     */
    public void create(User user) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(
                    "INSERT INTO user (name, email, phone_number) VALUES (?, ?, ?)");

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhoneNumber());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Usuário inserido com sucesso!");
            } else {
                System.out.println("Nenhum usuário foi inserido.");
                throw new DatabaseException("Nenhum usuário foi inserido.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
            throw new DatabaseException("Erro ao inserir usuário: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar o PreparedStatement: " + e.getMessage());
                throw new DatabaseException("Erro ao fechar o PreparedStatement: " + e.getMessage());
            }
        }
    }

    /**
     * Busca um usuário por id no banco de dados
     * 
     * @param id id do usuário a ser buscado
     * @throws SQLException caso ocorra um erro ao buscar o usuário ou ao fechar os
     *                      recursos
     * @return usuário encontrado ou null caso não encontre
     */
    public User findById(Long id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM user WHERE id = ?");
            ps.setLong(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));

                return user;
            } else {
                System.out.println("Nenhum usuário encontrado.");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
            throw new DatabaseException("Erro ao buscar usuário: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
                throw new DatabaseException("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

    /**
     * Busca todos os usuários no banco de dados
     * 
     * @throws SQLException caso ocorra um erro ao buscar os usuários ou ao fechar
     *                      os
     *                      recursos
     * @return lista de usuários encontrados
     */
    public List<User> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM user");

            rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));

                System.out.println(user);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuários: " + e.getMessage());
            throw new DatabaseException("Erro ao buscar usuários: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
                throw new DatabaseException("Erro ao fechar recursos: " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * Busca usuários por uma query no banco de dados
     * 
     * @param query query de busca
     * @throws SQLException caso ocorra um erro ao buscar os usuários ou ao fechar
     *                      os
     *                      recursos
     * @return lista de usuários encontrados com base na string de busca em nome,
     *         email ou telefone
     */
    public List<User> searchUsers(String query) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection
                    .prepareStatement("SELECT * FROM user WHERE name LIKE ? OR email LIKE ? OR phone_number LIKE ?");
            ps.setString(1, "%" + query + "%");
            ps.setString(2, "%" + query + "%");
            ps.setString(3, "%" + query + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));

                System.out.println(user);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuários: " + e.getMessage());
            throw new DatabaseException("Erro ao buscar usuários: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
                throw new DatabaseException("Erro ao fechar recursos: " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * Atualiza um usuário no banco de dados
     * 
     * @param id   id do usuário a ser atualizado
     * @param user usuário com os novos dados
     * 
     * @throws SQLException caso ocorra um erro ao atualizar o usuário ou ao fechar
     *                      os
     *                      recursos
     */
    public void update(Long id, User user) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(
                    "UPDATE user SET name = ?, email = ?, phone_number = ? WHERE id = ?");

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhoneNumber());
            ps.setLong(4, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Usuário atualizado com sucesso!");
            } else {
                System.out.println("Nenhum usuário foi atualizado.");
                throw new DatabaseException("Nenhum usuário foi atualizado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            throw new DatabaseException("Erro ao atualizar usuário: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar o PreparedStatement: " + e.getMessage());
                throw new DatabaseException("Erro ao fechar o PreparedStatement: " + e.getMessage());
            }
        }
    }

    /**
     * Deleta um usuário no banco de dados
     * 
     * @param id id do usuário a ser deletado
     * @throws SQLException caso ocorra um erro ao deletar o usuário ou ao fechar os
     *                      recursos
     */
    public void delete(Long id) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("DELETE FROM user WHERE id = ?");
            ps.setLong(1, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Usuário deletado com sucesso!");
            } else {
                System.out.println("Nenhum usuário foi deletado.");
                throw new DatabaseException("Nenhum usuário foi deletado.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar usuário: " + e.getMessage());
            throw new DatabaseException("Erro ao deletar usuário: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar o PreparedStatement: " + e.getMessage());
                throw new DatabaseException("Erro ao fechar o PreparedStatement: " + e.getMessage());
            }
        }
    }

}
