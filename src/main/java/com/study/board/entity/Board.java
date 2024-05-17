package com.study.board.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "username")
    private String username;

    @Column(name = "fileguid")
    private String fileguid;

    @Column(name = "filename")
    private String filename;

    @Column(name = "filepath")
    private String filepath;

    @Column(name = "insertdate")
    private LocalDate insertdate;
}
