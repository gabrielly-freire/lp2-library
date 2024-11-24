package br.ufrn.imd.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.ufrn.imd.exception.DatabaseException;
import br.ufrn.imd.model.Loan;

/**
 * Classe responsável por gerenciar as operações de CRUD de empréstimos no banco
 * de dados.
 * 
 * @autor Gabrielly Freire
 * @version 1.0
 */
public class LoanDAO {

    private final Connection connection;

    public LoanDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Insere um empréstimo no banco de dados
     * 
     * @param loan empréstimo a ser inserido
     * @throws SQLException caso ocorra um erro ao inserir o empréstimo ou ao fechar
     *                      os recursos
     */
    public void create(Loan loan) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(
                    "INSERT INTO loan (user_id, book_id, loan_date, due_date, return_date, is_returned) VALUES (?, ?, ?, ?, ?, ?)");

            ps.setLong(1, loan.getUserId());
            ps.setLong(2, loan.getBookId());
            ps.setDate(3, Date.valueOf(loan.getLoanDate()));
            ps.setDate(4, Date.valueOf(loan.getDueDate()));
            ps.setDate(5, Date.valueOf(loan.getReturnDate()));
            ps.setBoolean(6, loan.getIsReturned());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Empréstimo inserido com sucesso!");
            } else {
                System.out.println("Nenhum empréstimo foi inserido.");
                throw new DatabaseException("Nenhum empréstimo foi inserido.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir empréstimo: " + e.getMessage());
            throw new DatabaseException("Erro ao inserir empréstimo: " + e.getMessage());
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
     * Busca um empréstimo por id no banco de dados
     * 
     * @param id id do empréstimo a ser buscado
     * @throws SQLException caso ocorra um erro ao buscar o empréstimo ou ao fechar
     *                      os recursos
     * @return empréstimo encontrado ou null caso não encontre
     */
    public Loan findById(Long id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM loan WHERE id = ?");
            ps.setLong(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                Loan loan = new Loan();
                loan.setId(rs.getString("id"));
                loan.setUserId(rs.getLong("user_id"));
                loan.setBookId(rs.getLong("book_id"));
                loan.setLoanDate(rs.getDate("loan_date").toLocalDate());
                loan.setDueDate(rs.getDate("due_date").toLocalDate());
                loan.setReturnDate(rs.getDate("return_date").toLocalDate());
                loan.setIsReturned(rs.getBoolean("is_returned"));

                return loan;
            } else {
                System.out.println("Nenhum empréstimo encontrado.");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar empréstimo: " + e.getMessage());
            throw new DatabaseException("Erro ao buscar empréstimo: " + e.getMessage());
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
     * Busca todos os empréstimos no banco de dados
     * 
     * @throws SQLException caso ocorra um erro ao buscar os empréstimos ou ao
     *                      fechar
     *                      os recursos
     * @return lista de empréstimos encontrados
     */

    public List<Loan> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM loan");

            rs = ps.executeQuery();

            while (rs.next()) {
                Loan loan = new Loan();
                loan.setId(rs.getString("id"));
                loan.setUserId(rs.getLong("user_id"));
                loan.setBookId(rs.getLong("book_id"));
                loan.setLoanDate(rs.getDate("loan_date").toLocalDate());
                loan.setDueDate(rs.getDate("due_date").toLocalDate());
                loan.setReturnDate(rs.getDate("return_date").toLocalDate());
                loan.setIsReturned(rs.getBoolean("is_returned"));

                System.out.println(loan);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar empréstimos: " + e.getMessage());
            throw new DatabaseException("Erro ao buscar empréstimos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar os recursos: " + e.getMessage());
                throw new DatabaseException("Erro ao fechar os recursos: " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * Atualiza um empréstimo no banco de dados
     * 
     * @param id   id do empréstimo a ser atualizado
     * @param loan empréstimo com os novos dados
     * @throws SQLException caso ocorra um erro ao atualizar o empréstimo ou ao
     *                      fechar os recursos
     */
    public void update(Long id, Loan loan) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(
                    "UPDATE loan SET user_id = ?, book_id = ?, loan_date = ?, due_date = ?, return_date = ?, is_returned = ? WHERE id = ?");

            ps.setLong(1, loan.getUserId());
            ps.setLong(2, loan.getBookId());
            ps.setDate(3, Date.valueOf(loan.getLoanDate()));
            ps.setDate(4, Date.valueOf(loan.getDueDate()));
            ps.setDate(5, Date.valueOf(loan.getReturnDate()));
            ps.setBoolean(6, loan.getIsReturned());
            ps.setLong(7, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Empréstimo atualizado com sucesso!");
            } else {
                System.out.println("Nenhum empréstimo foi atualizado.");
                throw new DatabaseException("Nenhum empréstimo foi atualizado.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar empréstimo: " + e.getMessage());
            throw new DatabaseException("Erro ao atualizar empréstimo: " + e.getMessage());
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
     * Deleta um empréstimo no banco de dados
     * 
     * @param id id do empréstimo a ser deletado
     * @throws SQLException caso ocorra um erro ao deletar o empréstimo ou ao fechar
     *                      os recursos
     */
    public void delete(Long id) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("DELETE FROM loan WHERE id = ?");
            ps.setLong(1, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Empréstimo deletado com sucesso!");
            } else {
                System.out.println("Nenhum empréstimo foi deletado.");
                throw new DatabaseException("Nenhum empréstimo foi deletado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar empréstimo: " + e.getMessage());
            throw new DatabaseException("Erro ao deletar empréstimo: " + e.getMessage());
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
