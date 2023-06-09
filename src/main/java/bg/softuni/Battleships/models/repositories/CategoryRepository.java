package bg.softuni.Battleships.models.repositories;

import bg.softuni.Battleships.models.entities.Category;
import bg.softuni.Battleships.models.enums.ShipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(ShipType type);
}
