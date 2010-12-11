/* Generic definitions */




/* Assertions (useful to generate conditional code) */
/* Current type and class (and size, if applicable) */
/* Value methods */


/* Interfaces (keys) */
/* Interfaces (values) */
/* Abstract implementations (keys) */
/* Abstract implementations (values) */






/* Static containers (keys) */
/* Static containers (values) */






/* Implementations */
/* Synchronized wrappers */
/* Unmodifiable wrappers */
/* Other wrappers */




/* Methods (keys) */
/* Methods (values) */







/* Methods (keys/values) */

/* Methods that have special names depending on keys (but the special names depend on values) */







/* Equality */
/* Object/Reference-only definitions (keys) */
/* Primitive-type-only definitions (keys) */
/* Object/Reference-only definitions (values) */
/*		 
 * fastutil: Fast & compact type-specific collections for Java
 *
 * Copyright (C) 2002-2008 Sebastiano Vigna 
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package it.unimi.dsi.fastutil.chars;

/**  An abstract class facilitating the creation of type-specific {@linkplain it.unimi.dsi.fastutil.BidirectionalIterator bidirectional iterators}.
 *
 * <P>To create a type-specific bidirectional iterator, besides what is needed
 * for an iterator you need both a method returning the previous element as
 * primitive type and a method returning the previous element as an
 * object. However, if you inherit from this class you need just one (anyone).
 *
 * <P>This class implements also a trivial version of {@link #back(int)} that
 * uses type-specific methods.
 */

public abstract class AbstractCharBidirectionalIterator extends AbstractCharIterator implements CharBidirectionalIterator {

 protected AbstractCharBidirectionalIterator() {}



 /** Delegates to the corresponding generic method. */
 public char previousChar() { return previous().charValue(); }

 /** Delegates to the corresponding type-specific method. */
 public Character previous() { return Character.valueOf( previousChar() ); }



 /** This method just iterates the type-specific version of {@link #previous()} for
	 * at most <code>n</code> times, stopping if {@link
	 * #hasPrevious()} becomes false. */
 public int back( final int n ) {
  int i = n;
  while( i-- != 0 && hasPrevious() ) previousChar();
  return n - i - 1;
 }

}
