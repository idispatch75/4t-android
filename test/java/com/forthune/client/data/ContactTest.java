package com.forthune.client.data;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ContactTest {

	@Test
	public void testConstructor() {
		Contact contact = new Contact("pseudo");
		assertConstructorData(contact);
		
		TestUtils.assertConstructor(contact, new Contact("pseudo"), false);
	}
	
	@Test
	public void testFullConstructor() {
		Contact contact = new Contact("1", 1, "pseudo");
		assertConstructorData(contact);
		
		TestUtils.assertConstructor(contact, new Contact("1", 1, "pseudo"), true);
	}
	
	private void assertConstructorData(Contact contact) {
		assertThat(contact.getIcon().isPresent(), is(false));
	}
}
