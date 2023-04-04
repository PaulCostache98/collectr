package com.gamification.collectr.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Quest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_id")
    private Long id;

    @Column(name = "quest_name", nullable = false, unique = true)
    private String questName;

    @Column(name = "quest_description", nullable = false)
    private String questDescription;

    @Column(name = "quest_type", nullable = false)
    private String questType;

    @Transient
    private List<MyUser> completed;

    @ManyToMany(mappedBy = "quests")
    @JsonIgnore
    @ToString.Exclude
    private Set<MyUser> users;

    public Quest(Quest quest) {
        this.id = quest.getId();
        this.questName = quest.getQuestName();
        this.questDescription = quest.getQuestDescription();
        this.questType = quest.getQuestType();
        this.completed = quest.getCompleted();
        this.users = quest.getUsers();
    }



}
