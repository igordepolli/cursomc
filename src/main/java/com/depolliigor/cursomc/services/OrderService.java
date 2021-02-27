package com.depolliigor.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depolliigor.cursomc.domain.Order;
import com.depolliigor.cursomc.domain.OrderItem;
import com.depolliigor.cursomc.domain.PaymentWithSlip;
import com.depolliigor.cursomc.domain.enums.PaymentStatus;
import com.depolliigor.cursomc.repositories.OrderItemRepository;
import com.depolliigor.cursomc.repositories.OrderRepository;
import com.depolliigor.cursomc.repositories.PaymentRepository;
import com.depolliigor.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repo;
	
	@Autowired
	private SlipService slipService;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private ProductService productService;

	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public Order find(Integer id) {
		Optional<Order> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Pedido de ID: " + id + " não encontrado!"));
	}
	
	@Transactional
	public Order insert(Order obj) {
		obj.setId(null);
		obj.setInstant(new Date());
		obj.getPayment().setPaymentStatus(PaymentStatus.PENDENTE);
		obj.getPayment().setOrder(obj);
		if (obj.getPayment() instanceof PaymentWithSlip) {
			PaymentWithSlip payment = (PaymentWithSlip) obj.getPayment();
			slipService.fillPaymentWithSlip(payment, obj.getInstant());
		}
		obj = repo.save(obj);
		paymentRepository.save(obj.getPayment());
		for (OrderItem oi : obj.getItems()) {
			oi.setDiscount(0.0);
			oi.setPrice(productService.find(oi.getProduct().getId()).getPrice());
			oi.setOrder(obj);
		}
		orderItemRepository.saveAll(obj.getItems());
		return obj;
	}
	
}
