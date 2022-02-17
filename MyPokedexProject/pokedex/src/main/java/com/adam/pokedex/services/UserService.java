package com.adam.pokedex.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.adam.pokedex.models.*;
import com.adam.pokedex.repositories.*;

@Service
public class UserService {
	private final UserRepository userRepo;
	private final PokemonRepository pokeRepo;
	
	public UserService(UserRepository userRepo,PokemonRepository pokeRepo) {
        this.userRepo = userRepo;
        this.pokeRepo = pokeRepo;
    }
	
	// Register user and hash their password
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepo.save(user);
    }
    
 // Find user by email
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
    
    // Find user by id
    public User findUserById(Long id) {
    	Optional<User> u = userRepo.findById(id);
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
    }
    
    // Authenticate user
    // returns False is user doesn't exist OR if password doesn't match
    public boolean authenticateUser(String email, String password) {
        // first find the user by email
        User user = userRepo.findByEmail(email);
        // if we can't find it by email, return false
        if(user == null) {
            return false;
        } else {
            // if the passwords match, return true, else, return false
            if(BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
}
