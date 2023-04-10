package com.gamification.collectr.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

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

    @Column
    private List<Integer> steps;

    @Column
    private Integer defaultSteps;

    @Column
    private Integer tier;

    @Column
    private String createdBy;

    @Column
    private List<Long> completed;

    @Column(name = "reward", nullable = false)
    private Integer reward;

    @ManyToMany(mappedBy = "quests", fetch = FetchType.EAGER)
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
        this.reward = quest.getReward();
        this.steps = quest.getSteps();
        this.defaultSteps = quest.getDefaultSteps();
        this.tier = quest.getTier();
        this.createdBy = quest.getCreatedBy();
    }


    public List<Long> getCompleted() {
        if(completed == null) {
            return new ArrayList<Long>();
        }
        return completed;
    }

    public HashSet<MyUser> getUsers() {
        List<MyUser> userTemp = new ArrayList<>();
        if(this.users != null) {
            userTemp.addAll(List.copyOf(this.users));
        }
        if(this.users == null) {
            return new HashSet<>();
        }
        userTemp.sort(Comparator.comparing(MyUser::getId));
        return new HashSet<>(userTemp);
    }

    public void setUsers(Set<MyUser> users) {
        List<MyUser> userTemp = new ArrayList<>(List.copyOf(users));
        userTemp.sort(Comparator.comparing(MyUser::getId));
        this.users = new HashSet<>(userTemp);
    }

}
