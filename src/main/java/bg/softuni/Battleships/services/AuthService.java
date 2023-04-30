package bg.softuni.Battleships.services;

import bg.softuni.Battleships.models.dtos.UserLoginDTO;
import bg.softuni.Battleships.models.dtos.UserRegistrationDTO;
import bg.softuni.Battleships.models.entities.User;
import bg.softuni.Battleships.models.repositories.UserRepository;
import bg.softuni.Battleships.session.LoggedUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private UserRepository userRepository;
    private LoggedUser userSession;

    public AuthService(UserRepository userRepository, LoggedUser userSession) {
        this.userRepository = userRepository;
        this.userSession = userSession;
    }

    public boolean register(UserRegistrationDTO regDTO) {
        if (!regDTO.getPassword().equals(regDTO.getConfirmPassword())) {
            return false;
        }

        //duplicate email
        Optional<User> byEmail = this.userRepository.findByEmail(regDTO.getEmail());
        if (byEmail.isPresent()) {
            return false;
        }

        Optional<User> byUsername = this.userRepository.findByUsername(regDTO.getUsername());
        if (byUsername.isPresent()) {
            return false;
        }


        User user = new User();
        user.setUsername(regDTO.getUsername());
        user.setEmail(regDTO.getEmail());
        user.setFullName(regDTO.getFullName());
        user.setPassword(regDTO.getPassword());

        this.userRepository.save(user);

        return true;
    }

    public boolean login(UserLoginDTO loginDTO) {
        Optional<User> user = this.userRepository
                .findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());

        if (user.isEmpty()) {
            return false;
        }

        //actual login
        this.userSession.login(user.get());
        return true;
    }

    public void logout() {
        userSession.logout();
    }

    public boolean isLoggedIn() {
        return this.userSession.getId() > 0;
    }
}
