package es.udc.tfg.tfgprojectbackend.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data transfer object for authenticated user.
 *
 * @see UserDto
 */
public class AuthenticatedUserDto {


	private String serviceToken;
	private UserDto userDto;
	private String image;
	private ShoppingCartDto shoppingCartDto;
	private WishListDto wishListDto;

	public AuthenticatedUserDto() {}

	public AuthenticatedUserDto(String serviceToken, UserDto userDto, String image, ShoppingCartDto shoppingCartDto, WishListDto wishListDto ) {

		this.serviceToken = serviceToken;
		this.userDto = userDto;
		this.image = image;
		this.shoppingCartDto = shoppingCartDto;
		this.wishListDto = wishListDto;

	}

	public String getServiceToken() {
		return serviceToken;
	}

	public void setServiceToken(String serviceToken) {
		this.serviceToken = serviceToken;
	}

	@JsonProperty("user")
	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	@JsonProperty("image")
	public String getImage() {
		return image;
	}

	public void setImage (String image) {
		this.image = image;
	}

	@JsonProperty("shoppingCart")
	public ShoppingCartDto getShoppingCartDto() {
		return shoppingCartDto;
	}

	public void setShoppingCartDto(ShoppingCartDto shoppingCartDto) {
		this.shoppingCartDto = shoppingCartDto;
	}

	@JsonProperty("wishList")
	public WishListDto getWishListDto() {
		return wishListDto;
	}

	public void setWishListDto(WishListDto wishListDto) {
		this.wishListDto = wishListDto;
	}
}
