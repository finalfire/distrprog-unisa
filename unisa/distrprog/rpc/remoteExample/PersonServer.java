package unisa.distrprog.rpc.remoteExample;

public class PersonServer implements Person{
    private String name;
    private String placeOfBirth;
    private int dateOfBirth;

    public PersonServer(String n, String l,int a){
        name=n;
        placeOfBirth=l;
        dateOfBirth=a;
    }

    public int getDateOfBirth(){ return dateOfBirth; }
    public String getPlaceOfBirth(){ return placeOfBirth;}
    public String getName(){ return name;}
    public int year(int year){ return (year -dateOfBirth);}
}