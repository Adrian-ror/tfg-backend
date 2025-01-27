package es.udc.tfg.tfgprojectbackend.rest.dtos;

import java.util.List;

/**
 * Data transfer object for a wishlist.
 */
public class WishListDto {

    private Long id;
    private List<WishListItemDto> items;

    public WishListDto() {}

    public WishListDto(Long wishListId, List<WishListItemDto> items) {
        this.id = wishListId;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<WishListItemDto> getItems() {
        return items;
    }

    public void setItems(List<WishListItemDto> items) {
        this.items = items;
    }

}
