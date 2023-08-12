package com.example.xacaton.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@Table(name = "Cat")
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "filename")
    private String fileName;

    @Column(name = "cats_first_count")
    private int catsFirstCount;

    @Column(name = "cats_second_count")
    private int catsSecondCount;

    public Cat() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return Objects.equals(id, cat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
