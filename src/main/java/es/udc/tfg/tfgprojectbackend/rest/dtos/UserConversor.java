package es.udc.tfg.tfgprojectbackend.rest.dtos;

import es.udc.tfg.tfgprojectbackend.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static es.udc.tfg.tfgprojectbackend.rest.dtos.ShoppingCartConversor.toShoppingCartDto;
import static es.udc.tfg.tfgprojectbackend.rest.dtos.WishListConversor.toWishListDto;


/**
 * A class to convert between User and UserDto objects.
 */
@Component
public class UserConversor {


	/**
	 * Converts a User object to a UserDto object.
	 *
	 * @param user the User object to convert.
	 * @return the UserDto object.
	 */
	public final static  UserDto toUserDto(User user)  {
		return new UserDto(user.getId(), user.getUserName(), user.getFirstName(), user.getLastName(),
				user.getEmail(), user.getPhoneNumber(), user.getImage(), user.getRole().toString(), user.getStatus().toString());
	}

	/**
	 * Converts a UserDto object to a User object.
	 *
	 * @param userDto the UserDto object to convert.
	 * @return the User object.
	 */
	public  final static  User toUser(UserDto userDto) {
		return new User(userDto.getUserName(), userDto.getPassword(), userDto.getFirstName(), userDto.getLastName(),
				userDto.getEmail(), userDto.getPhoneNumber(), userDto.getImage(), User.RoleType.valueOf(userDto.getRole()), userDto.getStatus() != null ? User.StatusType.valueOf(userDto.getStatus()) : null);
	}

	/**
	 * Converts a list of User objects to a list of UserDto objects.
	 *
	 * @param users the list of User objects to convert.
	 * @return the list of UserDto objects.
	 */
	public final static List<UserDto> toUserDtos(List<User> users) {
		return users.stream()
				.map(UserConversor::toUserDto)
				.collect(Collectors.toList());
	}

	/**
	 * Converts a User object to an AuthenticatedUserDto object.
	 *
	 * @param serviceToken the service token.
	 * @param user the User object to convert.
	 * @return the AuthenticatedUserDto object.
	 */
	public final static AuthenticatedUserDto toAuthenticatedUserDto(String serviceToken, User user)  {

		return new AuthenticatedUserDto(
				serviceToken,
				toUserDto(user),
				user.getImage() ,
				user.getShoppingCart() != null ? toShoppingCartDto(user.getShoppingCart()) : null,
				user.getWishList() != null ? toWishListDto(user.getWishList()) : null );

	}

}
