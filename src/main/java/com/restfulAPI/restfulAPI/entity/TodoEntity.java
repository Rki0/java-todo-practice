package com.restfulAPI.restfulAPI.entity;

import com.restfulAPI.restfulAPI.dto.TodoDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "todo_table")
public class TodoEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 100)
    private String content;

    public static TodoEntity toSaveEntity(TodoDTO todoDTO) {
        TodoEntity todoEntity = new TodoEntity();

        todoEntity.setId(todoDTO.getId());
        todoEntity.setTitle(todoDTO.getTitle());
        todoEntity.setContent(todoDTO.getContent());

        return todoEntity;
    }
}
