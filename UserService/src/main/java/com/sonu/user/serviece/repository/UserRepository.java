package com.sonu.user.serviece.repository;

import com.sonu.user.serviece.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
