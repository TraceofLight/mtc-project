package com.soez.mtc.hashtag.service;

import com.soez.mtc.hashtag.dto.HashtagDto;
import com.soez.mtc.hashtag.entity.HashtagEntity;
import com.soez.mtc.hashtag.repository.HashtagJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagJpaRepository hashtagJpaRepository;

    /**
     * 인자로 받은 해시태그를 포함하는 해시태그 목록을 반환한다.
     * @param tagname
     * @return List<HashtagDto>
     */
    @Override
    public List<HashtagDto> readHashtagEntitiesByTagname(String tagname) {
        List<HashtagDto> hashtagDtoList = new ArrayList<>();

        if(!tagname.isBlank()) {
            List<HashtagEntity> hashtagEntities = hashtagJpaRepository.findTop10ByTagnameContains(tagname);
            for (HashtagEntity hashtagEntity : hashtagEntities) {
                hashtagDtoList.add(new HashtagDto(hashtagEntity.getTagname()));
            }
        }
        return hashtagDtoList;
    }
}
