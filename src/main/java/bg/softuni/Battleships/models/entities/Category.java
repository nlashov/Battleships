package bg.softuni.Battleships.models.entities;

import bg.softuni.Battleships.models.enums.ShipType;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(unique = true, nullable = false)
    private ShipType name;

    @Column(columnDefinition = "text")
    private String description;

    public Category() {
    }

    public Category(ShipType name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Category setId(Long id) {
        this.id = id;
        return this;
    }

    public ShipType getName() {
        return name;
    }

    public Category setName(ShipType name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Category setDescription(String description) {
        this.description = description;
        return this;
    }
}
