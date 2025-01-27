package es.udc.tfg.tfgprojectbackend.model.services;

import es.udc.tfg.tfgprojectbackend.model.entities.*;
import es.udc.tfg.tfgprojectbackend.model.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static es.udc.tfg.tfgprojectbackend.model.entities.User.StatusType.ACTIVE;
import static es.udc.tfg.tfgprojectbackend.model.entities.User.StatusType.BANNED;

/**
 * Implementation of the UserService interface.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private PermissionChecker permissionChecker;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    @Autowired
    private WishListDao wishListDao;

    @Override
    public void signUp(User user) throws DuplicateInstanceException {

        if (userDao.existsByUserName(user.getUserName())) {
            throw new DuplicateInstanceException("project.entities.user", user.getUserName());
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(ACTIVE);
        user.setShoppingCart(new ShoppingCart(user));
        user.setWishList(new WishList(user));

        userDao.save(user);
        shoppingCartDao.save(user.getShoppingCart());
        wishListDao.save(user.getWishList());
    }

    @Override
    @Transactional(readOnly = true)
    public User login(String userName, String password) throws IncorrectLoginException, UserBannedException {

        User user = userDao.findByUserName(userName)
                .orElseThrow(() -> new IncorrectLoginException(userName, password));


        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectLoginException(userName, password);
        }

        if (user.getStatus().equals(BANNED)) {
            throw new UserBannedException();
        }

        return user;
    }


    @Override
    @Transactional(readOnly = true)
    public User loginFromId(Long id) throws InstanceNotFoundException {
        return permissionChecker.checkUser(id);
    }
    @Override
    public User updateProfile(Long id, String firstName, String lastName, String email, String phoneNumber, String image)
            throws InstanceNotFoundException {

        User user = permissionChecker.checkUser(id);

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setImage(image);

        return userDao.save(user);
    }


    @Override
    public void changePassword(Long id, String oldPassword, String newPassword)
            throws InstanceNotFoundException, IncorrectPasswordException {

        User user = permissionChecker.checkUser(id);

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IncorrectPasswordException();
        }

        user.setPassword(passwordEncoder.encode(newPassword));
    }

    @Override
    public List<User> findAllUsers() {
        return StreamSupport.stream(userDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAllActiveUsers() {
        return userDao.findByStatus(ACTIVE);
    }

    @Override
    public List<User> findAllBannedUsers() {
        return userDao.findByStatus(BANNED);
    }


    @Override
    public void banUser(Long id) throws InstanceNotFoundException {
        User user = permissionChecker.checkUser(id);
        user.setStatus(BANNED);
    }

    @Override
    public void unbanUser(Long id) throws InstanceNotFoundException {
        User user = permissionChecker.checkUser(id);
        user.setStatus(ACTIVE);
    }

}
