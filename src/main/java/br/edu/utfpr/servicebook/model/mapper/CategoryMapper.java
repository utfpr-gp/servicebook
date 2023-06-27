package br.edu.utfpr.servicebook.model.mapper;

import br.edu.utfpr.servicebook.model.dto.CategoryDTO;
import br.edu.utfpr.servicebook.model.entity.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class CategoryMapper {
    @Autowired
    private ModelMapper mapper;

    public CategoryDTO toDto(Category entity){
        CategoryDTO dto = mapper.map(entity, CategoryDTO.class);
        return dto;
    }

    public Category toEntity(CategoryDTO dto){
        Category entity = mapper.map(dto, Category.class);
        return entity;
    }
}
