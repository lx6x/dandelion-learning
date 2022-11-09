package org.dandelion.jpa.dao;

import org.dandelion.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/3/2 17:52
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
