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
/* Primitive-type-only definitions (values) */
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

package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSets;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.NoSuchElementException;

/** A class providing static methods and objects that do useful things with type-specific sorted maps.
 *
 * @see java.util.Collections
 */

public class Short2ShortSortedMaps {

 private Short2ShortSortedMaps() {}

 /** Returns a comparator for entries based on a given comparator on keys.
	 *
	 * @param comparator a comparator on keys.
	 * @return the associated comparator on entries.
	 */
 public static Comparator<? super Map.Entry<Short, ?>> entryComparator( final ShortComparator comparator ) {
  return new Comparator<Map.Entry<Short, ?>>() {
   public int compare( Map.Entry<Short, ?> x, Map.Entry<Short, ?> y ) {
    return comparator.compare( x.getKey(), y.getKey() );
   }
  };
 }


 /** An immutable class representing an empty type-specific sorted map. 
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted map.
	 */

 public static class EmptySortedMap extends Short2ShortMaps.EmptyMap implements Short2ShortSortedMap , java.io.Serializable, Cloneable {

  public static final long serialVersionUID = -7046029254386353129L;

  protected EmptySortedMap() {}

  public ShortComparator comparator() { return null; }

  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Short2ShortMap.Entry > short2ShortEntrySet() { return ObjectSortedSets.EMPTY_SET; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Map.Entry<Short, Short>> entrySet() { return ObjectSortedSets.EMPTY_SET; }

  @SuppressWarnings("unchecked")
  public ShortSortedSet keySet() { return ShortSortedSets.EMPTY_SET; }

  @SuppressWarnings("unchecked")
  public Short2ShortSortedMap subMap( final short from, final short to ) { return EMPTY_MAP; }

  @SuppressWarnings("unchecked")
  public Short2ShortSortedMap headMap( final short to ) { return EMPTY_MAP; }

  @SuppressWarnings("unchecked")
  public Short2ShortSortedMap tailMap( final short from ) { return EMPTY_MAP; }

  public short firstShortKey() { throw new NoSuchElementException(); }
  public short lastShortKey() { throw new NoSuchElementException(); }


  public Short2ShortSortedMap headMap( Short oto ) { return headMap( ((oto).shortValue()) ); }
  public Short2ShortSortedMap tailMap( Short ofrom ) { return tailMap( ((ofrom).shortValue()) ); }
  public Short2ShortSortedMap subMap( Short ofrom, Short oto ) { return subMap( ((ofrom).shortValue()), ((oto).shortValue()) ); }

  public Short firstKey() { return (Short.valueOf(firstShortKey())); }
  public Short lastKey() { return (Short.valueOf(lastShortKey())); }


 }



 /** An empty type-specific sorted map (immutable). It is serializable and cloneable. */

 @SuppressWarnings("unchecked")
 public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();


 /** An immutable class representing a type-specific singleton sorted map. 
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted map.
	 */

 public static class Singleton extends Short2ShortMaps.Singleton implements Short2ShortSortedMap , java.io.Serializable, Cloneable {

  public static final long serialVersionUID = -7046029254386353129L;

  protected final ShortComparator comparator;

  protected Singleton( final short key, final short value, ShortComparator comparator ) {
   super( key, value );
   this.comparator = comparator;
  }

  protected Singleton( final short key, final short value ) {
   this( key, value, null );
  }

  @SuppressWarnings("unchecked")
  final int compare( final short k1, final short k2 ) {
   return comparator == null ? ( (k1) < (k2) ? -1 : ( (k1) == (k2) ? 0 : 1 ) ) : comparator.compare( k1, k2 );
  }

