package net.java.vll.vll4j.api;

public enum Multiplicity
{
  One,  ZeroOrOne,  ZeroOrMore,  OneOrMore,  Not,  Guard;
  
  private Multiplicity() {}
  
  public abstract String toString();
}


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/api/Multiplicity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */