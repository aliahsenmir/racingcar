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

package it.unimi.dsi.fastutil.longs;

import java.util.Collection;

import it.unimi.dsi.fastutil.objects.ObjectArrays;


/** A class providing static methods and objects that do useful things with type-specific collections.
 *
 * @see java.util.Collections
 */

public class LongCollections {

 private LongCollections() {}

 /** An immutable class representing an empty type-specific collection.
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific collection.
	 */

 public abstract static class EmptyCollection extends AbstractLongCollection {

  protected EmptyCollection() {}

  public boolean add( long k ) { throw new UnsupportedOperationException(); }

  public boolean contains( long k ) { return false; }

  public Object[] toArray() { return ObjectArrays.EMPTY_ARRAY; }


  public long[] toLongArray( long[] a ) { return a; }
  public long[] toLongArray() { return LongArrays.EMPTY_ARRAY; }

  public boolean rem( long k ) { throw new UnsupportedOperationException(); }
  public boolean addAll( LongCollection c ) { throw new UnsupportedOperationException(); }
  public boolean removeAll( LongCollection c ) { throw new UnsupportedOperationException(); }
  public boolean retainAll( LongCollection c ) { throw new UnsupportedOperationException(); }
  public boolean containsAll( LongCollection c ) { return c.isEmpty(); }




  @SuppressWarnings("unchecked")
  public LongListIterator iterator() { return LongIterators.EMPTY_ITERATOR; }

  public int size() { return 0; }
  public void clear() {}

  public int hashCode() { return 0; }
  public boolean equals( Object o ) {
   if ( o == this ) return true;
   if ( ! ( o instanceof Collection ) ) return false;
   return ((Collection<?>)o).isEmpty();
  }
 }


 /** A synchronized wrapper class for collections. */

 public static class SynchronizedCollection implements LongCollection , java.io.Serializable {

  public static final long serialVersionUID = -7046029254386353129L;

  protected final LongCollection collection;
  protected final Object sync;

  protected SynchronizedCollection( final LongCollection c, final Object sync ) {
   if ( c == null ) throw new NullPointerException();
   this.collection = c;
   this.sync = sync;
  }

  protected SynchronizedCollection( final LongCollection c ) {
   if ( c == null ) throw new NullPointerException();
   this.collection = c;
   this.sync = this;
  }

  public int size() { synchronized( sync ) { return collection.size(); } }
  public boolean isEmpty() { synchronized( sync ) { return collection.isEmpty(); } }
  public boolean contains( final long o ) { synchronized( sync ) { return collection.contains( o ); } }

  public long[] toLongArray() { synchronized( sync ) { return collection.toLongArray(); } }


  public Object[] toArray() { synchronized( sync ) { return collection.toArray(); } }
  public long[] toLongArray( final long[] a ) { synchronized( sync ) { return collection.toLongArray( a ); } }
  public long[] toArray( final long[] a ) { synchronized( sync ) { return collection.toLongArray( a ); } }

  public boolean addAll( final LongCollection c ) { synchronized( sync ) { return collection.addAll( c ); } }
  public boolean containsAll( final LongCollection c ) { synchronized( sync ) { return collection.containsAll( c ); } }
  public boolean removeAll( final LongCollection c ) { synchronized( sync ) { return collection.removeAll( c ); } }
  public boolean retainAll( final LongCollection c ) { synchronized( sync ) { return collection.retainAll( c ); } }

  public boolean add( final Long k ) { synchronized( sync ) { return collection.add( k ); } }
  public boolean contains( final Object k ) { synchronized( sync ) { return collection.contains( k ); } }


  public <T> T[] toArray( final T[] a ) { synchronized( sync ) { return collection.toArray( a ); } }

  public LongIterator iterator() { return collection.iterator(); }

  @Deprecated
  public LongIterator longIterator() { return iterator(); }

  public boolean add( final long k ) { synchronized( sync ) { return collection.add( k ); } }
  public boolean rem( final long k ) { synchronized( sync ) { return collection.rem( k ); } }
  public boolean remove( final Object ok ) { synchronized( sync ) { return collection.remove( ok ); } }

