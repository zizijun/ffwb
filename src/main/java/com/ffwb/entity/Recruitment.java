package com.ffwb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lenovo on 2017/8/3.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "RECRUITMENT")
public class Recruitment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String profession;

    @ManyToOne
    @JoinColumn(name="enterprise_id")
    private Enterprise enterprise;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="RECRUITMENT_SKILL",joinColumns={
            @JoinColumn(name="RECRUITMENT_id",referencedColumnName = "id")},inverseJoinColumns={
            @JoinColumn(name="SKILL_id",referencedColumnName = "id")
    })
    private Set<Skill> skillSet=new HashSet<Skill>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Set<Skill> getSkillSet() {
        return skillSet;
    }

    public void setSkillSet(Set<Skill> skillSet) {
        this.skillSet = skillSet;
    }
}
