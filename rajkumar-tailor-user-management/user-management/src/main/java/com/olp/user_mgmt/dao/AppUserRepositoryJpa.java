package com.olp.user_mgmt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.olp.user_mgmt.entity.AppUser;


@Repository
public interface AppUserRepositoryJpa extends JpaRepository<AppUser, Integer> {

}
