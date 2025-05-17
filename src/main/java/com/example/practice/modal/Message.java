package com.example.practice.modal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "messages", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id"})
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "date", nullable = false)
    private String date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.SENT;

}
