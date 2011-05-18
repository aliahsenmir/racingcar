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

package it.unimi.dsi.fastutil.longs;

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

public class Long2LongSortedMaps {

 private Long2LongSortedMaps() {}

 /** Returns a comparator for entries based on a given comparator on keys.
	 *
	 * @param comparator a comparator on keys.
	 * @return the associated comparator on entries.
	 */
 public static Comparator<? super Map.Entry<Long, ?>> entryComparator( final LongComparator comparator ) {
  return new Comparator<Map.Entry<Long, ?>>() {
   public int compare( Map.Entry<Long, ?> x, Map.Entry<Long, ?> y ) {
    return comparator.compare( x.getKey(), y.getKey() );
   }
  };
 }


 /** An immutable class representing an empty type-specific sorted map. 
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted map.
	 */

 public static class EmptySortedMap extends Long2LongMaps.EmptyMap implements Long2LongSortedMap , java.io.Serializable, Cloneable {

  public static final long serialVersionUID = -7046029254386353129L;

  protected EmptySortedMap() {}

  public LongComparator comparator() { return null; }

  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Long2LongMap.Entry > long2LongEntrySet() { return ObjectSortedSets.EMPTY_SET; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Map.Entry<Long, Long>> entrySet() { return ObjectSortedSets.EMPTY_SET; }

  @SuppressWarnings("unchecked")
  public LongSortedSet keySet() { return LongSortedSets.EMPTY_SET; }

  @SuppressWarnings("unchecked")
  public Long2LongSortedMap subMap( final long from, final long to ) { return EMPTY_MAP; }

  @SuppressWarnings("unchecked")
  public Long2LongSortedMap headMap( final long to ) { return EMPTY_MAP; }

  @SuppressWarnings("unchecked")
  public Long2LongSortedMap tailMap( final long from ) { return EMPTY_MAP; }

  public long firstLongKey() { throw new NoSuchElementException(); }
  public long lastLongKey() { throw new NoSuchElementException(); }


  public Long2LongSortedMap headMap( Long oto ) { return headMap( ((oto).longValue()) ); }
  public Long2LongSortedMap tailMap( Long ofrom ) { return tailMap( ((ofrom).longValue()) ); }
  public Long2LongSortedMap subMap( Long ofrom, Long oto ) { return subMap( ((ofrom).longValue()), ((oto).longValue()) ); }

  public Long firstKey() { return (Long.valueOf(firstLongKey())); }
  public Long lastKey() { return (Long.valueOf(lastLongKey())); }


 }



 /** An empty type-specific sorted map (immutable). It is serializable and cloneable. */

 @SuppressWarnings("unchecked")
 public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();


 /** An immutable class representing a type-specific singleton sorted map. 
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted map.
	 */

 public static class Singleton extends Long2LongMaps.Singleton implements Long2LongSortedMap , java.io.Serializable, Cloneable {

  public static final long serialVersionUID = -7046029254386353129L;

  protected final LongComparator comparator;

  protected Singleton( final long key, final long value, LongComparator comparator ) {
   super( key, value );
   this.comparator = comparator;
  }

  protected Singleton( final long key, final long value ) {
   this( key, value, null );
  }

  @SuppressWarnings("unchecked")
  final int compare( final long k1, final long k2 ) {
   return comparator == null ? ( (k1) < (k2) ? -1 : ( (k1) == (k2) ? 0 : 1 ) ) : comparator.compare( k1, k2 );
  }

