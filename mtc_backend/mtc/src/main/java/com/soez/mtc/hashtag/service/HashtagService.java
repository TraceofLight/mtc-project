package com.soez.mtc.hashtag.service;

import com.soez.mtc.hashtag.dto.HashtagDto;
import com.soez.mtc.hashtag.entity.HashtagEntity;

import java.util.List;

public interface HashtagService {

    List<HashtagDto> readHashtagEntitiesByTagname(String tagname);
}