  public boolean addAll( final Collection<? extends Long> c ) { synchronized( sync ) { return collection.addAll( c ); } }
  public boolean containsAll( final Collection<?> c ) { synchronized( sync ) { return collection.containsAll( c ); } }
  public boolean removeAll( final Collection<?> c ) { synchronized( sync ) { return collection.removeAll( c ); } }
  public boolean retainAll( final Collection<?> c ) { synchronized( sync ) { return collection.retainAll( c ); } }

  public void clear() { synchronized( sync ) { collection.clear(); } }
  public String toString() { synchronized( sync ) { return collection.toString(); } }
 }


 /** Returns a synchronized collection backed by the specified collection.
	 *
	 * @param c the collection to be wrapped in a synchronized collection.
	 * @return a synchronized view of the specified collection.
	 * @see java.util.Collections#synchronizedCollection(Collection)
	 */
 public static LongCollection synchronize( final LongCollection c ) { return new SynchronizedCollection ( c ); }

 /** Returns a synchronized collection backed by the specified collection, using an assigned object to synchronize.
	 *
	 * @param c the collection to be wrapped in a synchronized collection.
	 * @param sync an object that will be used to synchronize the list access.
	 * @return a synchronized view of the specified collection.
	 * @see java.util.Collections#synchronizedCollection(Collection)
	 */

 public static LongCollection synchronize( final LongCollection c, final Object sync ) { return new SynchronizedCollection ( c, sync ); }


 /** An unmodifiable wrapper class for collections. */

 public static class UnmodifiableCollection implements LongCollection , java.io.Serializable {

  public static final long serialVersionUID = -7046029254386353129L;

  protected final LongCollection collection;

  protected UnmodifiableCollection( final LongCollection c ) {
   if ( c == null ) throw new NullPointerException();
   this.collection = c;
  }

  public int size() { return collection.size(); }
  public boolean isEmpty() { return collection.isEmpty(); }
  public boolean contains( final long o ) { return collection.contains( o ); }

  public LongIterator iterator() { return LongIterators.unmodifiable( collection.iterator() ); }

  @Deprecated
  public LongIterator longIterator() { return iterator(); }

  public boolean add( final long k ) { throw new UnsupportedOperationException(); }
  public boolean remove( final Object ok ) { throw new UnsupportedOperationException(); }

  public boolean addAll( final Collection<? extends Long> c ) { throw new UnsupportedOperationException(); }
  public boolean containsAll( final Collection<?> c ) { return collection.containsAll( c ); }
  public boolean removeAll( final Collection<?> c ) { throw new UnsupportedOperationException(); }
  public boolean retainAll( final Collection<?> c ) { throw new UnsupportedOperationException(); }

  public void clear() { throw new UnsupportedOperationException(); }
  public String toString() { return collection.toString(); }

  public <T> T[] toArray( final T[] a ) { return collection.toArray( a ); }

  public Object[] toArray() { return collection.toArray(); }


  public long[] toLongArray() { return collection.toLongArray(); }
  public long[] toLongArray( final long[] a ) { return collection.toLongArray( a ); }
  public long[] toArray( final long[] a ) { return collection.toArray( a ); }
  public boolean rem( final long k ) { throw new UnsupportedOperationException(); }

  public boolean addAll( final LongCollection c ) { throw new UnsupportedOperationException(); }
  public boolean containsAll( final LongCollection c ) { return collection.containsAll( c ); }
  public boolean removeAll( final LongCollection c ) { throw new UnsupportedOperationException(); }
  public boolean retainAll( final LongCollection c ) { throw new UnsupportedOperationException(); }

  public boolean add( final Long k ) { throw new UnsupportedOperationException(); }
  public boolean contains( final Object k ) { return collection.contains( k ); }

 }


 /** Returns an unmodifiable collection backed by the specified collection.
	 *
	 * @param c the collection to be wrapped in an unmodifiable collection.
	 * @return an unmodifiable view of the specified collection.
	 * @see java.util.Collections#unmodifiableCollection(Collection)
	 */
 public static LongCollection unmodifiable( final LongCollection c ) { return new UnmodifiableCollection ( c ); }
}
