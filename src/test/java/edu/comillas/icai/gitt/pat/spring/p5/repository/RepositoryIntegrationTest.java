package edu.comillas.icai.gitt.pat.spring.p5.repository;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Token;
import edu.comillas.icai.gitt.pat.spring.p5.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class RepositoryIntegrationTest {

    @Autowired TokenRepository tokenRepository;
    @Autowired AppUserRepository appUserRepository;

    /**
     * TODO#9
     * Completa este test de integración para que verifique
     * que los repositorios TokenRepository y AppUserRepository guardan
     * los datos correctamente, y las consultas por AppToken y por email
     * definidas respectivamente en ellos retornan el token y usuario guardados.
     */

    @Test
    void saveTest() {
        // Given ...
        AppUser user = new AppUser();
        user.setName("Test User");
        user.setEmail("test@email.com");
        user.setPassword("password123");
        user.setRole(Role.USER);

        AppUser savedUser = appUserRepository.save(user);

        Token token = new Token();
        token.setId(java.util.UUID.randomUUID().toString()); // ID aleatorio
        token.setAppUser(savedUser);
        tokenRepository.save(token);

        // When ...
        AppUser fetchedUser = appUserRepository.findByEmail("test@email.com").orElse(null);
        Token fetchedToken = tokenRepository.findByAppUser(savedUser).orElse(null);

        // Then ...
        Assertions.assertNotNull(fetchedUser);
        assertEquals("Test User", fetchedUser.getName());
        assertEquals("test@email.com", fetchedUser.getEmail());
        Assertions.assertNotNull(fetchedToken);
        assertEquals(fetchedUser.getId(), fetchedToken.getAppUser().getId());
    }


    /**
     * TODO#10
     * Completa este test de integración para que verifique que
     * cuando se borra un usuario, automáticamente se borran sus tokens asociados.
     */
    @Test
    void deleteCascadeTest() {
        AppUser user = new AppUser();
        user.setName("User");
        user.setEmail("user@email.com");
        user.setPassword("Password123");
        user.setRole(Role.USER);

        Token token = new Token();
        token.setAppUser(user);
        user.setToken(token);

        appUserRepository.save(user);
        appUserRepository.delete(user);

        assertEquals(0, tokenRepository.count());
    }





}
