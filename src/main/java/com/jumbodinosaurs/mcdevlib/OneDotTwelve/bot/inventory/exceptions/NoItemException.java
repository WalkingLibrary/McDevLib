package com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.inventory.exceptions;
public class NoItemException extends Exception
{
    public NoItemException(String message)
    {
        super(message);
    }
    
    public NoItemException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
