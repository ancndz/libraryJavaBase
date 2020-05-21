package ru.ancndz.libraryBase.content.libraryEnvironment;


import javax.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "library")
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  int id;

    @Column(name = "name")
    private  String name;

    @Column(name = "address")
    private  String address;

    /**
     * класс "библиотека"
     * @param id айди библиотеки
     * @param name название библиотеки
     * @param address адрес библиотеки
     */
    public Library(int id,String name,String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Library(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Library() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Library library = (Library) o;
        return getName().equals(library.getName()) &&
                getAddress().equals(library.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAddress());
    }

    @Override
    public String toString() {
        return "Library{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
