package com.rohitvpatil0810.v2data.modules.users.repository;

import com.rohitvpatil0810.v2data.modules.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
