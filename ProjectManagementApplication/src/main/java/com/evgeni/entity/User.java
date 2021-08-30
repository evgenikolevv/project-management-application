package com.evgeni.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime dateOfCreation;

    @Column(name = "creator_id")
    private Long creatorId;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime dateOfLastChange;

    @Column(name = "updated_by")
    private Long editedBy;

    @Column(name = "role")
    private Boolean isAdmin;

    public User(String username, String password, String firstName, String lastName,
                Long creatorId, Long editedBy, Boolean isAdmin) {

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.creatorId = creatorId;
        this.editedBy = editedBy;
        this.isAdmin = isAdmin;
    }
}
