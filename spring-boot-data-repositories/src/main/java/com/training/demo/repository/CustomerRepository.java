package com.training.demo.repository;

import java.awt.print.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.training.demo.entity.Customer;

//public interface CustomerRepository extends Repository<Customer, Integer> {
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	Iterable<Customer> findByCity(String city);
	Iterable<Customer> findByNameAndCity(String name, String city);
	
	@Query("select c from Customer c where c.email like %?1%")
	Iterable<Customer> findByEmailAddressOf(String domain);

	@Query("select c from Customer c where c.email like %?1%")
	Iterable<Customer> findByEmailAddressOf(String domain, Pageable pageable);

	//Customer save(Customer entity);
	
	//Optional<Customer> findById(Integer id);
	
}
