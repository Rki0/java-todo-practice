package com.restfulAPI.restfulAPI.service;

import com.restfulAPI.restfulAPI.dto.TodoDTO;
import com.restfulAPI.restfulAPI.entity.TodoEntity;
import com.restfulAPI.restfulAPI.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public void save(TodoDTO todoDTO) {
        TodoEntity todoEntity = TodoEntity.toSaveEntity(todoDTO);
        todoRepository.save(todoEntity);
    }

    public List<TodoDTO> findAll() {
        List<TodoEntity> todoEntityList = todoRepository.findAll();
        List<TodoDTO> todoDTOList = new ArrayList<>();

        for (TodoEntity todoEntity: todoEntityList) {
            todoDTOList.add(TodoDTO.toTodoDTO(todoEntity));
        }

        return todoDTOList;
    }

    public TodoDTO findById(Long id) {
        // 에러 처리 case 1. if else 문으로 처리하는 방법
//        Optional<TodoEntity> optionalTodoEntity = todoRepository.findById(id);

//        if(optionalTodoEntity.isPresent()){
//            TodoEntity todoEntity = optionalTodoEntity.get();
//            return TodoDTO.toTodoDTO(todoEntity);
//        } else {
//            throw new NoSuchElementException("존재하지 않는 id 입니다.");
//        }

        // 에러 처리 case 2. Optional.orElseThrow()를 사용하는 방법 : findById가 Optional<>을 반환해서 메서드 체이닝이 가능한 것임!
        TodoEntity todoEntity = todoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 id 입니다."));
        return TodoDTO.toTodoDTO(todoEntity);
    }

    public void deleteById(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("존재하지 않는 id 입니다.");
        }
    }

    public TodoDTO updateById(Long id, TodoDTO todoDTO) {
        TodoEntity todoEntity = todoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 id 입니다."));

        if (todoDTO.getTitle() != null) {
            todoEntity.setTitle(todoDTO.getTitle());
        }

        if (todoDTO.getContent() != null) {
            todoEntity.setContent(todoDTO.getContent());
        }

        TodoEntity updatedTodoEntity = todoRepository.save(todoEntity);

        return TodoDTO.toTodoDTO(updatedTodoEntity);
    }

    public Page<TodoDTO> pagination(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3;

        Page<TodoEntity> todoEntities = todoRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        return todoEntities.map((todo) -> new TodoDTO(todo.getId(), todo.getTitle(), todo.getContent(), todo.getCreatedTime()));
    }
}
