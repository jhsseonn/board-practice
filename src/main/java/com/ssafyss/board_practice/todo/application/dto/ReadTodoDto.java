package com.ssafyss.board_practice.todo.application.dto;

import com.ssafyss.board_practice.todo.domain.Todo;

public class ReadTodoDto {
    private Long id;
    private Long userId;
    private String content;
    private boolean completed;
    private boolean deleted;

    public ReadTodoDto(Todo todo) {
        this.id = todo.getId();
        this.userId = todo.getUserId();
        this.content = todo.getContent();
        this.completed = todo.isCompleted();
        this.deleted = todo.isDeleted();
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isDeleted() {
        return deleted;
    }
}