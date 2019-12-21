package com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util;

/*
 *
 * */
public enum Quadrant
{
    
    POSPOS(0, 0), POSNEG(0, -1), NEGPOS(-1, 0), NEGNEG(-1, -1);
    public int x;
    public int z;
    
    Quadrant(int x, int z)
    {
        this.x = x;
        this.z = z;
    }
}
