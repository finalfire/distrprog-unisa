package unisa.distrprog.rpc.remoteExample;

public class PersonClient {
    public static void main(String[] args) {
        try {
            Person p = new PersonStub("localhost");

            System.out.println("Name: " + p.getName());
            System.out.println("Place of birth: " + p.getPlaceOfBirth());
            System.out.println("Date of birth : " + p.getDateOfBirth());
            System.out.println("This person in 2031 will have: " + p.year(2031) + " years old");

            ((PersonStub) p).close();
        } catch (Throwable t){
            t.printStackTrace();
        }
    }
}

