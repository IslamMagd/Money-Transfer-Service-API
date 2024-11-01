package com.banquemisr.moneytransactionservice.service;

import com.banquemisr.moneytransactionservice.dto.*;
import com.banquemisr.moneytransactionservice.exception.custom.UserAlreadyExistsException;
import com.banquemisr.moneytransactionservice.model.User;
import com.banquemisr.moneytransactionservice.exception.custom.PasswordResetException;

public interface IAuthenticator {

    /**
     * Checks if the username or email passed are present in the database.
     *
     * @param username The user's username
     * @param email    The user's email
     * @throws UserAlreadyExistsException if the username or email are already present.
     */
    void checkIfUsernameOrEmailExists(String username, String email) throws UserAlreadyExistsException;

    /**
     * Extracts the info from the createUserDTO into a User object
     *
     * @param createUserDTO The user's register info
     * @return user details @{@link User}
     */
    User extractUserDetails(CreateUserDTO createUserDTO);

    /**
     * Register the user into the database.
     *
     * @param createUserDTO The user's register info
     * @return registered user @{@link UserDTO}
     */
    UserDTO register(CreateUserDTO createUserDTO);

    /**
     * log the user into the database.
     *
     * @param loginRequestDTO The user's credentials
     * @return user logged in @{@link LoginResponseDTO}
     */
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    /**
     * Logout the user from the system by blacklisting the token
     *
     * @param token The token to blacklist
     */
    void logout(String token);

    /**
     * Refreshes the token if it's valid
     *
     * @param refreshTokenRequestDTO The refresh token
     */
    LoginResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO);

    /**
     * Resets a user's password using a JWT token to identify the user.
     *
     * @param token            The JWT token sent to the user.
     * @param resetPasswordDto A DTO containing the user's current password and the desired new password.
     * @throws IllegalArgumentException If the token is invalid or the current password does not match the user's existing password.
     * @throws PasswordResetException   If there's an error during the password reset process.
     */
    void resetPassword(String token, ResetPasswordDto resetPasswordDto);
}
