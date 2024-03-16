package com.proj.restreserve.category;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class CategoryDto {
    private String categoryid;
    private String name;
}
