package com.restfulAPI.restfulAPI.dto;

import com.restfulAPI.restfulAPI.entity.TodoEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TodoDTO(Long id, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static TodoDTO toTodoDTO(TodoEntity todoEntity) {
        TodoDTO todoDTO = new TodoDTO();

        todoDTO.setId(todoEntity.getId());
        todoDTO.setTitle(todoEntity.getTitle());
        todoDTO.setContent(todoEntity.getContent());
        todoDTO.setCreatedAt(todoEntity.getCreatedTime());
        todoDTO.setUpdatedAt(todoEntity.getUpdatedTime());

        return todoDTO;
    }
}
