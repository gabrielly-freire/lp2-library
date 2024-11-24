package br.ufrn.imd.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa um empréstimo.
 * 
 * @author Gabrielly Freire
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    
    private String id;
    private String userId;
    private String bookId;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Boolean isReturned;

}
