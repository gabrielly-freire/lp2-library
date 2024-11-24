package br.ufrn.imd.model;

import br.ufrn.imd.model.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa um livro.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private Long id;
    private String title;
    private String author;
    private Genre genre;
    private Integer publicationYear;
    private String isbn;
    private Boolean isAvailable;

}
