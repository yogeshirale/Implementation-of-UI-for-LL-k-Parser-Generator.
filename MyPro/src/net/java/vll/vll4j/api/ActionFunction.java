package net.java.vll.vll4j.api;

import javax.script.ScriptException;
import net.java.vll.vll4j.combinator.Reader;

public abstract interface ActionFunction
{
  public abstract Object run(Object paramObject, Reader paramReader, int paramInt)
    throws ScriptException;
}


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/api/ActionFunction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */