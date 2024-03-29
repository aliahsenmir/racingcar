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

package it.unimi.dsi.fastutil.floats;

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

public class Float2ByteSortedMaps {

 private Float2ByteSortedMaps() {}

 /** Returns a comparator for entries based on a given comparator on keys.
	 *
	 * @param comparator a comparator on keys.
	 * @return the associated comparator on entries.
	 */
 public static Comparator<? super Map.Entry<Float, ?>> entryComparator( final FloatComparator comparator ) {
  return new Comparator<Map.Entry<Float, ?>>() {
   public int compare( Map.Entry<Float, ?> x, Map.Entry<Float, ?> y ) {
    return comparator.compare( x.getKey(), y.getKey() );
   }
  };
 }


 /** An immutable class representing an empty type-specific sorted map. 
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted map.
	 */

 public static class EmptySortedMap extends Float2ByteMaps.EmptyMap implements Float2ByteSortedMap , java.io.Serializable, Cloneable {

  public static final long serialVersionUID = -7046029254386353129L;

  protected EmptySortedMap() {}

  public FloatComparator comparator() { return null; }

  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Float2ByteMap.Entry > float2ByteEntrySet() { return ObjectSortedSets.EMPTY_SET; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Map.Entry<Float, Byte>> entrySet() { return ObjectSortedSets.EMPTY_SET; }

  @SuppressWarnings("unchecked")
  public FloatSortedSet keySet() { return FloatSortedSets.EMPTY_SET; }

  @SuppressWarnings("unchecked")
  public Float2ByteSortedMap subMap( final float from, final float to ) { return EMPTY_MAP; }

  @SuppressWarnings("unchecked")
  public Float2ByteSortedMap headMap( final float to ) { return EMPTY_MAP; }

  @SuppressWarnings("unchecked")
  public Float2ByteSortedMap tailMap( final float from ) { return EMPTY_MAP; }

  public float firstFloatKey() { throw new NoSuchElementException(); }
  public float lastFloatKey() { throw new NoSuchElementException(); }


  public Float2ByteSortedMap headMap( Float oto ) { return headMap( ((oto).floatValue()) ); }
  public Float2ByteSortedMap tailMap( Float ofrom ) { return tailMap( ((ofrom).floatValue()) ); }
  public Float2ByteSortedMap subMap( Float ofrom, Float oto ) { return subMap( ((ofrom).floatValue()), ((oto).floatValue()) ); }

  public Float firstKey() { return (Float.valueOf(firstFloatKey())); }
  public Float lastKey() { return (Float.valueOf(lastFloatKey())); }


 }



 /** An empty type-specific sorted map (immutable). It is serializable and cloneable. */

 @SuppressWarnings("unchecked")
 public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();


 /** An immutable class representing a type-specific singleton sorted map. 
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific sorted map.
	 */

 public static class Singleton extends Float2ByteMaps.Singleton implements Float2ByteSortedMap , java.io.Serializable, Cloneable {

  public static final long serialVersionUID = -7046029254386353129L;

  protected final FloatComparator comparator;

  protected Singleton( final float key, final byte value, FloatComparator comparator ) {
   super( key, value );
   this.comparator = comparator;
  }

  protected Singleton( final float key, final byte value ) {
   this( key, value, null );
  }

  @SuppressWarnings("unchecked")
  final int compare( final float k1, final float k2 ) {
   return comparator == null ? ( (k1) < (k2) ? -1 : ( (k1) == (k2) ? 0 : 1 ) ) : comparator.compare( k1, k2 );
  }

