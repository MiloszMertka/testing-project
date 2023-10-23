package com.example.testing.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.NONE)
public class Borrow {

    private static final String BOOK_ALREADY_RETURNED_ERROR_MESSAGE = "Book already returned";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime borrowDate;

    @Column(nullable = false)
    private LocalDateTime returnDate;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Book book;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Reader reader;

    public Borrow(@NonNull Book book, @NonNull Reader reader) {
        this.book = book;
        this.reader = reader;
        borrowDate = LocalDateTime.now();
    }

    public void returnBook() {
        checkIfBookAlreadyReturned();
        returnDate = LocalDateTime.now();
    }

    private void checkIfBookAlreadyReturned() {
        if (returnDate != null) {
            throw new IllegalStateException(BOOK_ALREADY_RETURNED_ERROR_MESSAGE);
        }
    }

}