  public ShortComparator comparator() { return comparator; }

  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Short2ShortMap.Entry > short2ShortEntrySet() { if ( entries == null ) entries = ObjectSortedSets.singleton( (Short2ShortMap.Entry )new SingletonEntry(), (Comparator<? super Short2ShortMap.Entry >)entryComparator( comparator ) ); return (ObjectSortedSet<Short2ShortMap.Entry >)entries; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Map.Entry<Short, Short>> entrySet() { return (ObjectSortedSet)short2ShortEntrySet(); }

  public ShortSortedSet keySet() { if ( keys == null ) keys = ShortSortedSets.singleton( key, comparator ); return (ShortSortedSet )keys; }

  @SuppressWarnings("unchecked")
  public Short2ShortSortedMap subMap( final short from, final short to ) { if ( compare( from, key ) <= 0 && compare( key, to ) < 0 ) return this; return EMPTY_MAP; }

  @SuppressWarnings("unchecked")
  public Short2ShortSortedMap headMap( final short to ) { if ( compare( key, to ) < 0 ) return this; return EMPTY_MAP; }

  @SuppressWarnings("unchecked")
  public Short2ShortSortedMap tailMap( final short from ) { if ( compare( from, key ) <= 0 ) return this; return EMPTY_MAP; }

  public short firstShortKey() { return key; }
  public short lastShortKey() { return key; }


  public Short2ShortSortedMap headMap( Short oto ) { return headMap( ((oto).shortValue()) ); }
  public Short2ShortSortedMap tailMap( Short ofrom ) { return tailMap( ((ofrom).shortValue()) ); }
  public Short2ShortSortedMap subMap( Short ofrom, Short oto ) { return subMap( ((ofrom).shortValue()), ((oto).shortValue()) ); }

  public Short firstKey() { return (Short.valueOf(firstShortKey())); }
  public Short lastKey() { return (Short.valueOf(lastShortKey())); }

 }

 /** Returns a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <P>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair <code>&lt;key,value></code>.
	 */

 public static Short2ShortSortedMap singleton( final Short key, Short value ) {
  return new Singleton ( ((key).shortValue()), ((value).shortValue()) );
 }

 /** RETURNS a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <P>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @param comparator the comparator to use in the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair <code>&lt;key,value></code>.
	 */

 public static Short2ShortSortedMap singleton( final Short key, Short value, ShortComparator comparator ) {
  return new Singleton ( ((key).shortValue()), ((value).shortValue()), comparator );
 }



 /** Returns a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <P>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair <code>&lt;key,value></code>.
	 */

 public static Short2ShortSortedMap singleton( final short key, final short value ) {
  return new Singleton ( key, value );
 }

 /** Returns a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <P>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @param comparator the comparator to use in the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair <code>&lt;key,value></code>.
	 */

 public static Short2ShortSortedMap singleton( final short key, final short value, ShortComparator comparator ) {
  return new Singleton ( key, value, comparator );
 }




  /** A synchronized wrapper class for sorted maps. */

 public static class SynchronizedSortedMap extends Short2ShortMaps.SynchronizedMap implements Short2ShortSortedMap , java.io.Serializable {

  public static final long serialVersionUID = -7046029254386353129L;

  protected final Short2ShortSortedMap sortedMap;

  protected SynchronizedSortedMap( final Short2ShortSortedMap m, final Object sync ) {
   super( m, sync );
   sortedMap = m;
  }

  protected SynchronizedSortedMap( final Short2ShortSortedMap m ) {
   super( m );
   sortedMap = m;
  }

  public ShortComparator comparator() { synchronized( sync ) { return sortedMap.comparator(); } }

  public ObjectSortedSet<Short2ShortMap.Entry > short2ShortEntrySet() { if ( entries == null ) entries = ObjectSortedSets.synchronize( sortedMap.short2ShortEntrySet(), sync ); return (ObjectSortedSet<Short2ShortMap.Entry >)entries; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Map.Entry<Short, Short>> entrySet() { return (ObjectSortedSet)short2ShortEntrySet(); }
  public ShortSortedSet keySet() { if ( keys == null ) keys = ShortSortedSets.synchronize( sortedMap.keySet(), sync ); return (ShortSortedSet )keys; }

  public Short2ShortSortedMap subMap( final short from, final short to ) { return new SynchronizedSortedMap ( sortedMap.subMap( from, to ), sync ); }
  public Short2ShortSortedMap headMap( final short to ) { return new SynchronizedSortedMap ( sortedMap.headMap( to ), sync ); }
  public Short2ShortSortedMap tailMap( final short from ) { return new SynchronizedSortedMap ( sortedMap.tailMap( from ), sync ); }

  public short firstShortKey() { synchronized( sync ) { return sortedMap.firstShortKey(); } }
  public short lastShortKey() { synchronized( sync ) { return sortedMap.lastShortKey(); } }


  public Short firstKey() { synchronized( sync ) { return sortedMap.firstKey(); } }
  public Short lastKey() { synchronized( sync ) { return sortedMap.lastKey(); } }

  public Short2ShortSortedMap subMap( final Short from, final Short to ) { return new SynchronizedSortedMap ( sortedMap.subMap( from, to ), sync ); }
  public Short2ShortSortedMap headMap( final Short to ) { return new SynchronizedSortedMap ( sortedMap.headMap( to ), sync ); }
  public Short2ShortSortedMap tailMap( final Short from ) { return new SynchronizedSortedMap ( sortedMap.tailMap( from ), sync ); }



 }

