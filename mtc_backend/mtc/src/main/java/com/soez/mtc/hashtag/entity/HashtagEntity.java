package com.soez.mtc.hashtag.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hashtag")
@Setter
@Getter
@NoArgsConstructor
public class HashtagEntity {

    @Id
    private String tagname;

    public HashtagEntity(String tagname) {
        this.tagname = tagname;
    }
}