  public LongComparator comparator() { return comparator; }

  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Long2LongMap.Entry > long2LongEntrySet() { if ( entries == null ) entries = ObjectSortedSets.singleton( (Long2LongMap.Entry )new SingletonEntry(), (Comparator<? super Long2LongMap.Entry >)entryComparator( comparator ) ); return (ObjectSortedSet<Long2LongMap.Entry >)entries; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Map.Entry<Long, Long>> entrySet() { return (ObjectSortedSet)long2LongEntrySet(); }

  public LongSortedSet keySet() { if ( keys == null ) keys = LongSortedSets.singleton( key, comparator ); return (LongSortedSet )keys; }

  @SuppressWarnings("unchecked")
  public Long2LongSortedMap subMap( final long from, final long to ) { if ( compare( from, key ) <= 0 && compare( key, to ) < 0 ) return this; return EMPTY_MAP; }

  @SuppressWarnings("unchecked")
  public Long2LongSortedMap headMap( final long to ) { if ( compare( key, to ) < 0 ) return this; return EMPTY_MAP; }

  @SuppressWarnings("unchecked")
  public Long2LongSortedMap tailMap( final long from ) { if ( compare( from, key ) <= 0 ) return this; return EMPTY_MAP; }

  public long firstLongKey() { return key; }
  public long lastLongKey() { return key; }


  public Long2LongSortedMap headMap( Long oto ) { return headMap( ((oto).longValue()) ); }
  public Long2LongSortedMap tailMap( Long ofrom ) { return tailMap( ((ofrom).longValue()) ); }
  public Long2LongSortedMap subMap( Long ofrom, Long oto ) { return subMap( ((ofrom).longValue()), ((oto).longValue()) ); }

  public Long firstKey() { return (Long.valueOf(firstLongKey())); }
  public Long lastKey() { return (Long.valueOf(lastLongKey())); }

 }

 /** Returns a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <P>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair <code>&lt;key,value></code>.
	 */