 /** Returns a synchronized type-specific sorted map backed by the given type-specific sorted map.
	 *
	 * @param m the sorted map to be wrapped in a synchronized sorted map.
	 * @return a synchronized view of the specified sorted map.
	 * @see java.util.Collections#synchronizedSortedMap(SortedMap)
	 */
 public static Short2ShortSortedMap synchronize( final Short2ShortSortedMap m ) { return new SynchronizedSortedMap ( m ); }

 /** Returns a synchronized type-specific sorted map backed by the given type-specific sorted map, using an assigned object to synchronize.
	 *
	 * @param m the sorted map to be wrapped in a synchronized sorted map.
	 * @param sync an object that will be used to synchronize the access to the sorted sorted map.
	 * @return a synchronized view of the specified sorted map.
	 * @see java.util.Collections#synchronizedSortedMap(SortedMap)
	 */

 public static Short2ShortSortedMap synchronize( final Short2ShortSortedMap m, final Object sync ) { return new SynchronizedSortedMap ( m, sync ); }




 /** An unmodifiable wrapper class for sorted maps. */

 public static class UnmodifiableSortedMap extends Short2ShortMaps.UnmodifiableMap implements Short2ShortSortedMap , java.io.Serializable {

  public static final long serialVersionUID = -7046029254386353129L;

  protected final Short2ShortSortedMap sortedMap;

  protected UnmodifiableSortedMap( final Short2ShortSortedMap m ) {
   super( m );
   sortedMap = m;
  }

  public ShortComparator comparator() { return sortedMap.comparator(); }

  public ObjectSortedSet<Short2ShortMap.Entry > short2ShortEntrySet() { if ( entries == null ) entries = ObjectSortedSets.unmodifiable( sortedMap.short2ShortEntrySet() ); return (ObjectSortedSet<Short2ShortMap.Entry >)entries; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Map.Entry<Short, Short>> entrySet() { return (ObjectSortedSet)short2ShortEntrySet(); }
  public ShortSortedSet keySet() { if ( keys == null ) keys = ShortSortedSets.unmodifiable( sortedMap.keySet() ); return (ShortSortedSet )keys; }

  public Short2ShortSortedMap subMap( final short from, final short to ) { return new UnmodifiableSortedMap ( sortedMap.subMap( from, to ) ); }
  public Short2ShortSortedMap headMap( final short to ) { return new UnmodifiableSortedMap ( sortedMap.headMap( to ) ); }
  public Short2ShortSortedMap tailMap( final short from ) { return new UnmodifiableSortedMap ( sortedMap.tailMap( from ) ); }

  public short firstShortKey() { return sortedMap.firstShortKey(); }
  public short lastShortKey() { return sortedMap.lastShortKey(); }


  public Short firstKey() { return sortedMap.firstKey(); }
  public Short lastKey() { return sortedMap.lastKey(); }

  public Short2ShortSortedMap subMap( final Short from, final Short to ) { return new UnmodifiableSortedMap ( sortedMap.subMap( from, to ) ); }
  public Short2ShortSortedMap headMap( final Short to ) { return new UnmodifiableSortedMap ( sortedMap.headMap( to ) ); }
  public Short2ShortSortedMap tailMap( final Short from ) { return new UnmodifiableSortedMap ( sortedMap.tailMap( from ) ); }



 }

 /** Returns an unmodifiable type-specific sorted map backed by the given type-specific sorted map.
	 *
	 * @param m the sorted map to be wrapped in an unmodifiable sorted map.
	 * @return an unmodifiable view of the specified sorted map.
	 * @see java.util.Collections#unmodifiableSortedMap(SortedMap)
	 */
 public static Short2ShortSortedMap unmodifiable( final Short2ShortSortedMap m ) { return new UnmodifiableSortedMap ( m ); }
}
