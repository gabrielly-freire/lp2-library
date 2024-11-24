package br.ufrn.imd.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum para representar os gêneros dos livros.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum Genre {
    ACTION("Ação"), 
    ADVENTURE("Aventura"), 
    COMEDY("Comédia"), 
    CRIME("Crime"), 
    DRAMA("Drama"), 
    FANTASY("Fantasia"), 
    HISTORICAL("Histórico"), 
    HORROR("Terror"), 
    MYSTERY("Mistério"), 
    PHILOSOPHICAL("Filosófico"), 
    POLITICAL("Político"), 
    ROMANCE("Romance"),
    SAGA("Saga"), 
    SATIRE("Sátira"), 
    SCIENCE_FICTION("Ficção Científica"), 
    THRILLER("Suspense"), 
    URBAN("Urbano"), 
    WESTERN("Faroeste");

    private final String name;

}