  public FloatComparator comparator() { return comparator; }

  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Float2ByteMap.Entry > float2ByteEntrySet() { if ( entries == null ) entries = ObjectSortedSets.singleton( (Float2ByteMap.Entry )new SingletonEntry(), (Comparator<? super Float2ByteMap.Entry >)entryComparator( comparator ) ); return (ObjectSortedSet<Float2ByteMap.Entry >)entries; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Map.Entry<Float, Byte>> entrySet() { return (ObjectSortedSet)float2ByteEntrySet(); }

  public FloatSortedSet keySet() { if ( keys == null ) keys = FloatSortedSets.singleton( key, comparator ); return (FloatSortedSet )keys; }

  @SuppressWarnings("unchecked")
  public Float2ByteSortedMap subMap( final float from, final float to ) { if ( compare( from, key ) <= 0 && compare( key, to ) < 0 ) return this; return EMPTY_MAP; }

  @SuppressWarnings("unchecked")
  public Float2ByteSortedMap headMap( final float to ) { if ( compare( key, to ) < 0 ) return this; return EMPTY_MAP; }

  @SuppressWarnings("unchecked")
  public Float2ByteSortedMap tailMap( final float from ) { if ( compare( from, key ) <= 0 ) return this; return EMPTY_MAP; }

  public float firstFloatKey() { return key; }
  public float lastFloatKey() { return key; }


  public Float2ByteSortedMap headMap( Float oto ) { return headMap( ((oto).floatValue()) ); }
  public Float2ByteSortedMap tailMap( Float ofrom ) { return tailMap( ((ofrom).floatValue()) ); }
  public Float2ByteSortedMap subMap( Float ofrom, Float oto ) { return subMap( ((ofrom).floatValue()), ((oto).floatValue()) ); }

  public Float firstKey() { return (Float.valueOf(firstFloatKey())); }
  public Float lastKey() { return (Float.valueOf(lastFloatKey())); }

 }

 /** Returns a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <P>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair <code>&lt;key,value></code>.
	 */

