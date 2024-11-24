package br.ufrn.imd.service;

import br.ufrn.imd.dao.UserDAO;
import br.ufrn.imd.exception.ResourceNotFoundException;
import br.ufrn.imd.model.User;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Classe de serviço para operações relacionadas a usuários.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Cria um novo usuário.
     *
     * @param user o usuário a ser criado
     * @throws IllegalArgumentException se os dados do usuário forem inválidos
     */
    public void createUser(User user) {
        validateUser(user);
        userDAO.create(user);
    }

    /**
     * Atualiza um usuário existente.
     *
     * @param id   o ID do usuário a ser atualizado
     * @param user os novos dados do usuário
     * @throws IllegalArgumentException se os dados do usuário forem inválidos
     * @throws ResourceNotFoundException se o usuário não for encontrado
     */
    public void updateUser(Long id, User user) {
        validateId(id);
        validateUser(user);
        findUserById(id);

        userDAO.update(id, user);
    }

    /**
     * Busca um usuário pelo seu ID.
     *
     * @param id o ID do usuário a ser buscado
     * @throws IllegalArgumentException se o ID for inválido
     * @throws ResourceNotFoundException se o usuário não for encontrado
     * @return o usuário encontrado ou null caso não exista
     */
    public User findUserById(Long id) {
        validateId(id);
        User user = userDAO.findById(id);

        if (user == null) {
            throw new ResourceNotFoundException("Usuário não encontrado.");
        }

        return user;
    }

    /**
     * Busca todos os usuários.
     *
     * @throws ResourceNotFoundException se não existirem usuários
     * @return a lista de usuários
     */
    public List<User> findAllUsers() {
        List<User> users = userDAO.findAll();

        if (users.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum usuário encontrado.");
        }

        return users;
    }

    /**
     * Realiza uma busca por usuários com base em um texto de pesquisa.
     *
     * @param query a string de pesquisa
     * @throws IllegalArgumentException  se a string de pesquisa for inválida
     * @throws ResourceNotFoundException se nenhum usuário for encontrado
     * @return a lista de usuários encontrados
     */
    public List<User> searchUsers(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("A consulta não pode ser vazia.");
        }

        List<User> user = userDAO.searchUsers(query);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum usuário encontrado.");
        }

        return user;
    }

    /**
     * Deleta um usuário pelo seu ID.
     *
     * @param id o ID do usuário a ser deletado
     * @throws IllegalArgumentException se o ID for inválido
     */
    public void deleteUser(Long id) {
        validateId(id);
        findUserById(id);

        userDAO.delete(id);
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do usuário inválido.");
        }
    }

    /**
     * Valida os dados de um usuário.
     *
     * @param user o usuário a ser validado
     * @throws IllegalArgumentException se algum dado do usuário for inválido
     */
    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo.");
        }

        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do usuário é obrigatório.");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty() || !isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("E-mail inválido.");
        }

        if (user.getPhoneNumber() == null || user.getPhoneNumber().trim().isEmpty()
                || !isValidPhoneNumber(user.getPhoneNumber())) {
            throw new IllegalArgumentException("Número de telefone inválido.");
        }
    }

    /**
     * Valida o formato do e-mail.
     *
     * @param email o e-mail a ser validado
     * @return true se o e-mail for válido, false caso contrário
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    /**
     * Valida o formato do número de telefone.
     *
     * @param phoneNumber o número de telefone a ser validado
     * @return true se o número de telefone for válido, false caso contrário
     */
    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^\\+?\\d{1,4}?\\d{6,14}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        return pattern.matcher(phoneNumber).matches();
    }
}
