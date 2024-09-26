package com.restfulAPI.restfulAPI.repository;

import com.restfulAPI.restfulAPI.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
}
