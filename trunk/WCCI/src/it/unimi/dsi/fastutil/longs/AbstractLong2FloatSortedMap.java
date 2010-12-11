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

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.AbstractFloatIterator;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;





/** An abstract class providing basic methods for sorted maps implementing a type-specific interface. */

public abstract class AbstractLong2FloatSortedMap extends AbstractLong2FloatMap implements Long2FloatSortedMap {

 public static final long serialVersionUID = -1773560792952436569L;

 protected AbstractLong2FloatSortedMap() {}

 /** Delegates to the corresponding type-specific method. */
 public Long2FloatSortedMap headMap( final Long to ) {
  return headMap( ((to).longValue()) );
 }

 /** Delegates to the corresponding type-specific method. */
 public Long2FloatSortedMap tailMap( final Long from ) {
  return tailMap( ((from).longValue()) );
 }

 /** Delegates to the corresponding type-specific method. */
 public Long2FloatSortedMap subMap( final Long from, final Long to ) {
  return subMap( ((from).longValue()), ((to).longValue()) );
 }


 /** Delegates to the corresponding type-specific method. */
 public Long firstKey() {
  return (Long.valueOf(firstLongKey()));
 }

 /** Delegates to the corresponding type-specific method. */
 public Long lastKey() {
  return (Long.valueOf(lastLongKey()));
 }



 /** Returns a type-specific-sorted-set view of the keys of this map.
	 *
	 * <P>The view is backed by the sorted set returned by {@link #entrySet()}. Note that
	 * <em>no attempt is made at caching the result of this method</em>, as this would
	 * require adding some attributes that lightweight implementations would
	 * not need. Subclasses may easily override this policy by calling
	 * this method and caching the result, but implementors are encouraged to
	 * write more efficient ad-hoc implementations.
	 *
	 * @return a sorted set view of the keys of this map; it may be safely cast to a type-specific interface.
	 */


 public LongSortedSet keySet() {
  return new KeySet();
 }

 /** A wrapper exhibiting the keys of a map. */

 protected class KeySet extends AbstractLongSortedSet {
  public boolean contains( final long k ) { return containsKey( k ); }
  public int size() { return AbstractLong2FloatSortedMap.this.size(); }
  public void clear() { AbstractLong2FloatSortedMap.this.clear(); }

  public LongComparator comparator() { return AbstractLong2FloatSortedMap.this.comparator(); }

  public long firstLong() { return firstLongKey(); }
  public long lastLong() { return lastLongKey(); }

  public LongSortedSet headSet( final long to ) { return headMap( to ).keySet(); }
  public LongSortedSet tailSet( final long from ) { return tailMap( from ).keySet(); }
  public LongSortedSet subSet( final long from, final long to ) { return subMap( from, to ).keySet(); }

  public LongBidirectionalIterator iterator( final long from ) { return new KeySetIterator ( entrySet().iterator( new BasicEntry ( from, (0) ) ) ); }
  public LongBidirectionalIterator iterator() { return new KeySetIterator ( entrySet().iterator() ); }


 }
 /** A wrapper exhibiting a map iterator as an iterator on keys.
	 *
	 * <P>To provide an iterator on keys, just create an instance of this
	 * class using the corresponding iterator on entries.
	 */

 protected static class KeySetIterator extends AbstractLongBidirectionalIterator {
  protected final ObjectBidirectionalIterator<Map.Entry <Long, Float>> i;

  public KeySetIterator( ObjectBidirectionalIterator<Map.Entry <Long, Float>> i ) {
   this.i = i;
  }

  public long nextLong() { return ((i.next().getKey()).longValue()); };
  public long previousLong() { return ((i.previous().getKey()).longValue()); };

  public boolean hasNext() { return i.hasNext(); }
  public boolean hasPrevious() { return i.hasPrevious(); }
 }



 /** Returns a type-specific collection view of the values contained in this map.
	 *
	 * <P>The view is backed by the sorted set returned by {@link #entrySet()}. Note that
	 * <em>no attempt is made at caching the result of this method</em>, as this would
	 * require adding some attributes that lightweight implementations would
	 * not need. Subclasses may easily override this policy by calling
	 * this method and caching the result, but implementors are encouraged to
	 * write more efficient ad-hoc implementations.
	 *
	 * @return a type-specific collection view of the values contained in this map.
	 */

 public FloatCollection values() {
  return new ValuesCollection();
 }

 /** A wrapper exhibiting the values of a map. */
 protected class ValuesCollection extends AbstractFloatCollection {
  public FloatIterator iterator() { return new ValuesIterator ( entrySet().iterator() ); }
  public boolean contains( final float k ) { return containsValue( k ); }
  public int size() { return AbstractLong2FloatSortedMap.this.size(); }
  public void clear() { AbstractLong2FloatSortedMap.this.clear(); }

 }

 /** A wrapper exhibiting a map iterator as an iterator on values.
	 *
	 * <P>To provide an iterator on values, just create an instance of this
	 * class using the corresponding iterator on entries.
	 */

 protected static class ValuesIterator extends AbstractFloatIterator {
  protected final ObjectBidirectionalIterator<Map.Entry <Long, Float>> i;

  public ValuesIterator( ObjectBidirectionalIterator<Map.Entry <Long, Float>> i ) {
   this.i = i;
  }

  public float nextFloat() { return ((i.next().getValue()).floatValue()); };
  public boolean hasNext() { return i.hasNext(); }
 }

 @SuppressWarnings("unchecked")
 public ObjectSortedSet<Map.Entry<Long, Float>> entrySet() {
  return (ObjectSortedSet)long2FloatEntrySet();
 }
}
