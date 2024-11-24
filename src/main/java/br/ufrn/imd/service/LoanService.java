package br.ufrn.imd.service;

import br.ufrn.imd.dao.BookDAO;
import br.ufrn.imd.dao.LoanDAO;
import br.ufrn.imd.dao.UserDAO;
import br.ufrn.imd.exception.ResourceNotFoundException;
import br.ufrn.imd.model.Loan;

import java.util.List;

/**
 * Classe de serviço para operações relacionadas a empréstimos.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
public class LoanService {

    private final LoanDAO loanDAO;
    private final BookDAO bookDAO;
    private final UserDAO userDAO;

    public LoanService(LoanDAO loanDAO, BookDAO bookDAO, UserDAO userDAO) {
        this.loanDAO = loanDAO;
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
    }

    /**
     * Cria um empréstimo, validando a existência do usuário e do livro.
     *
     * @param loan Empréstimo a ser criado.
     * @throws IllegalArgumentException se os dados de empréstimo forem nulos
     */
    public void createLoan(Loan loan) {
        validateLoan(loan);
        
        loanDAO.create(loan);
    }

    /**
     * Busca um empréstimo por id.
     *
     * @param id id do empréstimo a ser buscado.
     * @return Empréstimo encontrado.
     * @throws ResourceNotFoundException caso o empréstimo não exista.
     */
    public Loan findLoanById(Long id) {
        Loan loan = loanDAO.findById(id);
        if (loan == null) {
            throw new ResourceNotFoundException("Empréstimo com ID " + id + " não encontrado.");
        }
        return loan;
    }

    /**
     * Busca todos os empréstimos.
     *
     * @return Lista de empréstimos.
     * @throws ResourceNotFoundException caso não existam empréstimos.
     */
    public List<Loan> findAllLoans() {
        List<Loan> loans = loanDAO.findAll();

        if (loans == null || loans.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum empréstimo encontrado.");
        }
        return loans;
    }

    /**
     * Atualiza um empréstimo.
     *
     * @param id   id do empréstimo a ser atualizado.
     * @param loan empréstimo com os novos dados.
     * @throws ResourceNotFoundException caso o empréstimo não exista.
     * @throws IllegalArgumentException  caso os dados do empréstimo sejam inválidos.
     */
    public void updateLoan(Long id, Loan loan) {
        validateId(id);
        findLoanById(id);
        validateLoan(loan);

        loanDAO.update(id, loan);
    }

    /**
     * Deleta um empréstimo.
     *
     * @param id id do empréstimo a ser deletado.
     * @throws IllegalArgumentException  caso o id seja inválido.
     * @throws ResourceNotFoundException caso ocorra um erro.
     */
    public void deleteLoan(Long id) {
        validateId(id);
        findLoanById(id);

        loanDAO.delete(id);
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
    }

    private void validateLoan(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("O empréstimo não pode ser nulo.");
        }
        if (loan.getUserId() == null || loan.getUserId() <= 0) {
            throw new IllegalArgumentException("O ID do usuário é obrigatório.");
        }
        if (loan.getBookId() == null || loan.getBookId() <= 0) {
            throw new IllegalArgumentException("O ID do livro é obrigatório.");
        }

        if (userDAO.findById(loan.getUserId()) == null) {
            throw new ResourceNotFoundException("Usuário com ID " + loan.getUserId() + " não encontrado.");
        }
        if (bookDAO.findById(loan.getBookId()) == null) {
            throw new ResourceNotFoundException("Livro com ID " + loan.getBookId() + " não encontrado.");
        }

    }
}
