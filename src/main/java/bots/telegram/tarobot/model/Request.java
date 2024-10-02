package bots.telegram.tarobot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10000)
    private String request;

    @Column(length = 10000)
    private String response;

    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
    }


}
