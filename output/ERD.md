|테이블명| | | |article| | | | | | |
|-|-|-|-|-|-|-|-|-|-|-|
|PRIMARY KEY| | | |article_index| | | | | | |
|FOREIGN KEY| | | || | | | | | |
|INDEX| | | || | | | | | |
|UNIQUE INDEX| | | || | | | | | |
|NO|PK|AI|FK|NULL|논리명|컬럼명|TYPE|DEFAULT|설명|참조 테이블|
|1|Y|Y||||article_index|bigint|| | |
|2||||||article_user_index|bigint|| | |
|3||||||article_title|varchar(100)|| | |
|4||||||article_picture|varchar(100)|| | |
|5||||||article_hit|int|'0'| | |
|6||||||article_regist_time|datetime|| | |
|7||||||article_visible|tinyint|| | |

|테이블명| | | |block| | | | | | |
|-|-|-|-|-|-|-|-|-|-|-|
|PRIMARY KEY| | | |block_user_index, block_target_index| | | | | | |
|FOREIGN KEY| | | |block_user_index, block_target_index| | | | | | |
|INDEX| | | |block_target_index(block_target_index)| | | | | | |
|UNIQUE INDEX| | | || | | | | | |
|NO|PK|AI|FK|NULL|논리명|컬럼명|TYPE|DEFAULT|설명|참조 테이블|
|1|Y||Y|||block_user_index|bigint|| |user|
|2|Y||Y|||block_target_index|bigint|| |user|

|테이블명| | | |comment| | | | | | |
|-|-|-|-|-|-|-|-|-|-|-|
|PRIMARY KEY| | | |comment_index| | | | | | |
|FOREIGN KEY| | | |comment_user_index, comment_article_index| | | | | | |
|INDEX| | | |comment_article_index(comment_article_index), comment_user_index(comment_user_index)| | | | | | |
|UNIQUE INDEX| | | || | | | | | |
|NO|PK|AI|FK|NULL|논리명|컬럼명|TYPE|DEFAULT|설명|참조 테이블|
|1|Y|Y||||comment_index|bigint|| | |
|2|||Y|||comment_user_index|bigint|| |user|
|3|||Y|||comment_article_index|bigint|| |article|
|4||||||comment_content|varchar(1000)|| | |
|5||||Y||comment_good|int|NULL| | |
|6||||Y||comment_bad|int|NULL| | |
|7||||Y||comment_regist_time|datetime|CURRENT_TIMESTAMP| | |

|테이블명| | | |comment_like| | | | | | |
|-|-|-|-|-|-|-|-|-|-|-|
|PRIMARY KEY| | | |comment_like_index| | | | | | |
|FOREIGN KEY| | | |comment_like_user_index, comment_like_comment_index| | | | | | |
|INDEX| | | |comment_like_comment_index(comment_like_comment_index), comment_like_user_index(comment_like_user_index)| | | | | | |
|UNIQUE INDEX| | | || | | | | | |
|NO|PK|AI|FK|NULL|논리명|컬럼명|TYPE|DEFAULT|설명|참조 테이블|
|1|Y|Y||||comment_like_index|bigint|| | |
|2|||Y|||comment_like_user_index|bigint|| |user|
|3|||Y|||comment_like_comment_index|bigint|| |comment|
|4||||||commnet_like_valuate|tinyint(1)|| | |

|테이블명| | | |evaluate| | | | | | |
|-|-|-|-|-|-|-|-|-|-|-|
|PRIMARY KEY| | | |evaluate_article_index, evaluate_user_index| | | | | | |
|FOREIGN KEY| | | |evaluate_article_index, evaluate_user_index| | | | | | |
|INDEX| | | |fk_evaluate_user1_idx(evaluate_user_index)| | | | | | |
|UNIQUE INDEX| | | || | | | | | |
|NO|PK|AI|FK|NULL|논리명|컬럼명|TYPE|DEFAULT|설명|참조 테이블|
|1|Y||Y|||evaluate_article_index|bigint|| |article|
|2|Y||Y|||evaluate_user_index|bigint|| |user|
|3||||||evaluate_value|char(1)|| | |

