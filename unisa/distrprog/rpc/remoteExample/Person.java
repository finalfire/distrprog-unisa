package unisa.distrprog.rpc.remoteExample;

public interface Person {
    public String getName()throws Throwable;
    public String getPlaceOfBirth ()throws Throwable;
    public int getDateOfBirth () throws Throwable;
    public int year(int year) throws Throwable;
}