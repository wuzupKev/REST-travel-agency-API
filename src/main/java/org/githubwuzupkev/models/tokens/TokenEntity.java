package org.githubwuzupkev.models.tokens;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.githubwuzupkev.models.auth.UserEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "tokens")
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private  String token;

    @Enumerated(EnumType.STRING)
    private  TokenType tokenType;

    private boolean isExpired;

    private boolean isRevoked;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
