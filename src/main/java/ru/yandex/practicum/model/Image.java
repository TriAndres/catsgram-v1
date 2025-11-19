package ru.yandex.practicum.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"id"})
public class Image {
    private Long id;
    private long postId;
    private String originalFileName;
    private String filePath;
}