 public static Long2LongSortedMap singleton( final Long key, Long value ) {
  return new Singleton ( ((key).longValue()), ((value).longValue()) );
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

 public static Long2LongSortedMap singleton( final Long key, Long value, LongComparator comparator ) {
  return new Singleton ( ((key).longValue()), ((value).longValue()), comparator );
 }



 /** Returns a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <P>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair <code>&lt;key,value></code>.
	 */

 public static Long2LongSortedMap singleton( final long key, final long value ) {
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

 public static Long2LongSortedMap singleton( final long key, final long value, LongComparator comparator ) {
  return new Singleton ( key, value, comparator );
 }




  /** A synchronized wrapper class for sorted maps. */

 public static class SynchronizedSortedMap extends Long2LongMaps.SynchronizedMap implements Long2LongSortedMap , java.io.Serializable {

  public static final long serialVersionUID = -7046029254386353129L;

  protected final Long2LongSortedMap sortedMap;

  protected SynchronizedSortedMap( final Long2LongSortedMap m, final Object sync ) {
   super( m, sync );
   sortedMap = m;
  }

  protected SynchronizedSortedMap( final Long2LongSortedMap m ) {
   super( m );
   sortedMap = m;
  }

  public LongComparator comparator() { synchronized( sync ) { return sortedMap.comparator(); } }

  public ObjectSortedSet<Long2LongMap.Entry > long2LongEntrySet() { if ( entries == null ) entries = ObjectSortedSets.synchronize( sortedMap.long2LongEntrySet(), sync ); return (ObjectSortedSet<Long2LongMap.Entry >)entries; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Map.Entry<Long, Long>> entrySet() { return (ObjectSortedSet)long2LongEntrySet(); }
  public LongSortedSet keySet() { if ( keys == null ) keys = LongSortedSets.synchronize( sortedMap.keySet(), sync ); return (LongSortedSet )keys; }

  public Long2LongSortedMap subMap( final long from, final long to ) { return new SynchronizedSortedMap ( sortedMap.subMap( from, to ), sync ); }
  public Long2LongSortedMap headMap( final long to ) { return new SynchronizedSortedMap ( sortedMap.headMap( to ), sync ); }
  public Long2LongSortedMap tailMap( final long from ) { return new SynchronizedSortedMap ( sortedMap.tailMap( from ), sync ); }

  public long firstLongKey() { synchronized( sync ) { return sortedMap.firstLongKey(); } }
  public long lastLongKey() { synchronized( sync ) { return sortedMap.lastLongKey(); } }


  public Long firstKey() { synchronized( sync ) { return sortedMap.firstKey(); } }
  public Long lastKey() { synchronized( sync ) { return sortedMap.lastKey(); } }

  public Long2LongSortedMap subMap( final Long from, final Long to ) { return new SynchronizedSortedMap ( sortedMap.subMap( from, to ), sync ); }
  public Long2LongSortedMap headMap( final Long to ) { return new SynchronizedSortedMap ( sortedMap.headMap( to ), sync ); }
  public Long2LongSortedMap tailMap( final Long from ) { return new SynchronizedSortedMap ( sortedMap.tailMap( from ), sync ); }



 }

 /** Returns a synchronized type-specific sorted map backed by the given type-specific sorted map.
	 *
	 * @param m the sorted map to be wrapped in a synchronized sorted map.
	 * @return a synchronized view of the specified sorted map.
	 * @see java.util.Collections#synchronizedSortedMap(SortedMap)
	 */
 public static Long2LongSortedMap synchronize( final Long2LongSortedMap m ) { return new SynchronizedSortedMap ( m ); }

 /** Returns a synchronized type-specific sorted map backed by the given type-specific sorted map, using an assigned object to synchronize.
	 *
	 * @param m the sorted map to be wrapped in a synchronized sorted map.
	 * @param sync an object that will be used to synchronize the access to the sorted sorted map.
	 * @return a synchronized view of the specified sorted map.
	 * @see java.util.Collections#synchronizedSortedMap(SortedMap)
	 */

 public static Long2LongSortedMap synchronize( final Long2LongSortedMap m, final Object sync ) { return new SynchronizedSortedMap ( m, sync ); }




 /** An unmodifiable wrapper class for sorted maps. */

 public static class UnmodifiableSortedMap extends Long2LongMaps.UnmodifiableMap implements Long2LongSortedMap , java.io.Serializable {

  public static final long serialVersionUID = -7046029254386353129L;

  protected final Long2LongSortedMap sortedMap;

  protected UnmodifiableSortedMap( final Long2LongSortedMap m ) {
   super( m );
   sortedMap = m;
  }

  public LongComparator comparator() { return sortedMap.comparator(); }

  public ObjectSortedSet<Long2LongMap.Entry > long2LongEntrySet() { if ( entries == null ) entries = ObjectSortedSets.unmodifiable( sortedMap.long2LongEntrySet() ); return (ObjectSortedSet<Long2LongMap.Entry >)entries; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Map.Entry<Long, Long>> entrySet() { return (ObjectSortedSet)long2LongEntrySet(); }
  public LongSortedSet keySet() { if ( keys == null ) keys = LongSortedSets.unmodifiable( sortedMap.keySet() ); return (LongSortedSet )keys; }

  public Long2LongSortedMap subMap( final long from, final long to ) { return new UnmodifiableSortedMap ( sortedMap.subMap( from, to ) ); }
  public Long2LongSortedMap headMap( final long to ) { return new UnmodifiableSortedMap ( sortedMap.headMap( to ) ); }
  public Long2LongSortedMap tailMap( final long from ) { return new UnmodifiableSortedMap ( sortedMap.tailMap( from ) ); }

  public long firstLongKey() { return sortedMap.firstLongKey(); }
  public long lastLongKey() { return sortedMap.lastLongKey(); }


  public Long firstKey() { return sortedMap.firstKey(); }
  public Long lastKey() { return sortedMap.lastKey(); }

  public Long2LongSortedMap subMap( final Long from, final Long to ) { return new UnmodifiableSortedMap ( sortedMap.subMap( from, to ) ); }
  public Long2LongSortedMap headMap( final Long to ) { return new UnmodifiableSortedMap ( sortedMap.headMap( to ) ); }
  public Long2LongSortedMap tailMap( final Long from ) { return new UnmodifiableSortedMap ( sortedMap.tailMap( from ) ); }



 }

 /** Returns an unmodifiable type-specific sorted map backed by the given type-specific sorted map.
	 *
	 * @param m the sorted map to be wrapped in an unmodifiable sorted map.
	 * @return an unmodifiable view of the specified sorted map.
	 * @see java.util.Collections#unmodifiableSortedMap(SortedMap)
	 */
 public static Long2LongSortedMap unmodifiable( final Long2LongSortedMap m ) { return new UnmodifiableSortedMap ( m ); }
}