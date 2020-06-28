package ru.ancndz.libraryBase.content.jobs;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

/**
 * Класс дефолтной должности, сотрудник общего назначения
 */
@Entity
@Table
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    protected int id;

    @Column
    @NotBlank
    protected String name;

    @Column
    @PositiveOrZero
    @NotNull
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
