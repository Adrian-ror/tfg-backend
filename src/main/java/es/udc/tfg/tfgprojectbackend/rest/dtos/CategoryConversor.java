package es.udc.tfg.tfgprojectbackend.rest.dtos;

import es.udc.tfg.tfgprojectbackend.model.entities.Category;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CategoryConversor {

    private CategoryConversor() {}

    /**
     * Converts a Category entity to a CategoryDto.
     *
     * @param category The Category entity to convert.
     * @param processedIds Set of processed category IDs to avoid cycles.
     * @return The corresponding CategoryDto, or null if already processed.
     */
    public static CategoryDto toCategoryDto(Category category, Set<Long> processedIds) {
        if (!processedIds.add(category.getId())) {
            return null; // Avoid processing the same category again
        }

        List<CategoryDto> subcategoryDtos = category.getSubcategories() != null
                ? category.getSubcategories().stream()
                .map(subcategory -> new CategoryDto(
                        subcategory.getId(),
                        subcategory.getName(),
                        null,
                        null
                ))
                .collect(Collectors.toList())
                : List.of(); // Use an empty list instead of null

        CategoryDto parentCategoryDto = category.getParentCategory() != null
                ? new CategoryDto(
                category.getParentCategory().getId(),
                category.getParentCategory().getName(),
                null,
                null
        )
                : null;

        return new CategoryDto(
                category.getId(),
                category.getName(),
                parentCategoryDto,
                subcategoryDtos
        );
    }

    /**
     * Converts a list of Category entities to a list of CategoryDtos.
     *
     * @param categories The list of Category entities to convert.
     * @return A list of corresponding CategoryDtos.
     */
    public static List<CategoryDto> toCategoryDtos(List<Category> categories) {
        Set<Long> processedIds = new HashSet<>();
        return categories.stream()
                .map(category -> toCategoryDto(category, processedIds))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
