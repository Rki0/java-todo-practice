package com.restfulAPI.restfulAPI.controller;

import com.restfulAPI.restfulAPI.dto.TodoDTO;
import com.restfulAPI.restfulAPI.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/save")
    public ResponseEntity<TodoDTO> save(@RequestBody TodoDTO todoDTO) {
        System.out.println("TodoDTO = " + todoDTO);
        todoService.save(todoDTO);

        // RestController이기 때문에 자동으로 JSON으로 반환된다.
//        return todoDTO;

        // HTTP 헤더 설정도 같이 하고 싶다면 이렇게.
        return new ResponseEntity<>(todoDTO, HttpStatus.OK);
    }

    @GetMapping("")
    public List<TodoDTO> findAll() {
        return todoService.findAll();
    }

    @GetMapping("/{id}")
    public TodoDTO findById(@PathVariable("id") Long id) {
        return todoService.findById(id);
    }

    @GetMapping("/pagination")
    public ResponseEntity pagination(@PageableDefault(page = 1) Pageable pageable) {
        // 보고자 하는 페이지의 내용들을 얻는다.
        Page<TodoDTO> pagination = todoService.pagination(pageable);

        // 보고자 하는 페이지와 주변 페이지의 상태를 정한다.
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), pagination.getTotalPages());

        // Map<K, V> : Value가 Object인데 어떻게 int 형이 들어가느냐? Autoboxing이라는 JAVA의 특징 때문!
        Map<String, Object> response = new HashMap<>();
        response.put("pagination", pagination);
        response.put("startPage", startPage);
        response.put("endPage", endPage);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        try {
            todoService.deleteById(id);
            return new ResponseEntity<>("Todo가 삭제되었습니다.", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("해당 Todo를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateById(@PathVariable("id") Long id, @RequestBody TodoDTO todoDTO) {
        try {
            TodoDTO updatedTodoDTO = todoService.updateById(id, todoDTO);
            return new ResponseEntity<>("Todo가 업데이트 되었습니다.", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("해당 Todo를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
    }
}
