package com.sumika.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import com.sumika.thrift.generated.DataException;
import com.sumika.thrift.generated.Person;
import com.sumika.thrift.generated.PersonService;

public class ThriftClient {
	public static void main(String[] args) throws DataException, TException {
		TTransport transport = new TFramedTransport(new TSocket("localhost", 8899), 600);
		TCompactProtocol protocol = new TCompactProtocol(transport);
		PersonService.Client client = new PersonService.Client(protocol);
		
		try {
			transport.open();
			Person person = client.getPersonByUsername("张三");
			System.out.println(person.getUsername()+"--"+person.getAge()+"--"+person.isMarried());
			
			System.out.println("---------------------");
			
			person = new Person();
			person.setUsername("李四");
			person.setAge(30);
			person.setMarried(false);
			client.savePerson(person);
		}
		finally {
			transport.close();
		}
		/**
		 张三--20--true
		---------------------
		 */
	}
}
