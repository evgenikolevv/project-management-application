package com.evgeni.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime dateOfCreation;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime dateOfLastChange;

    @Column(name = "updated_by")
    private Long editedBy;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User creator;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "teams_projects",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "team_id")}
    )
    private List<Team> teams = new ArrayList<>();

    public Project(String name, Long editedBy, User creator) {
        this.name = name;
        this.editedBy = editedBy;
        this.creator = creator;
    }
}

