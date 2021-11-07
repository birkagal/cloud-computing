package com.birkagal.management.service;

import com.birkagal.management.model.UserBoundary;
import com.birkagal.management.model.UserWithoutPasswordBoundary;

public interface UserService {

    UserWithoutPasswordBoundary store(UserBoundary newUser);

    UserWithoutPasswordBoundary get(String email);

    UserWithoutPasswordBoundary login(String email, String password);

    void update(String email, UserBoundary updatedUser);

    void delete(String email);

    void deleteAll();

    UserWithoutPasswordBoundary[] getAllFiltered(String criteriaType, String criteriaValue, int size, int page,
                                                 String sortBy, String sortOrder);
}
