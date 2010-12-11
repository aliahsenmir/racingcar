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

import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;

import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import it.unimi.dsi.fastutil.objects.ReferenceCollections;

import it.unimi.dsi.fastutil.objects.ReferenceSets;


import java.util.Map;

/** A class providing static methods and objects that do useful things with type-specific maps.
 *
 * @see it.unimi.dsi.fastutil.Maps
 * @see java.util.Collections
 */

public class Object2ReferenceMaps {

 private Object2ReferenceMaps() {}


 /** An immutable class representing an empty type-specific map.
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific map.
	 */

 public static class EmptyMap <K,V> extends Object2ReferenceFunctions.EmptyFunction <K,V> implements Object2ReferenceMap <K,V>, java.io.Serializable, Cloneable {

  public static final long serialVersionUID = -7046029254386353129L;

  protected EmptyMap() {}

  public boolean containsValue( final Object v ) { return false; }

  public void putAll( final Map<? extends K, ? extends V> m ) { throw new UnsupportedOperationException(); }

  @SuppressWarnings("unchecked")
  public ObjectSet<Object2ReferenceMap.Entry <K,V> > object2ReferenceEntrySet() { return ObjectSets.EMPTY_SET; }

  @SuppressWarnings("unchecked")
  public ObjectSet <K> keySet() { return ObjectSets.EMPTY_SET; }

  @SuppressWarnings("unchecked")
  public ReferenceCollection <V> values() { return ReferenceSets.EMPTY_SET; }





        private Object readResolve() { return EMPTY_MAP; }

  public Object clone() { return EMPTY_MAP; }

  public boolean isEmpty() { return true; }

  @SuppressWarnings("unchecked")
  public ObjectSet<Map.Entry<K, V>> entrySet() { return (ObjectSet)object2ReferenceEntrySet(); }

  public int hashCode() { return 0; }

  public boolean equals( final Object o ) {
   if ( ! ( o instanceof Map ) ) return false;

   return ((Map<?,?>)o).isEmpty();
  }

  public String toString() { return "{}"; }
 }



 /** An empty type-specific map (immutable). It is serializable and cloneable. */

 @SuppressWarnings("unchecked")
 public static final EmptyMap EMPTY_MAP = new EmptyMap();


 /** An immutable class representing a type-specific singleton map.	 
	 *
	 * <P>This class may be useful to implement your own in case you subclass
	 * a type-specific map.
	 */

 public static class Singleton <K,V> extends Object2ReferenceFunctions.Singleton <K,V> implements Object2ReferenceMap <K,V>, java.io.Serializable, Cloneable {

  public static final long serialVersionUID = -7046029254386353129L;

  protected transient volatile ObjectSet<Object2ReferenceMap.Entry <K,V> > entries;
  protected transient volatile ObjectSet <K> keys;
  protected transient volatile ReferenceCollection <V> values;

  protected Singleton( final K key, final V value ) {
   super( key, value );
  }

  public boolean containsValue( final Object v ) { return ( (value) == (v) ); }




  public void putAll( final Map<? extends K, ? extends V> m ) { throw new UnsupportedOperationException(); }

  public ObjectSet<Object2ReferenceMap.Entry <K,V> > object2ReferenceEntrySet() { if ( entries == null ) entries = ObjectSets.singleton( (Object2ReferenceMap.Entry <K,V>)new SingletonEntry() ); return entries; }
  public ObjectSet <K> keySet() { if ( keys == null ) keys = ObjectSets.singleton( key ); return keys; }
  public ReferenceCollection <V> values() { if ( values == null ) values = ReferenceSets.singleton( value ); return values; }

  protected class SingletonEntry implements Object2ReferenceMap.Entry <K,V>, Map.Entry<K,V> {
   public K getKey() { return (Singleton.this.key); }
   public V getValue() { return (Singleton.this.value); }
   public V setValue( final V value ) { throw new UnsupportedOperationException(); }

   public boolean equals( final Object o ) {
    if (!(o instanceof Map.Entry)) return false;
    Map.Entry<?,?> e = (Map.Entry<?,?>)o;

    return ( (Singleton.this.key) == null ? ((e.getKey())) == null : (Singleton.this.key).equals((e.getKey())) ) && ( (Singleton.this.value) == ((e.getValue())) );
   }

