package com.evgeni.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "worklogs")
@Data
@NoArgsConstructor
public class WorkLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime dateOfCreation;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime dateOfLastChange;

    @Column(name = "updated_by")
    private Long editedBy;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User creator;

    public WorkLog(String title, String description, Long editedBy, Task task, User creator) {
        this.title = title;
        this.description = description;
        this.editedBy = editedBy;
        this.task = task;
        this.creator = creator;
    }
}

