package com.soez.mtc.user.repository;

import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUserNickname(String userNickname);

    UserEntity findUserEntityByUserNickname(String userNickname);

    UserEntity findUserEntityByUserIndex(Long userIndex);

    UserEntity findUserEntityByUserUid(String UserUid);

    void deleteUserEntityByUserIndex(Long userIndex);
}
