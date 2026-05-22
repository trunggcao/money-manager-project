package com.example.MoneyManager.service;


import com.example.MoneyManager.dto.CategoryDTO;
import com.example.MoneyManager.entity.Category;
import com.example.MoneyManager.entity.ProfileEntity;
import com.example.MoneyManager.repository.CategoryRepository;
import com.example.MoneyManager.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProfileService profileService;

    private Category toEntity(CategoryDTO categoryDTO, ProfileEntity profile){
        return Category.builder()
                .name(categoryDTO.getName())
                .type(categoryDTO.getType())
                .icon(categoryDTO.getIcon())
                .profile(profile)
                .build();
    }

    private CategoryDTO toDTO(Category category){
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .type(category.getType())
                .icon(category.getIcon())
                .profileId(category.getProfile() != null ? category.getProfile().getId() : null)
                .createAt(category.getCreateAt())
                .updateAt(category.getUpdateAt())
                .build();
    }

    public CategoryDTO saveCategory(CategoryDTO categoryDTO){
        ProfileEntity profile = profileService.getCurrentProfile();

        if (categoryRepository.existsByNameAndProfileId(categoryDTO.getName(), profile.getId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category with this name is already exist");
        }
        Category newCategory = toEntity(categoryDTO,  profile);
        newCategory = categoryRepository.save(newCategory);
        return toDTO(newCategory);
    }

    public List<CategoryDTO> getCategoriesForCurrentUser(){
        ProfileEntity profile = profileService.getCurrentProfile();
        List<Category> categories = categoryRepository.findByProfileId(profile.getId());
        return categories.stream().map(this::toDTO).toList();
    }

    public List<CategoryDTO> getCategoriesByTypeForCurrentUser(String type){
        ProfileEntity profile = profileService.getCurrentProfile();
        List<Category> categories = categoryRepository.findByTypeAndProfileId(type, profile.getId());
        return  categories.stream().map(this::toDTO).toList();
    }

    public CategoryDTO updateCategoriesForCurrentUser(Long CategoryId ,CategoryDTO categoryDTO){
        ProfileEntity profile = profileService.getCurrentProfile();
        Category existingCategory = categoryRepository.findByIdAndProfileId(CategoryId,profile.getId())
                .orElseThrow(() -> new RuntimeException("Category not found or not accessible"));
        existingCategory.setName(categoryDTO.getName());
        existingCategory.setIcon(categoryDTO.getIcon());
        existingCategory.setType(categoryDTO.getType());
        existingCategory = categoryRepository.save(existingCategory);
        return toDTO(existingCategory);

    }

}
