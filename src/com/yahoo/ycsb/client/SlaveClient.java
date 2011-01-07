package com.yahoo.ycsb.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Properties;

import com.yahoo.ycsb.measurements.Measurements;
import com.yahoo.ycsb.measurements.OneMeasurement;
import com.yahoo.ycsb.rmi.SlaveRMIInterface;

public class SlaveClient implements SlaveRMIInterface {
	public static final String REGISTRY_NAME = "SlaveRMIInterface";
	public static final int RMI_PORT = 1098;
	private static SlaveClient client = null;
	
	private LoadThread lt;
	private Properties props;
	private Registry registry;

	private SlaveClient() {
		lt = null;
		props = null;
		
		try {
            SlaveRMIInterface stub = (SlaveRMIInterface) UnicastRemoteObject.exportObject(this, 0);
            LocateRegistry.createRegistry(RMI_PORT);
            registry = LocateRegistry.getRegistry();
            registry.rebind(REGISTRY_NAME, stub);
        } catch (Exception e) {
            System.err.println("SlaveRMI exception:");
            e.printStackTrace();
        }
	}
	
	public static SlaveClient getSlaveClient() {
		if (client == null)
			client = new SlaveClient();
		return client;
	}
	
	@Override
	public HashMap<String, OneMeasurement> getCurrentStats() {
		if (lt != null && lt.getState() != Thread.State.TERMINATED) {
			System.out.println("Ops Done: " + Measurements.getMeasurements().getOperations());
			return Measurements.getMeasurements().getAndResetPartialData();
		} else {
			return null;
		}
	}
	
	@Override
	public int setProperties(Properties props) {
		if (props == null)
			return -1;
		this.props = props;
		return 0;
	}
	
	@Override
	public int execute() {
		if (props == null)
			return -2;
		System.out.println(props.toString());
		if (lt == null || lt.getState() == Thread.State.TERMINATED) {
			lt = new LoadThread(props);
		} else {
			return -1;
		}
		lt.start();
		return 0;
	}

	@Override
	public Thread.State getStatus() {
		if (lt == null)
			return null;
		else
			return lt.getState();
	}
	
	public void shutdown() {
		try {
			registry.unbind(REGISTRY_NAME);
			UnicastRemoteObject.unexportObject(this, true);
		} catch (AccessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	public static void main(String args[]) {
		try {
		    InetAddress addr = InetAddress.getLocalHost();
		    System.out.println("Binding to: " + addr.getHostAddress());
		    System.setProperty("java.rmi.server.hostname", addr.getHostAddress());
		} catch (UnknownHostException e) {
			System.out.println("I can't get my IP address");
		}
		
		SlaveClient client = getSlaveClient();
		
	}
}

