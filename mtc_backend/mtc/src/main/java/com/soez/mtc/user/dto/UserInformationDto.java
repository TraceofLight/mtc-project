package com.soez.mtc.user.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInformationDto {
    private Long userIndex;
    private String userUid;
    private String userNickname;
    private String userProfilePictureSource;
}
