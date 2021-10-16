/**
 * Interface of remote application for RPC, sorting list of 10 numbers.
 */

import java.util.ArrayList;

public interface Sorter extends java.rmi.Remote{

    /**
     * Sorts list of 10 numbers.
     *
     * @param numbers array list of 10 numbers.
     * @return array list of 10 number sorted.
     * @throws java.rmi.RemoteException remove exception thrown.
     */
    public ArrayList sorted(ArrayList<Integer> numbers) throws java.rmi.RemoteException;
}
