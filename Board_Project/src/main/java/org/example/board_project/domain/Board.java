package org.example.board_project.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Board {
    @Id
    private Long id;
    private String name;
    private String title;
    private String password;
    private String content;  // 본문을 나타내는 필드
    private LocalDateTime createdAt;  // 등록일
    private LocalDateTime updatedAt;  // 수정일

}
