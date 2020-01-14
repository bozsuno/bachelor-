package com.company;

import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;

public class Dimension implements Comparable<Dimension> {
    private int r = 0;
    private int c = 0;
    public Dimension(int r, int c) {
        if ( r< 0 || c < 0 ) {
            throw new StringIndexOutOfBoundsException("no negative");
        }
        this.r =r;
        this.c =c;
    }
    public int getrows() { return r;}
    public int getclms() { return c;}

    @Override
    public int compareTo(Dimension o) {
        return 0;
    }

}
