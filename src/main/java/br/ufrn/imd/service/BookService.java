package br.ufrn.imd.service;

import java.util.List;

import br.ufrn.imd.dao.BookDAO;
import br.ufrn.imd.exception.ResourceNotFoundException;
import br.ufrn.imd.model.Book;

/**
 * Classe de serviço para operações relacionadas a livros.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
public class BookService {

    private final BookDAO bookDAO;

    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    /**
     * Cria um novo livro no banco de dados.
     * 
     * @param book Livro a ser inserido.
     * @throws IllegalArgumentException Se o livro for nulo, se o título, autor ou
     *                                  gênero forem nulos ou vazios.
     */
    public void createBook(Book book) {
        validateBook(book);

        bookDAO.create(book);
    }

    /**
     * Busca um livro pelo ID.
     * 
     * @param id ID do livro.
     * @throws IllegalArgumentException Se o ID for nulo ou menor ou igual a zero.
     * @throws ResourceNotFoundException Se o livro não for encontrado.
     * @return Livro encontrado ou null se não existir.
     */
    public Book findBookById(Long id) {
        validateId(id);
        Book book = bookDAO.findById(id);

        if (book == null) {
            throw new ResourceNotFoundException("Livro não encontrado.");
        }

        return book;
    }

    /**
     * Retorna todos os livros no banco de dados.
     * 
     * @throws ResourceNotFoundException Se não existirem livros.
     * @return Lista de livros.
     */
    public List<Book> findAllBooks() {
        List<Book> books = bookDAO.findAll();

        if (books == null || books.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum livro encontrado.");
        }

        return books;
    }

    /**
     * Busca livros com base em uma consulta.
     * 
     * @param query Texto para buscar no título ou autor.
     * @throws IllegalArgumentException Se a query de busca for nula ou vazia.
     * @throws ResourceNotFoundException Se não existirem livros com base na consulta.
     * @return Lista de livros encontrados.
     */
    public List<Book> searchBooks(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("A consulta não pode ser vazia.");
        }

        List<Book> books = bookDAO.searchBooks(query);

        if (books == null || books.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum livro encontrado.");
        }

        return books;
    }

    /**
     * Atualiza os dados de um livro.
     * 
     * @param id   ID do livro a ser atualizado.
     * @param book Dados atualizados do livro.
     * @throws IllegalArgumentException Se o ID for nulo ou menor ou igual a zero, se
     *                                  o livro for nulo, se o título, autor ou gênero
     *                                  forem nulos ou vazios.
     * @throws ResourceNotFoundException Se o livro não for encontrado.
     */
    public void updateBook(Long id, Book book) {
        validateId(id);
        validateBook(book);
        findBookById(id);

        bookDAO.update(id, book);
    }

    /**
     * Deleta um livro pelo ID.
     * 
     * @param id ID do livro a ser deletado.
     * @throws IllegalArgumentException Se o ID for nulo ou menor ou igual a zero.
     * @throws ResourceNotFoundException Se o livro não for encontrado.
     */
    public void deleteBook(Long id) {
        validateId(id);
        findBookById(id);

        bookDAO.delete(id);
    }

    /**
     * Valida os dados de um livro.
     * 
     * @param book Livro a ser validado.
     * @throws IllegalArgumentException Se o livro for nulo, se o título, autor ou
     *                                  gênero forem nulos ou vazios.
     */
    private void validateBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("O livro não pode ser nulo.");
        }
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new IllegalArgumentException("O título do livro é obrigatório.");
        }
        if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("O autor do livro é obrigatório.");
        }
        if (book.getGenre() == null) {
            throw new IllegalArgumentException("O gênero do livro é obrigatório.");
        }
    }

    /**
     * Valida o ID de um livro.
     * 
     * @param id ID do livro.
     * @throws IllegalArgumentException Se o ID for nulo ou menor ou igual a zero.
     */
    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do livro inválido.");
        }
    }
}
