package org.solar.auth.entity.repo;

import org.solar.auth.entity.IUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepo extends JpaRepository<IUser, Long> {

    IUser findByUsername(String username);
}
