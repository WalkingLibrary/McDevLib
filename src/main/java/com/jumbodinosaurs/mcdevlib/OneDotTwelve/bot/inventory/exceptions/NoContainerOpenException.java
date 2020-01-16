package com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.inventory.exceptions;

public class NoContainerOpenException extends Exception
{
    public NoContainerOpenException(String message)
    {
        super(message);
    }
    
    public NoContainerOpenException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
