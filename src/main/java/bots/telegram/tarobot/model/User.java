package bots.telegram.tarobot.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long telegramId;

    @Column(nullable = false)
    private String username;

    private String about;

    @OneToMany(mappedBy = "id",
            fetch = FetchType.EAGER)
    private List<Request> requests;
}