   public int hashCode() { return ( (Singleton.this.key) == null ? 0 : (Singleton.this.key).hashCode() ) ^ (System.identityHashCode(Singleton.this.value)); }
   public String toString() { return Singleton.this.key + "->" + Singleton.this.value; }
  }

  public boolean isEmpty() { return false; }

  @SuppressWarnings("unchecked")
  public ObjectSet<Map.Entry<K, V>> entrySet() { return (ObjectSet)object2ReferenceEntrySet(); }

  public int hashCode() { return ( (key) == null ? 0 : (key).hashCode() ) ^ (System.identityHashCode(value)); }

  public boolean equals( final Object o ) {
   if ( o == this ) return true;
   if ( ! ( o instanceof Map ) ) return false;

   Map<?,?> m = (Map<?,?>)o;
   if ( m.size() != 1 ) return false;
   return entrySet().iterator().next().equals( m.entrySet().iterator().next() );
  }

  public String toString() { return "{" + key + "=>" + value + "}"; }
 }

 /** Returns a type-specific immutable map containing only the specified pair. The returned map is serializable and cloneable.
	 *
	 * <P>Note that albeit the returned map is immutable, its default return value may be changed.
	 *
	 * @param key the only key of the returned map.
	 * @param value the only value of the returned map.
	 * @return a type-specific immutable map containing just the pair <code>&lt;key,value></code>.
	 */

 public static <K,V> Object2ReferenceMap <K,V> singleton( final K key, V value ) {
  return new Singleton <K,V>( key, value );
 }
 /** A synchronized wrapper class for maps. */

 public static class SynchronizedMap <K,V> extends Object2ReferenceFunctions.SynchronizedFunction <K,V> implements Object2ReferenceMap <K,V>, java.io.Serializable {

  public static final long serialVersionUID = -7046029254386353129L;

  protected final Object2ReferenceMap <K,V> map;

  protected transient volatile ObjectSet<Object2ReferenceMap.Entry <K,V> > entries;
  protected transient volatile ObjectSet <K> keys;
  protected transient volatile ReferenceCollection <V> values;

  protected SynchronizedMap( final Object2ReferenceMap <K,V> m, final Object sync ) {
   super( m, sync );
   this.map = m;
  }

  protected SynchronizedMap( final Object2ReferenceMap <K,V> m ) {
   super( m );
   this.map = m;
  }

  public int size() { synchronized( sync ) { return map.size(); } }
  public boolean containsKey( final Object k ) { synchronized( sync ) { return map.containsKey( k ); } }
  public boolean containsValue( final Object v ) { synchronized( sync ) { return map.containsValue( v ); } }

  public V defaultReturnValue() { synchronized( sync ) { return map.defaultReturnValue(); } }
  public void defaultReturnValue( final V defRetValue ) { synchronized( sync ) { map.defaultReturnValue( defRetValue ); } }

  public V put( final K k, final V v ) { synchronized( sync ) { return map.put( k, v ); } }

  //public void putAll( final MAP KEY_VALUE_EXTENDS_GENERIC c ) { synchronized( sync ) { map.putAll( c ); } }
  public void putAll( final Map<? extends K, ? extends V> m ) { synchronized( sync ) { map.putAll( m ); } }

  public ObjectSet<Object2ReferenceMap.Entry <K,V> > object2ReferenceEntrySet() { if ( entries == null ) entries = ObjectSets.synchronize( map.object2ReferenceEntrySet(), sync ); return entries; }
  public ObjectSet <K> keySet() { if ( keys == null ) keys = ObjectSets.synchronize( map.keySet(), sync ); return keys; }
  public ReferenceCollection <V> values() { if ( values == null ) return ReferenceCollections.synchronize( map.values(), sync ); return values; }

  public void clear() { synchronized( sync ) { map.clear(); } }
  public String toString() { synchronized( sync ) { return map.toString(); } }
  public V remove( final Object k ) { synchronized( sync ) { return map.remove( k ); } }
  public V get( final Object k ) { synchronized( sync ) { return map.get( k ); } }