|테이블명| | | |follow| | | | | | |
|-|-|-|-|-|-|-|-|-|-|-|
|PRIMARY KEY| | | |follow_user_index, follow_target_index| | | | | | |
|FOREIGN KEY| | | |follow_user_index, follow_target_index| | | | | | |
|INDEX| | | |follow_target_index(follow_target_index)| | | | | | |
|UNIQUE INDEX| | | || | | | | | |
|NO|PK|AI|FK|NULL|논리명|컬럼명|TYPE|DEFAULT|설명|참조 테이블|
|1|Y||Y|||follow_user_index|bigint|| |user|
|2|Y||Y|||follow_target_index|bigint|| |user|

|테이블명| | | |hashtag| | | | | | |
|-|-|-|-|-|-|-|-|-|-|-|
|PRIMARY KEY| | | |tagname| | | | | | |
|FOREIGN KEY| | | || | | | | | |
|INDEX| | | || | | | | | |
|UNIQUE INDEX| | | || | | | | | |
|NO|PK|AI|FK|NULL|논리명|컬럼명|TYPE|DEFAULT|설명|참조 테이블|
|1|Y|||||tagname|varchar(20)|| | |

|테이블명| | | |hashtagging| | | | | | |
|-|-|-|-|-|-|-|-|-|-|-|
|PRIMARY KEY| | | |hashtagging_index| | | | | | |
|FOREIGN KEY| | | |hashtagging_article_index, hashtagging_tagname| | | | | | |
|INDEX| | | |fk_hashtagging_article1_idx(hashtagging_article_index), fk_hashtagging_hashtag1_idx(hashtagging_tagname)| | | | | | |
|UNIQUE INDEX| | | || | | | | | |
|NO|PK|AI|FK|NULL|논리명|컬럼명|TYPE|DEFAULT|설명|참조 테이블|
|1|Y|||||hashtagging_index|bigint|| | |
|2|||Y|||hashtagging_article_index|bigint|| |article|
|3|||Y|||hashtagging_tagname|varchar(20)|| |hashtag|

|테이블명| | | |recent_view| | | | | | |
|-|-|-|-|-|-|-|-|-|-|-|
|PRIMARY KEY| | | |recent_view_user_index, recent_view_article_index| | | | | | |
|FOREIGN KEY| | | |recent_view_user_index, recent_view_article_index| | | | | | |
|INDEX| | | |fk_recent_view_article1_idx(recent_view_article_index)| | | | | | |
|UNIQUE INDEX| | | || | | | | | |
|NO|PK|AI|FK|NULL|논리명|컬럼명|TYPE|DEFAULT|설명|참조 테이블|
|1|Y||Y|||recent_view_user_index|bigint|| |user|
|2|Y||Y|||recent_view_article_index|bigint|| |article|
|3||||Y||recent_view_time|datetime|NULL| | |

|테이블명| | | |reply| | | | | | |
|-|-|-|-|-|-|-|-|-|-|-|
|PRIMARY KEY| | | |reply_index| | | | | | |
|FOREIGN KEY| | | |reply_user_index, reply_comment_index| | | | | | |
|INDEX| | | |reply_comment_index(reply_comment_index), reply_user_index(reply_user_index)| | | | | | |
|UNIQUE INDEX| | | || | | | | | |
|NO|PK|AI|FK|NULL|논리명|컬럼명|TYPE|DEFAULT|설명|참조 테이블|
|1|Y|Y||||reply_index|bigint|| | |
|2|||Y|||reply_user_index|bigint|| |user|
|3|||Y|||reply_comment_index|bigint|| |comment|
|4||||||reply_content|varchar(1000)|| | |
|5||||Y||reply_good|int|NULL| | |
|6||||Y||reply_bad|int|NULL| | |
|7||||Y||reply_regist_time|datetime|CURRENT_TIMESTAMP| | |

