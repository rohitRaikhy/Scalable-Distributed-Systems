import java.rmi.Naming;

public class SorterServer {

    public SorterServer() {
        try {
            Sorter s = new SorterImpl();
            Naming.rebind("rmi://localhost:1099/SorterService", s);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

    public static void main(String args[]) {
        new SorterServer();
    }
}
