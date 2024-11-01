package com.banquemisr.moneytransactionservice.service;

import com.banquemisr.moneytransactionservice.dto.*;
import com.banquemisr.moneytransactionservice.exception.custom.AccountNotFoundException;
import com.banquemisr.moneytransactionservice.exception.custom.UserNotFoundException;
import com.banquemisr.moneytransactionservice.model.User;

public interface IUser {

    /**
     * Retrieves the user with id passed if exists
     *
     * @param userId The user's ID
     * @return The user object @{@link User}
     * @throws UserNotFoundException If user doesn't exist with passed id
     * */
    User getUserIfExistsById(Long userId) throws UserNotFoundException;

    /**
     * Retrieves the user with email passed if exists
     *
     * @param email The user's email
     * @return The user object @{@link User}
     * @throws UserNotFoundException If user doesn't exist with passed email
     * */
    User getUserIfExistsByEmail(String email) throws UserNotFoundException;

    /**
     * update the user based on the jwt token
     * @param token the auth token -> JWT
     * @param updateUserDTO the update info of the user
     * @throws UserNotFoundException If user doesn't exist with token
     */
    void updateUser(String token, UpdateUserDTO updateUserDTO) throws UserNotFoundException;

    /**
     * Retrieves the user form the jwt token if exists
     * @param token the auth token -> JWT
     * @return the user {@link User} object as userDto {@link UserResponseDTO}
     * @throws UserNotFoundException If user doesn't exist with token
     * @throws AccountNotFoundException If user bank account doesn't exist
     */
    UserResponseDTO getUser(String token) throws UserNotFoundException, AccountNotFoundException;

    /**
     * Gets the currency equivalent rate in EGP
     *
     * @param currencyRateDTO The currency & amount
     * @return The equivalent in EGP
     * */
    Double getRateToEGP(CurrencyRateDTO currencyRateDTO);

    /**
     * Gets the currency equivalent rate from EGP
     *
     * @param currencyRateDTO The currency & amount
     * @return The equivalent from EGP
     * */
    Double getRateFromEGP(CurrencyRateDTO currencyRateDTO);

    /**
     * Gets the currency equivalent rate
     *
     * @param currencyToFromRateDTO The fromCurrency and toCurrency & amount
     * @return The equivalent rate
     * */
    Double getRate(CurrencyToFromRateDTO currencyToFromRateDTO);

    /**
     * Converts a given amount from one currency to another.
     *
     * @param from the currency code of the amount to be converted
     * @param to the currency code of the target currency
     * @param givenAmount the amount to be converted
     * @return a {@link RateResponseDTO} object containing the conversion details
     */
    RateResponseDTO convertCurrency(String from, String to, double givenAmount);
}
