package bg.softuni.Battleships.models.repositories;

import bg.softuni.Battleships.models.dtos.ShipDTO;
import bg.softuni.Battleships.models.entities.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {
    Optional<Ship> findByName(String name);

    List<Ship> findByOwnerId(long ownerId);

    List<Ship> findByOwnerIdNot(long ownerId);

    List<Ship> findByOrderByHealthAscNameDescPowerAsc();
}
