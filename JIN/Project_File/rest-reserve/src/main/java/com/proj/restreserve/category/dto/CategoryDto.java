package com.proj.restreserve.category.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class CategoryDto {
    private String categoryid;
    private String name;
}
