package com.training.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.training.demo.entity.Customer;
import com.training.demo.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@Rollback(false)
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class SpringBootDataRepositoriesApplicationTests {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testAddCustomer() {
		Customer customer = new Customer();
		customer.setName("Majrul");
		customer.setEmail("majrul@gmail.com");
		customer.setCity("Mumbai");
		customerRepository.save(customer);
	}
	
	@Test
	public void testFetchAllCustomers() {
		Iterable<Customer> customers = customerRepository.findAll();
		customers.forEach((customer) -> System.out.println(customer));
	}

	@Test
	public void testFetchCustomersBasedonEmailAddress() {
		Iterable<Customer> customers = customerRepository.findByEmailAddressOf("gmail");
		customers.forEach((customer) -> System.out.println(customer));
	}

	@Test
	public void testPagination() {
		long count = customerRepository.count();
		int pageSize = 5;
		long pages = count / pageSize;
		
		for(int i=0; i<pages; i++) {
			Iterable<Customer> customers = 
					//customerRepository.findByEmailAddressOf("gmail",
					customerRepository.findAll(
							PageRequest.of(i, pageSize, Sort.by("name").ascending()));
			customers.forEach((customer) -> System.out.println(customer));
		}
	}
	
}