  public boolean isEmpty() { synchronized( sync ) { return map.isEmpty(); } }
  public ObjectSet<Map.Entry<K, V>> entrySet() { synchronized( sync ) { return map.entrySet(); } }

  public int hashCode() { synchronized( sync ) { return map.hashCode(); } }
  public boolean equals( final Object o ) { synchronized( sync ) { return map.equals( o ); } }
 }

 /** Returns a synchronized type-specific map backed by the given type-specific map.
	 *
	 * @param m the map to be wrapped in a synchronized map.
	 * @return a synchronized view of the specified map.
	 * @see java.util.Collections#synchronizedMap(Map)
	 */
 public static <K,V> Object2ReferenceMap <K,V> synchronize( final Object2ReferenceMap <K,V> m ) { return new SynchronizedMap <K,V>( m ); }

 /** Returns a synchronized type-specific map backed by the given type-specific map, using an assigned object to synchronize.
	 *
	 * @param m the map to be wrapped in a synchronized map.
	 * @param sync an object that will be used to synchronize the access to the map.
	 * @return a synchronized view of the specified map.
	 * @see java.util.Collections#synchronizedMap(Map)
	 */

 public static <K,V> Object2ReferenceMap <K,V> synchronize( final Object2ReferenceMap <K,V> m, final Object sync ) { return new SynchronizedMap <K,V>( m, sync ); }



 /** An unmodifiable wrapper class for maps. */

 public static class UnmodifiableMap <K,V> extends Object2ReferenceFunctions.UnmodifiableFunction <K,V> implements Object2ReferenceMap <K,V>, java.io.Serializable {

  public static final long serialVersionUID = -7046029254386353129L;

  protected final Object2ReferenceMap <K,V> map;

  protected transient volatile ObjectSet<Object2ReferenceMap.Entry <K,V> > entries;
  protected transient volatile ObjectSet <K> keys;
  protected transient volatile ReferenceCollection <V> values;

  protected UnmodifiableMap( final Object2ReferenceMap <K,V> m ) {
   super( m );
   this.map = m;
  }

  public int size() { return map.size(); }
  public boolean containsKey( final Object k ) { return map.containsKey( k ); }
  public boolean containsValue( final Object v ) { return map.containsValue( v ); }

  public V defaultReturnValue() { throw new UnsupportedOperationException(); }
  public void defaultReturnValue( final V defRetValue ) { throw new UnsupportedOperationException(); }

  public V put( final K k, final V v ) { throw new UnsupportedOperationException(); }

  //public void putAll( final MAP KEY_VALUE_EXTENDS_GENERIC c ) { throw new UnsupportedOperationException(); }
  public void putAll( final Map<? extends K, ? extends V> m ) { throw new UnsupportedOperationException(); }

  public ObjectSet<Object2ReferenceMap.Entry <K,V> > object2ReferenceEntrySet() { if ( entries == null ) entries = ObjectSets.unmodifiable( map.object2ReferenceEntrySet() ); return entries; }
  public ObjectSet <K> keySet() { if ( keys == null ) keys = ObjectSets.unmodifiable( map.keySet() ); return keys; }
  public ReferenceCollection <V> values() { if ( values == null ) return ReferenceCollections.unmodifiable( map.values() ); return values; }

  public void clear() { throw new UnsupportedOperationException(); }
  public String toString() { return map.toString(); }
  public V remove( final Object k ) { throw new UnsupportedOperationException(); }
  public V get( final Object k ) { return map.get( k ); }


  public boolean isEmpty() { return map.isEmpty(); }
  public ObjectSet<Map.Entry<K, V>> entrySet() { return ObjectSets.unmodifiable( map.entrySet() ); }
 }

 /** Returns an unmodifiable type-specific map backed by the given type-specific map.
	 *
	 * @param m the map to be wrapped in an unmodifiable map.
	 * @return an unmodifiable view of the specified map.
	 * @see java.util.Collections#unmodifiableMap(Map)
	 */
 public static <K,V> Object2ReferenceMap <K,V> unmodifiable( final Object2ReferenceMap <K,V> m ) { return new UnmodifiableMap <K,V>( m ); }

}
