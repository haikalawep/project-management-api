package com.HaikalArif.project_management_api.Model;

import com.HaikalArif.project_management_api.Enum.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "task")
    private long id;

    @NotNull(message = "Title cannot be null!")
    @NotEmpty(message = "Title cannot be empty!")
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "projectId")
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Project project;

//    @OneToOne(fetch = FetchType.EAGER)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

}
