package bg.softuni.Battleships.services;

import bg.softuni.Battleships.models.dtos.StartBattleDTO;
import bg.softuni.Battleships.models.entities.Ship;
import bg.softuni.Battleships.models.repositories.ShipRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BattleService {

    private ShipRepository shipRepository;

    public BattleService(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    public void attack(StartBattleDTO attackData) {
        Optional<Ship> attackerOpt = this.shipRepository.findById((long) attackData.getAttackerId());
        Optional<Ship> defenderOpt = this.shipRepository.findById((long) attackData.getDefenderId());

        if (attackerOpt.isEmpty() || defenderOpt.isEmpty()) {
            throw new NoSuchElementException();
        }

        Ship attacker = attackerOpt.get();
        Ship defender = defenderOpt.get();

        long newDefenderHealth = defender.getHealth() - attacker.getPower();


        defender.setHealth(newDefenderHealth);
        this.shipRepository.save(defender);
        if (newDefenderHealth <= 0) {
            this.shipRepository.delete(defender);
        }
    }
}
