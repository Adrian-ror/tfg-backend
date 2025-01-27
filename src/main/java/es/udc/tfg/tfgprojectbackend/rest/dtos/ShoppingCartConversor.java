package es.udc.tfg.tfgprojectbackend.rest.dtos;

import es.udc.tfg.tfgprojectbackend.model.entities.ProductImage;
import es.udc.tfg.tfgprojectbackend.model.entities.ShoppingCart;
import es.udc.tfg.tfgprojectbackend.model.entities.ShoppingCartItem;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShoppingCartConversor {



    public final static ShoppingCartDto toShoppingCartDto(ShoppingCart cart) {

        List<ShoppingCartItemDto> items =
                cart.getItems().stream()
                        .map(ShoppingCartConversor::toShoppingCartItemDto)
                        .collect(Collectors.toList());

        items.sort(Comparator.comparing(ShoppingCartItemDto::getProductName));

        return new ShoppingCartDto(cart.getId(), items, cart.getTotalQuantity(), cart.getTotalPrice());
    }

    public final static ShoppingCartItemDto toShoppingCartItemDto(ShoppingCartItem item) {

        String mainImage = item.getProduct().getImages().stream()
                .filter(ProductImage::getIsPrimary)
                .map(ProductImage::getImageUrl)
                .findFirst()
                .orElse(null);


        return new ShoppingCartItemDto(item.getProduct().getId(), item.getProduct().getName(),
                item.getProduct().getCategory().getName(), mainImage, item.getProduct().getPrice(), item.getQuantity());
    }

}