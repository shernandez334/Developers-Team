package org.example.services;

import com.google.common.hash.Hashing;
import org.example.dao.DatabaseFactory;
import org.example.dao.MySqlFactory;
import org.example.dao.UserDao;
import org.example.entities.Admin;
import org.example.entities.Player;
import org.example.entities.User;
import org.example.enums.UserRole;
import org.example.exceptions.ExistingEmailException;
import org.example.exceptions.FormatException;
import org.example.exceptions.MySqlException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;


import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class UserRegistrationServiceTest {

    private UserRegistrationAndLoginService service;
    private UserDao mockUserDao;

    @BeforeEach
    void setUp(){
        mockUserDao = mock(UserDao.class);
        DatabaseFactory mockDatabaseFactory = mock(MySqlFactory.class);

        Mockito.when(mockDatabaseFactory.createUserDao()).thenReturn(mockUserDao);

        service = new UserRegistrationAndLoginService(mockDatabaseFactory);
    }


    @ParameterizedTest
    @ValueSource(strings = {" username","user name", "user  "})
    void validateUserName_ContainsBlankSpace_ThrowsException(String input){
        Exception e = assertThrows(FormatException.class, () -> service.validateUserName(input));
        assertEquals(e.getMessage(), "The username cannot contain blank spaces.");
    }

    @Test
    void validateUserName_LengthLessThan4_ThrowsException(){
        Exception e = assertThrows(FormatException.class, () -> service.validateUserName("PAL"));
        assertEquals(e.getMessage(), "The username must be at least 4 characters long.");
    }

    @Test
    void validateUserName_LengthMoreThan20_ThrowsException(){
        Exception e = assertThrows(FormatException.class, () -> service.validateUserName("myUsernameIsReallyLong"));
        assertEquals(e.getMessage(), "The username cannot have more than 20 characters.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"us€RName","*sern@me", "uSer#~"})
    void validateUserName_ContainsNonAlphaNumChars_ThrowsException(String input){
        Exception e = assertThrows(FormatException.class, () -> service.validateUserName(input));
        assertEquals(e.getMessage(), "The username can contain only alphanumeric characters.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"myUser","saraPerez"})
    void validateUserName_Valid_ThenReturnInput(String input) throws FormatException {
        String response = service.validateUserName(input);
        assertEquals(input, response);
    }

    @ParameterizedTest
    @ValueSource(strings = {"   ","my password", "P A S S"})
    void validatePassword_ContainsBlankSpace_ThrowsException(String input){
        Exception e = assertThrows(FormatException.class, () -> service.validatePasswordAndEncrypt(input));
        assertEquals(e.getMessage(), "The password cannot contain blank spaces.");
    }

    @Test
    void validatePassword_LengthLessThan4_ThrowsException(){
        Exception e = assertThrows(FormatException.class, () -> service.validatePasswordAndEncrypt("pas"));
        assertEquals(e.getMessage(), "The password must be at least 4 characters long.");
    }

    @Test
    void validatePassword_LengthMoreThan16_ThrowsException(){
        Exception e = assertThrows(FormatException.class, () -> service.validatePasswordAndEncrypt("myPasswordIsReallyLong"));
        assertEquals(e.getMessage(), "The password cannot have more than 16 characters.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"password","p@$$WoRd"})
    void validatePasswordAndEncrypt_Valid_ThenReturnInput(String password) throws FormatException {
        String response = service.validatePasswordAndEncrypt(password);
        String encryptedPassword = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        assertEquals(encryptedPassword, response);
    }

    @ParameterizedTest
    @ValueSource(strings = {" email@mail.com","my email@mail.com"})
    void validateEmail_ContainsBlankSpace_ThrowsException(String input){
        Exception e = assertThrows(FormatException.class, () -> service.validateEmail(input));
        assertEquals(e.getMessage(), "Invalid email: contains blank spaces.");
    }

    @Test
    void validateEmail_MissingAtSign_ThrowsException(){
        Exception e = assertThrows(FormatException.class, () -> service.validateEmail("mail.com"));
        assertEquals(e.getMessage(), "Invalid email: missing '@' character.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"@mail.com","jôsépérez@mail.com"})
    void validateEmail_InvalidLocalPart_ThrowsException(String input){
        Exception e = assertThrows(FormatException.class, () -> service.validateEmail(input));
        assertEquals(e.getMessage(), "Invalid email: invalid local-part.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"mail@.com","mail@domain", "mail@domain.", "mail@m@il.com", "mail@dirección.es"})
    void validateEmail_InvalidDomain_ThrowsException(String input){
        Exception e = assertThrows(FormatException.class, () -> service.validateEmail(input));
        assertEquals(e.getMessage(), "Invalid email: invalid domain name.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"mail@mail.com","name@domain.es"})
    void validateEmail_Valid_ThenReturnInput(String input) throws FormatException {
        String response = service.validateEmail(input);
        assertEquals(input, response);
    }

    @Test
    void registerUser_ValidPlayer_ThenReturnUser() throws MySqlException, ExistingEmailException {
        String name = "Player1";
        String password = "12345";
        String email = "player@mail.com";

        Mockito.when(mockUserDao.saveUser(Mockito.any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User result = service.registerUser(name, password, email, UserRole.PLAYER);

        assertInstanceOf(Player.class, result);
        assertEquals(result.getName(), name);
        assertEquals(result.getPassword(), password);
        assertEquals(result.getEmail(), email);
        Mockito.verify(mockUserDao).saveUser(Mockito.any(User.class));
    }

    @Test
    void registerUser_ValidAdmin_ThenReturnUser() throws MySqlException, ExistingEmailException {
        String name = "admin123";
        String password = "pass123";
        String email = "name@mail.com";

        Mockito.when(mockUserDao.saveUser(Mockito.any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User result = service.registerUser(name, password, email, UserRole.ADMIN);

        assertInstanceOf(Admin.class, result);
        assertEquals(result.getName(), name);
        assertEquals(result.getPassword(), password);
        assertEquals(result.getEmail(), email);
        Mockito.verify(mockUserDao).saveUser(Mockito.any(User.class));
    }

}
