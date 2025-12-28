package com.example.blog.exception;

public class ResourseNotFoundException extends RuntimeException{
    public ResourseNotFoundException(String msg){
        super(msg);
    }
}
