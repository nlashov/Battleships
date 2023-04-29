package bg.softuni.Battleships.services;

import bg.softuni.Battleships.models.dtos.CreateShipDTO;
import bg.softuni.Battleships.models.dtos.ShipDTO;
import bg.softuni.Battleships.models.entities.Category;
import bg.softuni.Battleships.models.entities.Ship;
import bg.softuni.Battleships.models.entities.User;
import bg.softuni.Battleships.models.enums.ShipType;
import bg.softuni.Battleships.models.repositories.CategoryRepository;
import bg.softuni.Battleships.models.repositories.ShipRepository;
import bg.softuni.Battleships.models.repositories.UserRepository;
import bg.softuni.Battleships.session.LoggedUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShipService {

    private ShipRepository shipRepository;
    private CategoryRepository categoryRepository;
    private LoggedUser loggedUser;
    private UserRepository userRepository;

    public ShipService(ShipRepository shipRepository,
                       CategoryRepository categoryRepository,
                       LoggedUser loggedUser,
                       UserRepository userRepository) {
        this.shipRepository = shipRepository;
        this.categoryRepository = categoryRepository;
        this.loggedUser = loggedUser;
        this.userRepository = userRepository;
    }

    public boolean create(CreateShipDTO createShipDTO) {

        Optional<Ship> byName =
                this.shipRepository.findByName(createShipDTO.getName());

        if (byName.isPresent()) {
            return false;
        }

        ShipType type = switch (createShipDTO.getCategory()) {
            case 0 -> ShipType.BATTLE;
            case 1 -> ShipType.CARGO;
            case 2 -> ShipType.PATROL;
            default -> ShipType.BATTLE;
        };

        Category category = this.categoryRepository.findByName(type);
        Optional<User> owner = this.userRepository.findById(this.loggedUser.getId());
        Ship ship = new Ship();
        ship.setName(createShipDTO.getName());
        ship.setPower(createShipDTO.getPower());
        ship.setHealth(createShipDTO.getHealth());
        ship.setCreated(createShipDTO.getCreated());
        ship.setCategory(category);
        ship.setOwner(owner.get());




        this.shipRepository.save(ship);
        return true;
    }

    public List<ShipDTO> getShipsOwnedBy(long ownerId) {
       return this.shipRepository.findByUserId(ownerId)
               .stream()
               .map(ShipDTO::new)
               .collect(Collectors.toList());
    }

    public List<ShipDTO> getShipsNotOwnedBy(long ownerId) {
        return null;
    }
}
