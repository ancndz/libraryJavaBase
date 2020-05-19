package ru.ancndz.libraryBase.content.jobs;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс дефолтной должности, сотрудник общего назначения
 */
@Entity
@Table(name = "jobs")
public class Worker {
    /**
     * id должности
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected int id;
    /**
     * название должности
     */
    @Column(name = "name")
    protected String name;
    /**
     * зарплата, тысяч рублей
     */
    @Column(name = "pay")
    protected int pay;

    public Worker(){}

    /**
     * конструктор
     * @param id айди должности
     * @param name название должности
     * @param pay зар. плата, тыс. руб.
     */
    public Worker(int id, String name, int pay) {
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
        Worker worker = (Worker) o;
        return getPay() == worker.getPay() &&
                getName().equals(worker.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPay());
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pay=" + pay +
                '}';
    }
}
