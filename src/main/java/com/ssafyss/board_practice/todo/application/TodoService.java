package com.ssafyss.board_practice.todo.application;

import com.ssafyss.board_practice.todo.application.dto.CreateTodoDto;
import com.ssafyss.board_practice.todo.application.dto.ReadTodoDetailDto;
import com.ssafyss.board_practice.todo.application.dto.ReadTodoDto;
import com.ssafyss.board_practice.todo.application.exception.NotFoundTodoException;
import com.ssafyss.board_practice.todo.domain.Todo;
import com.ssafyss.board_practice.todo.infrastructure.repository.TodoRepository;
import com.ssafyss.board_practice.user.application.exception.NotFoundUserException;
import com.ssafyss.board_practice.user.domain.User;
import com.ssafyss.board_practice.user.infrastructure.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ReadTodoDto> readAllTodos() {
        final List<Todo> readTodos = todoRepository.findAllByDeletedFalse();
        return readTodos.stream()
                        .map(ReadTodoDto::of)
                        .toList();
    }

    public ReadTodoDetailDto createTodo(final CreateTodoDto createTodoDto) {
        final User user = userRepository.findById(createTodoDto.userId())
                                        .orElseThrow(NotFoundUserException::new);
        final Long todoId = saveTodo(user, createTodoDto.content());
        final Todo createdTodo = todoRepository.findById(todoId)
                                               .orElseThrow(NotFoundTodoException::new);
        return ReadTodoDetailDto.from(createdTodo);
    }

    private Long saveTodo(final User user, final String content) {
        final Todo todo = Todo.builder()
                              .user(user)
                              .content(content)
                              .build();
        final Todo savedTodo = todoRepository.save(todo);
        return savedTodo.getId();
    }
}
