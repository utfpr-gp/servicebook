package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Category;
import br.edu.utfpr.servicebook.model.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category entity){ return categoryRepository.save(entity); }

    public List<Category> findAll() { return categoryRepository.findAll(); }

    public Page<Category> findAll(PageRequest pageRequest) { return this.categoryRepository.findAll(pageRequest); }

    public Optional<Category> findByName(String name){
        return this.categoryRepository.findByName(name);
    }

    public Optional<Category> findById(Long id) {
        return this.categoryRepository.findById(id);
    }

    public void delete(Long id) {
        this.categoryRepository.deleteById(id);
    }

}
