import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;

public class SorterClient {

    public static void main(String[] args) {
        try {
            Sorter s = (Sorter) Naming.lookup("rmi://localhost/SorterService");
            ArrayList <Integer> numbers = new ArrayList();
            Random objGenerator = new Random();
            for(int i = 0; i < 10; i ++) {
                int randomNumber = objGenerator.nextInt(100);
                numbers.add(randomNumber);
            }
            System.out.println( s.sorted(numbers) );
        } catch (MalformedURLException murle) {
            System.out.println();
            System.out.println("Malformed Exception");
            System.out.println(murle);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
