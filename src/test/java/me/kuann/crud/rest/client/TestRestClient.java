package me.kuann.crud.rest.client;

import java.util.Arrays;

import org.junit.Test;

public class TestRestClient {

	private static final String EMPLOYEE_SERVICE_URL = "http://kuann.free.beeceptor.com/demo";

	@Test
	public void test_get_all_employees() {
		Employee[] employees = RestClient.build(EMPLOYEE_SERVICE_URL)
				.onPath("employees")
				.getForResponse(Employee[].class);
		
		System.out.println(Arrays.asList(employees));
	}
	
	@Test(expected = RestClientException.class)
	public void test_not_found_uri() {
		Employee[] employees = RestClient.build(EMPLOYEE_SERVICE_URL)
				.onPath("employeess")
				.getForResponse(Employee[].class);
		
		System.out.println(Arrays.asList(employees));
	}
	
	@Test
	public void test_get_a_specific_employee() {
		Employee david = RestClient.build(EMPLOYEE_SERVICE_URL)
				.onPath("employees")
				.onPath("1")
				.getForResponse(Employee.class);
		
		System.out.println(david);
	}
	
	@Test
	public void test_get_a_an_employee_that_is_not_existing() {
		Employee david = RestClient.build(EMPLOYEE_SERVICE_URL)
				.onPath("employees")
				.onPath("5")
				.getForResponse(Employee.class);
		
		System.out.println(david);
	}
	
	@Test
	public void test_create_new_employee() {
		Employee employee = new Employee();
		employee.setName("Klara");
		
		Employee createdEmployee = RestClient.build(EMPLOYEE_SERVICE_URL)
				.onPath("employees")
				.withBody(employee)
				.postForReponse(Employee.class);
		
		System.out.println(createdEmployee);
	}

}
