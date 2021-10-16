import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;

public class SorterImpl extends java.rmi.server.UnicastRemoteObject implements Sorter {

    public SorterImpl() throws java.rmi.RemoteException {
        super();
    }

    @Override
    public ArrayList sorted(ArrayList<Integer> numbers) throws java.rmi.RemoteException {
        Collections.sort(numbers);
        return numbers;
    }
}
