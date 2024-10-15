package unisa.distrprog.rpc;

public class PersonServer {
    private final String name;
    private final String placeOfBirth;
    private final int dateOfBirth;

    public PersonServer(String n, String l,int a){
        name = n;
        placeOfBirth = l;
        dateOfBirth = a;
    }

    public int getDateOfBirth(){ return dateOfBirth; }
    public String getPlaceOfBirth(){ return placeOfBirth;}
    public String getName(){ return name;}
    public int year(int currentYear){ return (currentYear-dateOfBirth);}

}
