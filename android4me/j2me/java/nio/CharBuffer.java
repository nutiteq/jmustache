/*
 * Javolution - Java(TM) Solution for Real-Time and Embedded Systems
 * Copyright (C) 2006 - Javolution (http://javolution.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package j2me.java.nio;

import j2me.java.lang.CharSequence;
import java.lang.Comparable;
import j2me.java.lang.Readable;
import j2me.java.lang.Appendable;

/**
 *  Class provided for the sole purpose of compiling the Readable interface.
 */
public abstract class CharBuffer extends Buffer implements Comparable,
        Appendable, CharSequence, Readable {

    CharBuffer(int capacity, int limit, int position, int mark) {
        super(capacity, limit, position, mark);
    }
}
