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

package it.unimi.dsi.fastutil.objects;

import java.util.Set;

/**  An abstract class providing basic methods for sets implementing a type-specific interface. */

public abstract class AbstractReferenceSet <K> extends AbstractReferenceCollection <K> implements Cloneable, ReferenceSet <K> {

 protected AbstractReferenceSet() {}

 public abstract ObjectIterator <K> iterator();

 public boolean equals( final Object o ) {
  if ( o == this ) return true;
  if ( !( o instanceof Set ) ) return false;

  Set<?> s = (Set<?>) o;
  if ( s.size() != size() ) return false;
  return containsAll(s);
 }


 /** Returns a hash code for this set.
	 *
	 * The hash code of a set is computed by summing the hash codes of
	 * its elements.
	 *
	 * @return a hash code for this set.
	 */

 public int hashCode() {
  int h = 0, n = size();
  ObjectIterator <K> i = iterator();
  K k;

  while( n-- != 0 ) {
   k = i.next(); // We need k because KEY2INT() is a macro with repeated evaluation.
   h += (System.identityHashCode(k));
  }
  return h;
 }


 public boolean remove( Object k ) {
  throw new UnsupportedOperationException();
 }
}
