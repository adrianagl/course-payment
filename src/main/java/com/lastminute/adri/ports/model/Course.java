package com.lastminute.adri.ports.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Course {

    private String id;
    private String name;
    private BigDecimal price;

    public Course(String id, String name, BigDecimal price) {
        requirePositivePrice(price);
        this.id = id;
        this.name = name;
        this.price = price;
    }

    private void requirePositivePrice(BigDecimal price) {
        Objects.requireNonNull(price);
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) &&
                Objects.equals(name, course.name) &&
                Objects.equals(price, course.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
