package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.naming.NamingException;

public class WarehouseClient
{
    public static void main(String[] args) throws NamingException, RemoteException, MalformedURLException, NotBoundException
    {
        for (int i =0;i<10;i++){
            System.out.println("RMI registry binding:");
            String url = "rmi://localhost:1099/central_warehoues";
            Warehouse centralWarehouse = (Warehouse) Naming.lookup(url);
            System.err.println(i+"#########"+Naming.list(url));
            String descr = "mate7";
            double price = centralWarehouse.getPrice(descr);
            System.out.println(i+"----"+descr + ":" + price);
        }

    }
}