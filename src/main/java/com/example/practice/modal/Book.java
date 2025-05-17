package com.example.practice.modal;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.PERSIST)
    private List<BorrowRecord> borrowRecords;

}
