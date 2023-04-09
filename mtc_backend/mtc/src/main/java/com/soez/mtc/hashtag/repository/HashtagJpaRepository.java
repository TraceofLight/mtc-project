package com.soez.mtc.hashtag.repository;

import com.soez.mtc.hashtag.entity.HashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HashtagJpaRepository extends JpaRepository<HashtagEntity, String> {

    @Override
    <S extends HashtagEntity> S save(S entity);

    HashtagEntity findHashtagEntityByTagname(String tagname);

    @Query(value = "select h from HashtagEntity h where h.tagname like %:keyword%")
    List<HashtagEntity> findHashtagEntitiesByTagname(@Param("keyword") String keyword);

    List<HashtagEntity> findTop10ByTagnameContains(String keyword);
}