 public static Float2ByteSortedMap singleton( final Float key, Byte value ) {
  return new Singleton ( ((key).floatValue()), ((value).byteValue()) );
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

 public static Float2ByteSortedMap singleton( final Float key, Byte value, FloatComparator comparator ) {
  return new Singleton ( ((key).floatValue()), ((value).byteValue()), comparator );
 }



 /** Returns a type-specific immutable sorted map containing only the specified pair. The returned sorted map is serializable and cloneable.
	 *
	 * <P>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned sorted map.
	 * @param value the only value of the returned sorted map.
	 * @return a type-specific immutable sorted map containing just the pair <code>&lt;key,value></code>.
	 */

 public static Float2ByteSortedMap singleton( final float key, final byte value ) {
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

 public static Float2ByteSortedMap singleton( final float key, final byte value, FloatComparator comparator ) {
  return new Singleton ( key, value, comparator );
 }




  /** A synchronized wrapper class for sorted maps. */

 public static class SynchronizedSortedMap extends Float2ByteMaps.SynchronizedMap implements Float2ByteSortedMap , java.io.Serializable {

  public static final long serialVersionUID = -7046029254386353129L;

  protected final Float2ByteSortedMap sortedMap;

  protected SynchronizedSortedMap( final Float2ByteSortedMap m, final Object sync ) {
   super( m, sync );
   sortedMap = m;
  }

  protected SynchronizedSortedMap( final Float2ByteSortedMap m ) {
   super( m );
   sortedMap = m;
  }

  public FloatComparator comparator() { synchronized( sync ) { return sortedMap.comparator(); } }

  public ObjectSortedSet<Float2ByteMap.Entry > float2ByteEntrySet() { if ( entries == null ) entries = ObjectSortedSets.synchronize( sortedMap.float2ByteEntrySet(), sync ); return (ObjectSortedSet<Float2ByteMap.Entry >)entries; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Map.Entry<Float, Byte>> entrySet() { return (ObjectSortedSet)float2ByteEntrySet(); }
  public FloatSortedSet keySet() { if ( keys == null ) keys = FloatSortedSets.synchronize( sortedMap.keySet(), sync ); return (FloatSortedSet )keys; }

  public Float2ByteSortedMap subMap( final float from, final float to ) { return new SynchronizedSortedMap ( sortedMap.subMap( from, to ), sync ); }
  public Float2ByteSortedMap headMap( final float to ) { return new SynchronizedSortedMap ( sortedMap.headMap( to ), sync ); }
  public Float2ByteSortedMap tailMap( final float from ) { return new SynchronizedSortedMap ( sortedMap.tailMap( from ), sync ); }

  public float firstFloatKey() { synchronized( sync ) { return sortedMap.firstFloatKey(); } }
  public float lastFloatKey() { synchronized( sync ) { return sortedMap.lastFloatKey(); } }


  public Float firstKey() { synchronized( sync ) { return sortedMap.firstKey(); } }
  public Float lastKey() { synchronized( sync ) { return sortedMap.lastKey(); } }

  public Float2ByteSortedMap subMap( final Float from, final Float to ) { return new SynchronizedSortedMap ( sortedMap.subMap( from, to ), sync ); }
  public Float2ByteSortedMap headMap( final Float to ) { return new SynchronizedSortedMap ( sortedMap.headMap( to ), sync ); }
  public Float2ByteSortedMap tailMap( final Float from ) { return new SynchronizedSortedMap ( sortedMap.tailMap( from ), sync ); }



 }

 /** Returns a synchronized type-specific sorted map backed by the given type-specific sorted map.
	 *
	 * @param m the sorted map to be wrapped in a synchronized sorted map.
	 * @return a synchronized view of the specified sorted map.
	 * @see java.util.Collections#synchronizedSortedMap(SortedMap)
	 */
 public static Float2ByteSortedMap synchronize( final Float2ByteSortedMap m ) { return new SynchronizedSortedMap ( m ); }

 /** Returns a synchronized type-specific sorted map backed by the given type-specific sorted map, using an assigned object to synchronize.
	 *
	 * @param m the sorted map to be wrapped in a synchronized sorted map.
	 * @param sync an object that will be used to synchronize the access to the sorted sorted map.
	 * @return a synchronized view of the specified sorted map.
	 * @see java.util.Collections#synchronizedSortedMap(SortedMap)
	 */

 public static Float2ByteSortedMap synchronize( final Float2ByteSortedMap m, final Object sync ) { return new SynchronizedSortedMap ( m, sync ); }




 /** An unmodifiable wrapper class for sorted maps. */

 public static class UnmodifiableSortedMap extends Float2ByteMaps.UnmodifiableMap implements Float2ByteSortedMap , java.io.Serializable {

  public static final long serialVersionUID = -7046029254386353129L;

  protected final Float2ByteSortedMap sortedMap;

  protected UnmodifiableSortedMap( final Float2ByteSortedMap m ) {
   super( m );
   sortedMap = m;
  }

  public FloatComparator comparator() { return sortedMap.comparator(); }

  public ObjectSortedSet<Float2ByteMap.Entry > float2ByteEntrySet() { if ( entries == null ) entries = ObjectSortedSets.unmodifiable( sortedMap.float2ByteEntrySet() ); return (ObjectSortedSet<Float2ByteMap.Entry >)entries; }
  @SuppressWarnings("unchecked")
  public ObjectSortedSet<Map.Entry<Float, Byte>> entrySet() { return (ObjectSortedSet)float2ByteEntrySet(); }
  public FloatSortedSet keySet() { if ( keys == null ) keys = FloatSortedSets.unmodifiable( sortedMap.keySet() ); return (FloatSortedSet )keys; }

  public Float2ByteSortedMap subMap( final float from, final float to ) { return new UnmodifiableSortedMap ( sortedMap.subMap( from, to ) ); }
  public Float2ByteSortedMap headMap( final float to ) { return new UnmodifiableSortedMap ( sortedMap.headMap( to ) ); }
  public Float2ByteSortedMap tailMap( final float from ) { return new UnmodifiableSortedMap ( sortedMap.tailMap( from ) ); }

  public float firstFloatKey() { return sortedMap.firstFloatKey(); }
  public float lastFloatKey() { return sortedMap.lastFloatKey(); }


  public Float firstKey() { return sortedMap.firstKey(); }
  public Float lastKey() { return sortedMap.lastKey(); }

  public Float2ByteSortedMap subMap( final Float from, final Float to ) { return new UnmodifiableSortedMap ( sortedMap.subMap( from, to ) ); }
  public Float2ByteSortedMap headMap( final Float to ) { return new UnmodifiableSortedMap ( sortedMap.headMap( to ) ); }
  public Float2ByteSortedMap tailMap( final Float from ) { return new UnmodifiableSortedMap ( sortedMap.tailMap( from ) ); }



 }

 /** Returns an unmodifiable type-specific sorted map backed by the given type-specific sorted map.
	 *
	 * @param m the sorted map to be wrapped in an unmodifiable sorted map.
	 * @return an unmodifiable view of the specified sorted map.
	 * @see java.util.Collections#unmodifiableSortedMap(SortedMap)
	 */
 public static Float2ByteSortedMap unmodifiable( final Float2ByteSortedMap m ) { return new UnmodifiableSortedMap ( m ); }
}
