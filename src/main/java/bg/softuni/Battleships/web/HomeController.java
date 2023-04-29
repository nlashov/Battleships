package bg.softuni.Battleships.web;

import bg.softuni.Battleships.models.dtos.ShipDTO;
import bg.softuni.Battleships.services.ShipService;
import bg.softuni.Battleships.session.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ShipService shipService;
    private final LoggedUser loggedUser;


    public HomeController(ShipService shipService, LoggedUser loggedUser) {
        this.shipService = shipService;
        this.loggedUser = loggedUser;
    }

    @GetMapping("/")
    public String loggedOutIndex() {
        return "index";
    }

    @GetMapping("/home")
    public String loggedInIndex(Model model) {
        long loggedUserId = this.loggedUser.getId();
        List<ShipDTO> ownShips = this.shipService.getShipsOwnedBy(loggedUserId);
        List<ShipDTO> enemyShips = this.shipService.getShipsNotOwnedBy(loggedUserId);

        model.addAttribute("ownShips", ownShips);
        model.addAttribute("enemyShips", enemyShips);

        return "home";
    }
}
