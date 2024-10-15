package unisa.distrprog.rpc;

public class PersonClient {

    public static void main(String[] args) {
        PersonServer p = new PersonServer("Francesco","Salerno",1989);
        System.out.println("Name: " + p.getName());
        System.out.println("Place of birth: " + p.getPlaceOfBirth());
        System.out.println("Date of birth : " + p.getDateOfBirth());
        System.out.println("This person in 2030 will have: " + p.year(2031) + " years old");
    }

}
