package ru.ancndz.libraryBase.content.jobs;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс дефолтной должности, сотрудник общего назначения
 */
@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected int id;

    @Column(name = "name")
    protected String name;

    @Column(name = "pay")
    protected int pay;

    public Job(){}

    public Job(String name, int pay) {
        this.name = name;
        this.pay = pay;
    }

    public Job(int id, String name, int pay) {
        this.id = id;
        this.name = name;
        this.pay = pay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return getPay() == job.getPay() &&
                getName().equals(job.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPay());
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pay=" + pay +
                '}';
    }
}
