package com.soez.mtc.recent.service;

import com.soez.mtc.recent.entity.RecentViewEntity;

public interface RecentViewService {

    boolean createRecentView(Long userIndex, Long articleIndex);
}
