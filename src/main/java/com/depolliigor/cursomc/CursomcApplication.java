package com.depolliigor.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.depolliigor.cursomc.domain.Address;
import com.depolliigor.cursomc.domain.Category;
import com.depolliigor.cursomc.domain.City;
import com.depolliigor.cursomc.domain.Client;
import com.depolliigor.cursomc.domain.Order;
import com.depolliigor.cursomc.domain.OrderItem;
import com.depolliigor.cursomc.domain.Payment;
import com.depolliigor.cursomc.domain.PaymentWithCard;
import com.depolliigor.cursomc.domain.PaymentWithSlip;
import com.depolliigor.cursomc.domain.Product;
import com.depolliigor.cursomc.domain.State;
import com.depolliigor.cursomc.domain.enums.ClientType;
import com.depolliigor.cursomc.domain.enums.PaymentStatus;
import com.depolliigor.cursomc.repositories.AddressRepository;
import com.depolliigor.cursomc.repositories.CategoryRepository;
import com.depolliigor.cursomc.repositories.CityRepository;
import com.depolliigor.cursomc.repositories.OrderRepository;
import com.depolliigor.cursomc.repositories.ClientRepository;
import com.depolliigor.cursomc.repositories.OrderItemRepository;
import com.depolliigor.cursomc.repositories.PaymentRepository;
import com.depolliigor.cursomc.repositories.ProductRepository;
import com.depolliigor.cursomc.repositories.StateRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired 
	private OrderRepository clientOrderRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");
		
		Product p1 = new Product(null, "Computador", 2000.00);
		Product p2 = new Product(null, "Impressora", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);
		
		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2));
		
		p1.getCategories().addAll(Arrays.asList(cat1));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2));
		p3.getCategories().addAll(Arrays.asList(cat1));
		
		categoryRepository.saveAll(Arrays.asList(cat1, cat2));
		productRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		//----------------------------------------------------------------
		
		State st1 = new State(null, "Minas Gerais");
		State st2 = new State(null, "São Paulo");
		
		City c1 = new City(null, "Uberlândia", st1);
		City c2 = new City(null, "São Paulo", st2);
		City c3 = new City(null, "Campinas", st2);
		
		st1.getCities().addAll(Arrays.asList(c1));
		st2.getCities().addAll(Arrays.asList(c2, c3));
		
		stateRepository.saveAll(Arrays.asList(st1, st2));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		//----------------------------------------------------------------
		
		Client cli1 = new Client(null, "Maria Silva", "maria@gmail.com", "36379112377", ClientType.PESSOAFISICA);
		cli1.getPhones().addAll(Arrays.asList("27363323", "93838393"));
		
		Address a1 = new Address(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Address a2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getAdresses().addAll(Arrays.asList(a1, a2));
		
		clientRepository.saveAll(Arrays.asList(cli1));
		addressRepository.saveAll(Arrays.asList(a1, a2));
		
		//----------------------------------------------------------------
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Order ord1 = new Order(null, sdf.parse("30/09/2017 10:32"), cli1, a1);
		Order ord2 = new Order(null, sdf.parse("10/10/2017 19:35"), cli1, a2);
		
		Payment pyt1 = new PaymentWithCard(null, PaymentStatus.QUITADO, ord1, 6);
		ord1.setPayment(pyt1);
		
		Payment pyt2 = new PaymentWithSlip(null, PaymentStatus.PENDENTE, ord2, sdf.parse("20/10/2017 00:00"), null);
		ord2.setPayment(pyt2);
		
		cli1.getOrders().addAll(Arrays.asList(ord1, ord2));
		
		clientOrderRepository.saveAll(Arrays.asList(ord1, ord2));
		paymentRepository.saveAll(Arrays.asList(pyt1, pyt2));
		
		//----------------------------------------------------------------
		
		OrderItem oi1 = new OrderItem(ord1, p1, 0.00, 1, 2000.00);
		OrderItem oi2 = new OrderItem(ord1, p3, 0.00, 2, 80.00);
		OrderItem oi3 = new OrderItem(ord2, p2, 100.00, 1, 800.00);
		
		ord1.getItems().addAll(Arrays.asList(oi1, oi2));
		ord2.getItems().addAll(Arrays.asList(oi3));
		
		p1.getItems().addAll(Arrays.asList(oi1));
		p2.getItems().addAll(Arrays.asList(oi3));
		p3.getItems().addAll(Arrays.asList(oi2));
		
		orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3));
		
		System.out.println(System.currentTimeMillis());
	}
	
}