|테이블명| | | |reply_like| | | | | | |
|-|-|-|-|-|-|-|-|-|-|-|
|PRIMARY KEY| | | |reply_like_index| | | | | | |
|FOREIGN KEY| | | |reply_like_user_index, reply_like_reply_index| | | | | | |
|INDEX| | | |reply_like_reply_index(reply_like_reply_index), reply_like_user_index(reply_like_user_index)| | | | | | |
|UNIQUE INDEX| | | || | | | | | |
|NO|PK|AI|FK|NULL|논리명|컬럼명|TYPE|DEFAULT|설명|참조 테이블|
|1|Y|Y||||reply_like_index|bigint|| | |
|2|||Y|||reply_like_user_index|bigint|| |user|
|3|||Y|||reply_like_reply_index|bigint|| |reply|
|4||||||reply_like_valuate|tinyint(1)|| | |

|테이블명| | | |report_article| | | | | | |
|-|-|-|-|-|-|-|-|-|-|-|
|PRIMARY KEY| | | |report_article_article_index, report_article_user_index| | | | | | |
|FOREIGN KEY| | | |report_article_article_index, report_article_user_index| | | | | | |
|INDEX| | | |fk_report_article_user1_idx(report_article_user_index)| | | | | | |
|UNIQUE INDEX| | | || | | | | | |
|NO|PK|AI|FK|NULL|논리명|컬럼명|TYPE|DEFAULT|설명|참조 테이블|
|1|Y||Y|||report_article_article_index|bigint|| |article|
|2|Y||Y|||report_article_user_index|bigint|| |user|
|3||||Y||report_article_content|varchar(200)|NULL| | |

|테이블명| | | |report_comment| | | | | | |
|-|-|-|-|-|-|-|-|-|-|-|
|PRIMARY KEY| | | |report_comment_comment_index, report_comment_user_index| | | | | | |
|FOREIGN KEY| | | |report_comment_comment_index, report_comment_user_index| | | | | | |
|INDEX| | | |fk_report_comment_user1_idx(report_comment_user_index)| | | | | | |
|UNIQUE INDEX| | | || | | | | | |
|NO|PK|AI|FK|NULL|논리명|컬럼명|TYPE|DEFAULT|설명|참조 테이블|
|1|Y||Y|||report_comment_comment_index|bigint|| |comment|
|2|Y||Y|||report_comment_user_index|bigint|| |user|
|3||||Y||report_comment_content|varchar(200)|NULL| | |

|테이블명| | | |report_reply| | | | | | |
|-|-|-|-|-|-|-|-|-|-|-|
|PRIMARY KEY| | | |report_reply_reply_index, report_reply_user_index| | | | | | |
|FOREIGN KEY| | | |report_reply_reply_index, report_reply_user_index| | | | | | |
|INDEX| | | |fk_report_reply_user1_idx(report_reply_user_index)| | | | | | |
|UNIQUE INDEX| | | || | | | | | |
|NO|PK|AI|FK|NULL|논리명|컬럼명|TYPE|DEFAULT|설명|참조 테이블|
|1|Y||Y|||report_reply_reply_index|bigint|| |reply|
|2|Y||Y|||report_reply_user_index|bigint|| |user|
|3||||Y||report_reply_content|varchar(200)|NULL| | |

|테이블명| | | |user| | | | | | |
|-|-|-|-|-|-|-|-|-|-|-|
|PRIMARY KEY| | | |user_index| | | | | | |
|FOREIGN KEY| | | || | | | | | |
|INDEX| | | || | | | | | |
|UNIQUE INDEX| | | |(user_email)| | | | | | |
|NO|PK|AI|FK|NULL|논리명|컬럼명|TYPE|DEFAULT|설명|참조 테이블|
|1|Y|Y||||user_index|bigint|| | |
|2||||||user_email|varchar(100)|| | |
|3||||||user_password|varchar(100)|| | |
|4||||||user_nickname|varchar(100)|| | |
|5||||||user_name|varchar(100)|| | |
|6||||||user_phonenumber|varchar(100)|| | |
|7||||||user_birthday|date|| | |
|8||||||user_reported|int|| | |
|9||||Y||user_picture|varchar(100)|NULL| | |
|10||||Y||user_picture_src|varchar(100)|NULL| | |
|11||||||user_token|varchar(100)|| | |
