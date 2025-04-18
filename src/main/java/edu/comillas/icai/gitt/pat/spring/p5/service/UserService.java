package edu.comillas.icai.gitt.pat.spring.p5.service;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Token;
import edu.comillas.icai.gitt.pat.spring.p5.model.ProfileRequest;
import edu.comillas.icai.gitt.pat.spring.p5.model.ProfileResponse;
import edu.comillas.icai.gitt.pat.spring.p5.model.RegisterRequest;
import edu.comillas.icai.gitt.pat.spring.p5.repository.TokenRepository;
import edu.comillas.icai.gitt.pat.spring.p5.repository.AppUserRepository;
import edu.comillas.icai.gitt.pat.spring.p5.util.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;

/**
 * TODO#6
 * Completa los métodos del servicio para que cumplan con el contrato
 * especificado en el interface UserServiceInterface, utilizando
 * los repositorios y entidades creados anteriormente
 */

// TODO#14 lo pongo aquí para que sea más facil buscarlo luego para corregir

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private Hashing hashing;

    @Override
    public Token login(String email, String password) {
        Optional<AppUser> userOptional = appUserRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return null;
        }

        AppUser user = userOptional.get();
        if (!hashing.compare(user.getPassword(), password)) {
            return null;
        }

        Optional<Token> tokenOptional = tokenRepository.findByAppUser(user);
        if (tokenOptional.isPresent()) {
            return tokenOptional.get();
        }

        Token token = new Token();
        token.setId(UUID.randomUUID().toString());
        token.setAppUser(user);
        user.setToken(token);
        return tokenRepository.save(token);
    }

    @Override
    public AppUser authentication(String tokenId) {
        return tokenRepository.findById(tokenId)
                .map(Token::getAppUser)
                .orElse(null);
    }

    @Override
    public ProfileResponse profile(AppUser appUser) {
        return new ProfileResponse(appUser.getEmail(), appUser.getName(), appUser.getRole());
    }


    @Override
    public ProfileResponse profile(AppUser appUser, ProfileRequest profile) {
        if (StringUtils.hasText(profile.name())) {
            appUser.setName(profile.name());
        }

        if (StringUtils.hasText(profile.password())) {
            appUser.setPassword(hashing.hash(profile.password()));
        }

        AppUser updatedUser = appUserRepository.save(appUser);
        return profile(updatedUser);
    }

    @Override
    public ProfileResponse profile(RegisterRequest register) {
        AppUser user = new AppUser();
        user.setEmail(register.email());
        user.setPassword(hashing.hash(register.password()));
        user.setName(register.name());
        user.setRole(register.role());

        AppUser savedUser = appUserRepository.save(user);
        return profile(savedUser);
    }

    @Override
    public void logout(String tokenId) {
        tokenRepository.deleteById(tokenId);
    }

    @Override
    public void delete(AppUser appUser) {
        tokenRepository.deleteByAppUser(appUser);
        appUserRepository.delete(appUser);
    }

}
