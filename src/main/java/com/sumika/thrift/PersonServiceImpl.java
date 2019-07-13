package com.sumika.thrift;

import org.apache.thrift.TException;

import com.sumika.thrift.generated.DataException;
import com.sumika.thrift.generated.Person;
import com.sumika.thrift.generated.PersonService;

public class PersonServiceImpl implements PersonService.Iface {

	@Override
	public Person getPersonByUsername(String username) throws DataException, TException {
		System.out.println("get client param:"+username);
		Person person = new Person();
		person.setUsername(username);
		person.setAge(20);
		person.setMarried(true);
		return person;
	}

	@Override
	public void savePerson(Person person) throws DataException, TException {
		System.out.println("get client param:"+person);
		System.out.println(person.getUsername()+"--"+person.getAge()+"--"+person.isMarried());
	}

}
