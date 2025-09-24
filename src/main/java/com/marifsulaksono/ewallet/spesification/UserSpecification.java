package com.marifsulaksono.ewallet.spesification;

import org.springframework.data.jpa.domain.Specification;

import com.marifsulaksono.ewallet.entity.User;

public class UserSpecification {

    private UserSpecification() {
        // Private constructor to prevent instantiation
    }

    public static Specification<User> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isEmpty()) {
                return null;
            }
            String likePattern = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                cb.like(cb.lower(root.get("fullName")), likePattern),
                cb.like(cb.lower(root.get("email")), likePattern)
            );
        };
    }

    public static Specification<User> hasRole(String role) {
        return (root, query, cb) ->
            (role == null || role.isEmpty()) ? null : cb.equal(root.get("role"), role);
    }
}
