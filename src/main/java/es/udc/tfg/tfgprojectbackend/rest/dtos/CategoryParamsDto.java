package es.udc.tfg.tfgprojectbackend.rest.dtos;

public class CategoryParamsDto {

    private String name;

    public CategoryParamsDto() {}

    public CategoryParamsDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
