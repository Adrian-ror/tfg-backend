package es.udc.tfg.tfgprojectbackend.rest.dtos;

import es.udc.tfg.tfgprojectbackend.model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class WishListConversor {


    public final static  WishListDto toWishListDto(WishList list) {
        List<WishListItemDto> items = list.getItems().stream()
                .map(WishListConversor::toWishListItemDto)
                .sorted(Comparator.comparing(WishListItemDto::getProductName))
                .collect(Collectors.toList());

        return new WishListDto(list.getId(), items);
    }

    public final static  WishListItemDto toWishListItemDto(WishListItem item) {

        Product product = item.getProduct();

        String mainImage = product.getImages().stream()
                .filter(ProductImage::getIsPrimary)
                .map(ProductImage::getImageUrl)
                .findFirst()
                .orElse(null);


        return new WishListItemDto(
                item.getId(),
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory().getId(),
                toMillis(item.getAddedDate()),
                mainImage,
                product.getPrice(),
                product.getRating(),
                product.getReviews().size()
        );
    }

    private static long toMillis(LocalDateTime date){
        return date.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
    }

}