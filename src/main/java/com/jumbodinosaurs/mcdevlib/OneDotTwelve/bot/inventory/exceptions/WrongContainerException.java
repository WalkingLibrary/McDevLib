package com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.inventory.exceptions;

public class WrongContainerException extends Exception
{
    public WrongContainerException(String message)
    {
        super(message);
    }
    
    public WrongContainerException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
