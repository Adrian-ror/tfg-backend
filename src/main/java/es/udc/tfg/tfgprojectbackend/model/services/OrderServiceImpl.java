package es.udc.tfg.tfgprojectbackend.model.services;

import es.udc.tfg.tfgprojectbackend.model.entities.*;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.PermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{


    @Autowired
    private PermissionChecker permissionChecker;

    @Autowired
    private OrderDao orderDao;


    @Override
    @Transactional(readOnly=true)
    public Order findOrder(Long userId, Long orderId) throws InstanceNotFoundException, PermissionException {

        User user = permissionChecker.checkUser(userId);
        Optional<Order> order;
        if(Objects.equals(user.getRole().toString(), "ADMIN")){
            order = orderDao.findById(orderId);
            if(order.isPresent()){
                return order.get();
            }else {
                throw new InstanceNotFoundException("project.entities.order" , orderId);
            }
        }else{
            return permissionChecker.checkOrderExistsAndBelongsTo(orderId, userId);
        }
    }

    @Override
    @Transactional(readOnly=true)
    public Block<Order> findOrders(Long userId, int page, int size) {

        long totalOrders = orderDao.countByUserId(userId);

        Slice<Order> slice = orderDao.findByUserIdOrderByDateDesc(userId, PageRequest.of(page, size));

        return new Block<>(slice.getContent(), (int)totalOrders, slice.hasNext() );

    }


    @Override
    public Block<Order> findOrdersByState(Long userId, Order.OrderState state, int page, int size) {

        long totalOrders = orderDao.countByUserIdAndState(userId, state);

        Slice<Order> slice = orderDao.findByStateOrderByDateDesc(state, PageRequest.of(page, size));

        return new Block<>(slice.getContent(), (int) totalOrders, slice.hasNext());
    }

    @Override
    public void changeOrderState(Long orderId, Order.OrderState newState) throws InstanceNotFoundException {
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.order" , orderId));
        order.setState(newState);
        orderDao.save(order);
    }


    @Override
    @Transactional(readOnly=true)
    public Block<Order> findAllOrders(int page, int size) {

        long totalOrders = orderDao.count();

        Slice<Order> slice = orderDao.findAllByOrderByDateDesc(PageRequest.of(page, size));

        return new Block<>(slice.getContent(), (int) totalOrders, slice.hasNext());
    }


}
