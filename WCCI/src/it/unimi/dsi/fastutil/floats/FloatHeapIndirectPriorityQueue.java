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
 * Copyright (C) 2003-2008 Paolo Boldi and Sebastiano Vigna 
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





import it.unimi.dsi.fastutil.ints.IntArrays;

import java.util.NoSuchElementException;

/** A type-specific heap-based indirect priority queue. 
 *
 * <P>Instances of this class use an additional <em>inversion array</em>, of the same length of the reference array,
 * to keep track of the heap position containing a given element of the reference array. The priority queue is
 * represented using a heap. The heap is enlarged as needed, but it is never
 * shrunk. Use the {@link #trim()} method to reduce its size, if necessary.
 *
 * <P>This implementation does <em>not</em> allow one to enqueue several times the same index.
 */

public class FloatHeapIndirectPriorityQueue extends FloatHeapSemiIndirectPriorityQueue {

 /** The inversion array. */
 protected int inv[];

 /** Creates a new empty queue with a given capacity and comparator.
	 *
	 * @param refArray the reference array.
	 * @param capacity the initial capacity of this queue.
	 * @param c the comparator used in this queue, or <code>null</code> for the natural order.
	 */
 public FloatHeapIndirectPriorityQueue( float[] refArray, int capacity, FloatComparator c ) {
  super( refArray, capacity, c );
  if ( capacity > 0 ) this.heap = new int[ capacity ];

  this.refArray = refArray;
  this.c = c;

  this.inv = new int[ refArray.length ];
  IntArrays.fill( inv, -1 );
 }

 /** Creates a new empty queue with a given capacity and using the natural order.
	 *
	 * @param refArray the reference array.
	 * @param capacity the initial capacity of this queue.
	 */
 public FloatHeapIndirectPriorityQueue( float[] refArray, int capacity ) {
  this( refArray, capacity, null );
 }

 /** Creates a new empty queue with capacity equal to the length of the reference array and a given comparator.
	 *
	 * @param refArray the reference array.
	 * @param c the comparator used in this queue, or <code>null</code> for the natural order.
	 */
 public FloatHeapIndirectPriorityQueue( float[] refArray, FloatComparator c ) {
  this( refArray, refArray.length, c );
 }

 /** Creates a new empty queue with capacity equal to the length of the reference array and using the natural order. 
	 * @param refArray the reference array.
	 */
 public FloatHeapIndirectPriorityQueue( float[] refArray ) {
  this( refArray, refArray.length, null );
 }


 /** Wraps a given array in a queue using a given comparator.
	 *
	 * <P>The queue returned by this method will be backed by the given array.
	 * The first <code>size</code> element of the array will be rearranged so to form a heap (this is
	 * more efficient than enqueing the elements of <code>a</code> one by one).
	 *
	 * @param refArray the reference array.
	 * @param a an array of indices into <code>refArray</code>.
	 * @param size the number of elements to be included in the queue.
	 * @param c the comparator used in this queue, or <code>null</code> for the natural order.
	 */
 public FloatHeapIndirectPriorityQueue( final float[] refArray, final int[] a, final int size, final FloatComparator c ) {
  this( refArray, 0, c );
  this.heap = a;
  this.size = size;
  int i = size;
  while( i-- != 0 ) {
   if ( inv[ a[ i ] ] != -1 ) throw new IllegalArgumentException( "Index " + a[ i ] + " appears twice in the heap" );
   inv[ a[ i ] ] = i;
  }
  FloatIndirectHeaps.makeHeap( refArray, a, inv, size, c );
 }


 /** Wraps a given array in a queue using a given comparator.
	 *
	 * <P>The queue returned by this method will be backed by the given array.
	 * The elements of the array will be rearranged so to form a heap (this is
	 * more efficient than enqueing the elements of <code>a</code> one by one).
	 *
	 * @param refArray the reference array.
	 * @param a an array of indices into <code>refArray</code>.
	 * @param c the comparator used in this queue, or <code>null</code> for the natural order.
	 */
 public FloatHeapIndirectPriorityQueue( final float[] refArray, final int[] a, final FloatComparator c ) {
  this( refArray, a, a.length, c );
 }

 /** Wraps a given array in a queue using the natural order.
	 *
	 * <P>The queue returned by this method will be backed by the given array.
	 * The first <code>size</code> element of the array will be rearranged so to form a heap (this is
	 * more efficient than enqueing the elements of <code>a</code> one by one).
	 *
	 * @param refArray the reference array.
	 * @param a an array of indices into <code>refArray</code>.
	 * @param size the number of elements to be included in the queue.
	 */
 public FloatHeapIndirectPriorityQueue( final float[] refArray, final int[] a, int size ) {
  this( refArray, a, size, null );
 }


 /** Wraps a given array in a queue using the natural order.
	 *
	 * <P>The queue returned by this method will be backed by the given array.
	 * The elements of the array will be rearranged so to form a heap (this is
	 * more efficient than enqueing the elements of <code>a</code> one by one).
	 *
	 * @param refArray the reference array.
	 * @param a an array of indices into <code>refArray</code>.
	 */
 public FloatHeapIndirectPriorityQueue( final float[] refArray, final int[] a ) {
  this( refArray, a, a.length );
 }

 public void enqueue( final int x ) {
  if ( inv[ x ] >= 0 ) throw new IllegalArgumentException( "Index " + x + " belongs to the queue" );

  if ( size == heap.length ) heap = IntArrays.grow( heap, size + 1 );

  inv[ heap[ size ] = x ] = size++;

  FloatIndirectHeaps.upHeap( refArray, heap, inv, size, size - 1, c );
 }

 public int dequeue() {
  if ( size == 0 ) throw new NoSuchElementException();
  final int result = heap[ 0 ];
  if ( --size != 0 ) inv[ heap[ 0 ] = heap[ size ] ] = 0;
  inv[ result ] = -1;

  if ( size != 0 ) FloatIndirectHeaps.downHeap( refArray, heap, inv, size, 0, c );
  return result;
 }

 public void changed() {
  FloatIndirectHeaps.downHeap( refArray, heap, inv, size, 0, c );
 }

 public void changed( final int index ) {
  final int pos = inv[ index ];
  if ( pos < 0 ) throw new IllegalArgumentException( "Index " + index + " does not belong to the queue" );
  final int newPos = FloatIndirectHeaps.upHeap( refArray, heap, inv, size, pos, c );
  FloatIndirectHeaps.downHeap( refArray, heap, inv, size, newPos, c );
 }

 /** Rebuilds this heap in a bottom-up fashion.
	 */

 public void allChanged() {
  FloatIndirectHeaps.makeHeap( refArray, heap, inv, size, c );
 }


 public void remove( final int index ) {
  final int result = inv[ index ];
  if ( result < 0 ) throw new IllegalArgumentException( "Index " + index + " does not belong to the queue" );
  inv[ index ] = -1;

  if ( result < --size ) {
   inv[ heap[ result ] = heap[ size ] ] = result;
   final int newPos = FloatIndirectHeaps.upHeap( refArray, heap, inv, size, result, c );
   FloatIndirectHeaps.downHeap( refArray, heap, inv, size, newPos, c );
  }
 }


 public void clear() {
  size = 0;
  IntArrays.fill( inv, -1 );
 }
}
