package com.sumika.thrift;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

import com.sumika.thrift.generated.PersonService;

public class ThriftServer {
	public static void main(String[] args) throws TTransportException {
		TNonblockingServerSocket socket = new TNonblockingServerSocket(8899);
		THsHaServer.Args arg = new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4);
		PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<PersonServiceImpl>(new PersonServiceImpl());
		
		arg.protocolFactory(new TCompactProtocol.Factory());
		arg.transportFactory(new TFramedTransport.Factory());
		arg.processorFactory(new TProcessorFactory(processor));
		
		TServer server = new THsHaServer(arg);
		server.serve();
		
		/**
		get client param:张三
		get client param:Person(username:李四, age:30, married:false)
		李四--30--false
		 * 
		 */
	}
}
