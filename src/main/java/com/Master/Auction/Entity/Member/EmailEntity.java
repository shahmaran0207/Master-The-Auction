package com.Master.Auction.Entity.Member;

import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "email")
public class EmailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "email_status", nullable = false)
    private boolean emailStatus;

    @Builder
    public EmailEntity(String email) {
        this.email = email;
        this.emailStatus = false;
    }

}
