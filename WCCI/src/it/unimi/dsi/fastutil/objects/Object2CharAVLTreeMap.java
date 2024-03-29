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

package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.NoSuchElementException;


import it.unimi.dsi.fastutil.chars.CharListIterator;


/** A type-specific AVL tree map with a fast, small-footprint implementation.
 *
 * <P>The iterators provided by the views of this class are type-specific {@linkplain
 * it.unimi.dsi.fastutil.BidirectionalIterator bidirectional iterators}.
 * Moreover, the iterator returned by <code>iterator()</code> can be safely cast
 * to a type-specific {@linkplain java.util.ListIterator list iterator}.
 */

public class Object2CharAVLTreeMap <K> extends AbstractObject2CharSortedMap <K> implements java.io.Serializable, Cloneable {

 /** A reference to the root entry. */
 protected transient Entry <K> tree;

 /** Number of entries in this map. */
 protected int count;

 /** The first key in this map. */
 protected transient Entry <K> firstEntry;

 /** The last key in this map. */
 protected transient Entry <K> lastEntry;

 /** Cached set of entries. */
 protected transient volatile ObjectSortedSet<Object2CharMap.Entry <K> > entries;

 /** Cached set of keys. */
 protected transient volatile ObjectSortedSet <K> keys;

 /** Cached collection of values. */
 protected transient volatile CharCollection values;

 /** The value of this variable remembers, after a <code>put()</code> 
	 * or a <code>remove()</code>, whether the <em>domain</em> of the map
	 * has been modified. */
 protected transient boolean modified;

 /** This map's comparator, as provided in the constructor. */
 protected Comparator<? super K> storedComparator;

 /** This map's actual comparator; it may differ from {@link #storedComparator} because it is
		always a type-specific comparator, so it could be derived from the former by wrapping. */
 protected transient Comparator <? super K> actualComparator;

 public static final long serialVersionUID = -7046029254386353129L;

 private static final boolean ASSERTS = false;

 {
  allocatePaths();
 }

 /** Creates a new empty tree map. 
	 */

 public Object2CharAVLTreeMap() {
  tree = null;
  count = 0;
 }

 /** Generates the comparator that will be actually used.
	 *
	 * <P>When a specific {@link Comparator} is specified and stored in {@link
	 * #storedComparator}, we must check whether it is type-specific.  If it is
	 * so, we can used directly, and we store it in {@link #actualComparator}. Otherwise,
	 * we generate on-the-fly an anonymous class that wraps the non-specific {@link Comparator}
	 * and makes it into a type-specific one.
	 */
 @SuppressWarnings("unchecked")
 private void setActualComparator() {

  actualComparator = storedComparator;
 }


 /** Creates a new empty tree map with the given comparator.
	 *
	 * @param c a (possibly type-specific) comparator.
	 */

 public Object2CharAVLTreeMap( final Comparator<? super K> c ) {
  this();
  storedComparator = c;
  setActualComparator();
 }


 /** Creates a new tree map copying a given map.
	 *
	 * @param m a {@link Map} to be copied into the new tree map. 
	 */

 public Object2CharAVLTreeMap( final Map<? extends K, ? extends Character> m ) {
  this();
  putAll( m );
 }

 /** Creates a new tree map copying a given sorted map (and its {@link Comparator}).
	 *
	 * @param m a {@link SortedMap} to be copied into the new tree map. 
	 */

 public Object2CharAVLTreeMap( final SortedMap<K,Character> m ) {
  this( m.comparator() );
  putAll( m );
 }

 /** Creates a new tree map copying a given map.
	 *
	 * @param m a type-specific map to be copied into the new tree map. 
	 */

 public Object2CharAVLTreeMap( final Object2CharMap <? extends K> m ) {
  this();
  putAll( m );
 }

 /** Creates a new tree map copying a given sorted map (and its {@link Comparator}).
	 *
	 * @param m a type-specific sorted map to be copied into the new tree map. 
	 */

 public Object2CharAVLTreeMap( final Object2CharSortedMap <K> m ) {
  this( m.comparator() );
  putAll( m );
 }

 /** Creates a new tree map using the elements of two parallel arrays and the given comparator.
	 *
	 * @param k the array of keys of the new tree map.
	 * @param v the array of corresponding values in the new tree map.
	 * @param c a (possibly type-specific) comparator.
	 * @throws IllegalArgumentException if <code>k</code> and <code>v</code> have different lengths.
	 */

 public Object2CharAVLTreeMap( final K[] k, final char v[], final Comparator<? super K> c ) {
  this( c );
  if ( k.length != v.length ) throw new IllegalArgumentException( "The key array and the value array have different lengths (" + k.length + " and " + v.length + ")" );
  for( int i = 0; i < k.length; i++ ) this.put( k[ i ], v[ i ] );
 }

 /** Creates a new tree map using the elements of two parallel arrays.
	 *
	 * @param k the array of keys of the new tree map.
	 * @param v the array of corresponding values in the new tree map.
	 * @throws IllegalArgumentException if <code>k</code> and <code>v</code> have different lengths.
	 */

 public Object2CharAVLTreeMap( final K[] k, final char v[] ) {
  this( k, v, null );
 }

 /*
	 * The following methods implements some basic building blocks used by
	 * all accessors.  They are (and should be maintained) identical to those used in AVLTreeSet.drv.
	 *
	 * The put()/remove() code is derived from Ben Pfaff's GNU libavl
	 * (http://www.msu.edu/~pfaffben/avl/). If you want to understand what's
	 * going on, you should have a look at the literate code contained therein
	 * first.  
	 */


 /** Compares two keys in the right way. 
	 *
	 * <P>This method uses the {@link #actualComparator} if it is non-<code>null</code>.
	 * Otherwise, it resorts to primitive type comparisons or to {@link Comparable#compareTo(Object) compareTo()}.
	 *
	 * @param k1 the first key.
	 * @param k2 the second key.
	 * @return a number smaller than, equal to or greater than 0, as usual
	 * (i.e., when k1 &lt; k2, k1 = k2 or k1 &gt; k2, respectively).
	 */

 @SuppressWarnings("unchecked")
 final int compare( final K k1, final K k2 ) {
  return actualComparator == null ? ( ((Comparable<K>)(k1)).compareTo(k2) ) : actualComparator.compare( k1, k2 );
 }



 /** Returns the entry corresponding to the given key, if it is in the tree; <code>null</code>, otherwise.
	 *
	 * @param k the key to search for.
	 * @return the corresponding entry, or <code>null</code> if no entry with the given key exists.
	 */

 final Entry <K> findKey( final K k ) {
  Entry <K> e = tree;
  int cmp;

  while ( e != null && ( cmp = compare( k, e.key ) ) != 0 ) e = cmp < 0 ? e.left() : e.right();

  return e;
 }

 /** Locates a key.
	 *
	 * @param k a key.
	 * @return the last entry on a search for the given key; this will be
	 * the given key, if it present; otherwise, it will be either the smallest greater key or the greatest smaller key.
	 */

 final Entry <K> locateKey( final K k ) {
  Entry <K> e = tree, last = tree;
  int cmp = 0;

  while ( e != null && ( cmp = compare( k, e.key ) ) != 0 ) {
   last = e;
   e = cmp < 0 ? e.left() : e.right();
  }

  return cmp == 0 ? e : last;
 }

 /** This vector remembers the directions followed during 
	 * the current insertion. It suffices for about 2<sup>32</sup> entries. */
 private transient boolean dirPath[];

 private void allocatePaths() {
  dirPath = new boolean[ 48 ];
 }


 /* After execution of this method, modified is true iff a new entry has
	been inserted. */

 public char put( final K k, final char v ) {
  modified = false;

  if ( tree == null ) { // The case of the empty tree is treated separately.
   count++;
   tree = lastEntry = firstEntry = new Entry <K>( k, v );
   modified = true;
  }
  else {
   Entry <K> p = tree, q = null, y = tree, z = null, e = null, w = null;
   int cmp, i = 0;

   while( true ) {
    if ( ( cmp = compare( k, p.key ) ) == 0 ) {
     final char oldValue = p.value;
     p.value = v;
     return oldValue;
    }

    if ( (byte)p.info != 0 ) {
     i = 0;
     z = q;
     y = p;
    }

    if ( dirPath[ i++ ] = cmp > 0 ) {
     if ( p.succ() ) {
      count++;
      e = new Entry <K>( k, v );

      modified = true;
      if ( p.right == null ) lastEntry = e;

      e.left = p;
      e.right = p.right;

      p.right( e );

      break;
     }

     q = p;
     p = p.right;
    }
    else {
     if ( p.pred() ) {
      count++;
      e = new Entry <K>( k, v );

      modified = true;
      if ( p.left == null ) firstEntry = e;

      e.right = p;
      e.left = p.left;

      p.left( e );

      break;
     }

     q = p;
     p = p.left;
    }
   }

   p = y;
   i = 0;

   while( p != e ) {
    if ( dirPath[ i ] ) p.incBalance();
    else p.decBalance();

    p = dirPath[ i++ ] ? p.right : p.left;
   }

   if ( (byte)y.info == -2 ) {
    Entry <K> x = y.left;

    if ( (byte)x.info == -1 ) {
     w = x;
     if ( x.succ() ) {
      x.succ( false );
      y.pred( x );
     }
     else y.left = x.right;

     x.right = y;
     x.balance( 0 );
     y.balance( 0 );
    }
    else {
     if ( ASSERTS ) assert (byte)x.info == 1;

     w = x.right;
     x.right = w.left;
     w.left = x;
     y.left = w.right;
     w.right = y;
     if ( (byte)w.info == -1 ) {
      x.balance( 0 );
      y.balance( 1 );
     }
     else if ( (byte)w.info == 0 ) {
      x.balance( 0 );
      y.balance( 0 );
     }
     else {
      x.balance( -1 );
      y.balance( 0 );
     }
     w.balance( 0 );


     if ( w.pred() ) {
      x.succ( w );
      w.pred( false );
     }
     if ( w.succ() ) {
      y.pred( w );
      w.succ( false );
     }

    }
   }
   else if ( (byte)y.info == +2 ) {
    Entry <K> x = y.right;

    if ( (byte)x.info == 1 ) {
     w = x;
     if ( x.pred() ) {
      x.pred( false );
      y.succ( x );
     }
     else y.right = x.left;

     x.left = y;
     x.balance( 0 );
     y.balance( 0 );
    }
    else {
     if ( ASSERTS ) assert (byte)x.info == -1;

     w = x.left;
     x.left = w.right;
     w.right = x;
     y.right = w.left;
     w.left = y;
     if ( (byte)w.info == 1 ) {
      x.balance( 0 );
      y.balance( -1 );
     }
     else if ( (byte)w.info == 0 ) {
      x.balance( 0 );
      y.balance( 0 );
     }
     else {
      x.balance( 1 );
      y.balance( 0 );
     }
     w.balance( 0 );


     if ( w.pred() ) {
      y.succ( w );
      w.pred( false );
     }
     if ( w.succ() ) {
      x.pred( w );
      w.succ( false );
     }

    }
   }
   else return defRetValue;

   if ( z == null ) tree = w;
   else {
    if ( z.left == y ) z.left = w;
    else z.right = w;
   }
  }

  if ( ASSERTS ) checkTree( tree );
  return defRetValue;
 }

 /** Finds the parent of an entry.
	 *
	 * @param e a node of the tree.
	 * @return the parent of the given node, or <code>null</code> for the root.
	 */

 private Entry <K> parent( final Entry <K> e ) {
  if ( e == tree ) return null;

  Entry <K> x, y, p;
  x = y = e;

  while( true ) {
   if ( y.succ() ) {
    p = y.right;
    if ( p == null || p.left != e ) {
     while( ! x.pred() ) x = x.left;
     p = x.left;
    }
    return p;
   }
   else if ( x.pred() ) {
    p = x.left;
    if ( p == null || p.right != e ) {
     while( ! y.succ() ) y = y.right;
     p = y.right;
    }
    return p;
   }

   x = x.left;
   y = y.right;
  }
 }


 /* After execution of this method, {@link #modified} is true iff an entry
	has been deleted. */

 @SuppressWarnings("unchecked")
 public char removeChar( final Object k ) {
  modified = false;

  if ( tree == null ) return defRetValue;

  int cmp;
  Entry <K> p = tree, q = null;
  boolean dir = false;
  final K kk = (K) k;

  while( true ) {
   if ( ( cmp = compare( kk, p.key ) ) == 0 ) break;
   else if ( dir = cmp > 0 ) {
    q = p;
    if ( ( p = p.right() ) == null ) return defRetValue;
   }
   else {
    q = p;
    if ( ( p = p.left() ) == null ) return defRetValue;
   }
  }

  if ( p.left == null ) firstEntry = p.next();
  if ( p.right == null ) lastEntry = p.prev();

  if ( p.succ() ) {
   if ( p.pred() ) {
    if ( q != null ) {
     if ( dir ) q.succ( p.right );
     else q.pred( p.left );
    }
    else tree = dir ? p.right : p.left;
   }
   else {
    p.prev().right = p.right;

    if ( q != null ) {
     if ( dir ) q.right = p.left;
     else q.left = p.left;
    }
    else tree = p.left;
   }
  }
  else {
   Entry <K> r = p.right;

   if ( r.pred() ) {
    r.left = p.left;
    r.pred( p.pred() );
    if ( ! r.pred() ) r.prev().right = r;
    if ( q != null ) {
     if ( dir ) q.right = r;
     else q.left = r;
    }
    else tree = r;

    r.balance( (byte)p.info );
    q = r;
    dir = true;

   }
   else {
    Entry <K> s;

    while( true ) {
     s = r.left;
     if ( s.pred() ) break;
     r = s;
    }

    if ( s.succ() ) r.pred( s );
    else r.left = s.right;

    s.left = p.left;

    if ( ! p.pred() ) {
     p.prev().right = s;
     s.pred( false );
    }

    s.right = p.right;
    s.succ( false );

    if ( q != null ) {
     if ( dir ) q.right = s;
     else q.left = s;
    }
    else tree = s;

    s.balance( (byte)p.info );
    q = r;
    dir = false;
   }
  }

  Entry <K> y;

  while( q != null ) {
   y = q;
   q = parent( y );

   if ( ! dir ) {
    dir = q != null && q.left != y;
    y.incBalance();

    if ( (byte)y.info == 1 ) break;
    else if ( (byte)y.info == 2 ) {

     Entry <K> x = y.right;
     if ( ASSERTS ) assert x != null;

     if ( (byte)x.info == -1 ) {
      Entry <K> w;

      if ( ASSERTS ) assert (byte)x.info == -1;

      w = x.left;
      x.left = w.right;
      w.right = x;
      y.right = w.left;
      w.left = y;

      if ( (byte)w.info == 1 ) {
       x.balance( 0 );
       y.balance( -1 );
      }
      else if ( (byte)w.info == 0 ) {
       x.balance( 0 );
       y.balance( 0 );
      }
      else {
       if ( ASSERTS ) assert (byte)w.info == -1;

       x.balance( 1 );
       y.balance( 0 );
      }

      w.balance( 0 );

      if ( w.pred() ) {
       y.succ( w );
       w.pred( false );
      }
      if ( w.succ() ) {
       x.pred( w );
       w.succ( false );
      }

      if ( q != null ) {
       if ( dir ) q.right = w;
       else q.left = w;
      }
      else tree = w;
     }
     else {
      if ( q != null ) {
       if ( dir ) q.right = x;
       else q.left = x;
      }
      else tree = x;

      if ( (byte)x.info == 0 ) {
       y.right = x.left;
       x.left = y;
       x.balance( -1 );
       y.balance( +1 );
       break;
      }
      if ( ASSERTS ) assert (byte)x.info == 1;

      if ( x.pred() ) {
       y.succ( true );
       x.pred( false );
      }
      else y.right = x.left;

      x.left = y;
      y.balance( 0 );
      x.balance( 0 );
     }
    }
   }
   else {
    dir = q != null && q.left != y;
    y.decBalance();

    if ( (byte)y.info == -1 ) break;
    else if ( (byte)y.info == -2 ) {

     Entry <K> x = y.left;
     if ( ASSERTS ) assert x != null;

     if ( (byte)x.info == 1 ) {
      Entry <K> w;

      if ( ASSERTS ) assert (byte)x.info == 1;

      w = x.right;
      x.right = w.left;
      w.left = x;
      y.left = w.right;
      w.right = y;

      if ( (byte)w.info == -1 ) {
       x.balance( 0 );
       y.balance( 1 );
      }
      else if ( (byte)w.info == 0 ) {
       x.balance( 0 );
       y.balance( 0 );
      }
      else {
       if ( ASSERTS ) assert (byte)w.info == 1;

       x.balance( -1 );
       y.balance( 0 );
      }

      w.balance( 0 );

      if ( w.pred() ) {
       x.succ( w );
       w.pred( false );
      }
      if ( w.succ() ) {
       y.pred( w );
       w.succ( false );
      }

      if ( q != null ) {
       if ( dir ) q.right = w;
       else q.left = w;
      }
      else tree = w;
     }
     else {
      if ( q != null ) {
       if ( dir ) q.right = x;
       else q.left = x;
      }
      else tree = x;

      if ( (byte)x.info == 0 ) {
       y.left = x.right;
       x.right = y;
       x.balance( +1 );
       y.balance( -1 );
       break;
      }
      if ( ASSERTS ) assert (byte)x.info == -1;

      if ( x.succ() ) {
       y.pred( true );
       x.succ( false );
      }
      else y.left = x.right;

      x.right = y;
      y.balance( 0 );
      x.balance( 0 );
     }
    }
   }
  }

  modified = true;
  count--;
  if ( ASSERTS ) checkTree( tree );
  return p.value;
 }



 public Character put( final K ok, final Character ov ) {
  final char oldValue = put( (ok), ((ov).charValue()) );
  return modified ? (null) : (Character.valueOf(oldValue));
 }





 public Character remove( final Object ok ) {
  final char oldValue = removeChar( (ok) );
  return modified ? (Character.valueOf(oldValue)) : (null);
 }



 public boolean containsValue( final char v ) {
  final ValueIterator i = new ValueIterator();
  char ev;

  int j = count;
  while( j-- != 0 ) {
   ev = i.nextChar();
   if ( ( (ev) == (v) ) ) return true;
  }

  return false;
 }

 public void clear() {
  count = 0;
  tree = null;
  entries = null;
  values = null;
  keys = null;
  firstEntry = lastEntry = null;
 }


 /** This class represent an entry in a tree map.
	 *
	 * <P>We use the only "metadata", i.e., {@link Entry#info}, to store
	 * information about balance, predecessor status and successor status.
	 *
	 * <P>Note that since the class is recursive, it can be
	 * considered equivalently a tree.
	 */

 private static final class Entry <K> implements Cloneable, Object2CharMap.Entry <K> {
  /** If the bit in this mask is true, {@link #right} points to a successor. */
  private final static int SUCC_MASK = 1 << 31;
  /** If the bit in this mask is true, {@link #left} points to a predecessor. */
  private final static int PRED_MASK = 1 << 30;
  /** The bits in this mask hold the node balance info. You can get it just by casting to byte. */
  private final static int BALANCE_MASK = 0xFF;
  /** The key of this entry. */
  K key;
  /** The value of this entry. */
  char value;
  /** The pointers to the left and right subtrees. */
  Entry <K> left, right;
  /** This integers holds different information in different bits (see {@link #SUCC_MASK}, {@link #PRED_MASK} and {@link #BALANCE_MASK}). */
  int info;

  Entry() {}

  /** Creates a new entry with the given key and value.
		 *
		 * @param k a key.
		 * @param v a value.
		 */
  Entry( final K k, final Character v ) {
   this.key = (k);
   this.value = ((v).charValue());
   info = SUCC_MASK | PRED_MASK;
  }



  /** Creates a new entry with the given key and value.
		 *
		 * @param k a key.
		 * @param v a value.
		 */
  Entry( final K k, final char v ) {
   this.key = k;
   this.value = v;
   info = SUCC_MASK | PRED_MASK;
  }



  /** Returns the left subtree. 
		 *
		 * @return the left subtree (<code>null</code> if the left
		 * subtree is empty).
		 */
  Entry <K> left() {
   return ( info & PRED_MASK ) != 0 ? null : left;
  }

  /** Returns the right subtree. 
		 *
		 * @return the right subtree (<code>null</code> if the right
		 * subtree is empty).
		 */
  Entry <K> right() {
   return ( info & SUCC_MASK ) != 0 ? null : right;
  }

  /** Checks whether the left pointer is really a predecessor.
		 * @return true if the left pointer is a predecessor.
		 */
  boolean pred() {
   return ( info & PRED_MASK ) != 0;
  }

  /** Checks whether the right pointer is really a successor.
		 * @return true if the right pointer is a successor.
		 */
  boolean succ() {
   return ( info & SUCC_MASK ) != 0;
  }

  /** Sets whether the left pointer is really a predecessor.
		 * @param pred if true then the left pointer will be considered a predecessor.
		 */
  void pred( final boolean pred ) {
   if ( pred ) info |= PRED_MASK;
   else info &= ~PRED_MASK;
  }

  /** Sets whether the right pointer is really a successor.
		 * @param succ if true then the right pointer will be considered a successor.
		 */
  void succ( final boolean succ ) {
   if ( succ ) info |= SUCC_MASK;
   else info &= ~SUCC_MASK;
  }

  /** Sets the left pointer to a predecessor.
		 * @param pred the predecessr.
		 */
  void pred( final Entry <K> pred ) {
   info |= PRED_MASK;
   left = pred;
  }

  /** Sets the right pointer to a successor.
		 * @param succ the successor.
		 */
  void succ( final Entry <K> succ ) {
   info |= SUCC_MASK;
   right = succ;
  }

  /** Sets the left pointer to the given subtree.
		 * @param left the new left subtree.
		 */
  void left( final Entry <K> left ) {
   info &= ~PRED_MASK;
   this.left = left;
  }

  /** Sets the right pointer to the given subtree.
		 * @param right the new right subtree.
		 */
  void right( final Entry <K> right ) {
   info &= ~SUCC_MASK;
   this.right = right;
  }

  /** Returns the current level of the node.
		 * @return the current level of this node.
		 */
  int balance() {
   return (byte)info;
  }

  /** Sets the level of this node.
		 * @param level the new level of this node.
		 */
  void balance( int level ) {
   info &= ~BALANCE_MASK;
   info |= ( level & BALANCE_MASK );
  }

  /** Increments the level of this node. */
  void incBalance() {
   info = info & ~BALANCE_MASK | ( (byte)info + 1 ) & 0xFF;
  }

  /** Decrements the level of this node. */
  protected void decBalance() {
   info = info & ~BALANCE_MASK | ( (byte)info - 1 ) & 0xFF;
  }

  /** Computes the next entry in the set order.
		 *
		 * @return the next entry (<code>null</code>) if this is the last entry).
		 */

  Entry <K> next() {
   Entry <K> next = this.right;
   if ( ( info & SUCC_MASK ) == 0 ) while ( ( next.info & PRED_MASK ) == 0 ) next = next.left;
   return next;
  }

  /** Computes the previous entry in the set order.
		 *
		 * @return the previous entry (<code>null</code>) if this is the first entry).
		 */

  Entry <K> prev() {
   Entry <K> prev = this.left;
   if ( ( info & PRED_MASK ) == 0 ) while ( ( prev.info & SUCC_MASK ) == 0 ) prev = prev.right;
   return prev;
  }

  public K getKey() {
   return (key);
  }







  public Character getValue() {
   return (Character.valueOf(value));
  }


  public char getCharValue() {
   return value;
  }


  public char setValue(final char value) {
   final char oldValue = this.value;
   this.value = value;
   return oldValue;
  }



  public Character setValue(final Character value) {
   return (Character.valueOf(setValue(((value).charValue()))));
  }



  @SuppressWarnings("unchecked")
  public Object clone() {
   Entry <K> c;
   try {
    c = (Entry <K>)super.clone();
   }
   catch(CloneNotSupportedException cantHappen) {
    throw new InternalError();
   }

   c.key = key;
   c.value = value;
   c.info = info;

   return c;
  }

  @SuppressWarnings("unchecked")
  public boolean equals( final Object o ) {
   if (!(o instanceof Map.Entry)) return false;
   Map.Entry<K, Character> e = (Map.Entry<K, Character>)o;

   return ( (key) == null ? ((e.getKey())) == null : (key).equals((e.getKey())) ) && ( (value) == (((e.getValue()).charValue())) );
  }

  public int hashCode() {
   return ( (key) == null ? 0 : (key).hashCode() ) ^ (value);
  }


  public String toString() {
   return key + "=>" + value;
  }


  /*
		public void prettyPrint() {
			prettyPrint(0);
		}

		public void prettyPrint(int level) {
			if ( pred() ) {
				for (int i = 0; i < level; i++)
					System.err.print("  ");
				System.err.println("pred: " + left );
			}
			else if (left != null)
				left.prettyPrint(level +1 );
			for (int i = 0; i < level; i++)
				System.err.print("  ");
			System.err.println(key + "=" + value + " (" + balance() + ")");
			if ( succ() ) {
				for (int i = 0; i < level; i++)
					System.err.print("  ");
				System.err.println("succ: " + right );
			}
			else if (right != null)
				right.prettyPrint(level + 1);
		}
		*/
 }

 /*
	public void prettyPrint() {
		System.err.println("size: " + count);
		if (tree != null) tree.prettyPrint();
	}
	*/

 @SuppressWarnings("unchecked")
 public boolean containsKey( final Object k ) {
  return findKey( (K) k ) != null;
 }

 public int size() {
  return count;
 }

 public boolean isEmpty() {
  return count == 0;
 }


 @SuppressWarnings("unchecked")
 public char getChar( final Object k ) {
  final Entry <K> e = findKey( (K) k );
  return e == null ? defRetValue : e.value;
 }



 @SuppressWarnings("unchecked")
 public Character get( final Object ok ) {
  final Entry <K> e = findKey( (K) ok );
  return e == null ? (null) : e.getValue();
 }



 public K firstKey() {
  if ( tree == null ) throw new NoSuchElementException();
  return firstEntry.key;
 }

 public K lastKey() {
  if ( tree == null ) throw new NoSuchElementException();
  return lastEntry.key;
 }


 /** An abstract iterator on the whole range.
	 *
	 * <P>This class can iterate in both directions on a threaded tree.
	 */

 private class TreeIterator {
  /** The entry that will be returned by the next call to {@link java.util.ListIterator#previous()} (or <code>null</code> if no previous entry exists). */
  Entry <K> prev;
  /** The entry that will be returned by the next call to {@link java.util.ListIterator#next()} (or <code>null</code> if no next entry exists). */
  Entry <K> next;
  /** The last entry that was returned (or <code>null</code> if we did not iterate or used {@link #remove()}). */
  Entry <K> curr;
  /** The current index (in the sense of a {@link java.util.ListIterator}). Note that this value is not meaningful when this {@link TreeIterator} has been created using the nonempty constructor.*/
  int index = 0;

  TreeIterator() {
   next = firstEntry;
  }

  TreeIterator( final K k ) {
   if ( ( next = locateKey( k ) ) != null ) {
    if ( compare( next.key, k ) <= 0 ) {
     prev = next;
     next = next.next();
    }
    else prev = next.prev();
   }
  }

  public boolean hasNext() { return next != null; }
  public boolean hasPrevious() { return prev != null; }

  void updateNext() {
   next = next.next();
  }

  Entry <K> nextEntry() {
   if ( ! hasNext() ) throw new NoSuchElementException();
   curr = prev = next;
   index++;
   updateNext();
   return curr;
  }

  void updatePrevious() {
   prev = prev.prev();
  }

  Entry <K> previousEntry() {
   if ( ! hasPrevious() ) throw new NoSuchElementException();
   curr = next = prev;
   index--;
   updatePrevious();
   return curr;
  }

  public int nextIndex() {
   return index;
  }

  public int previousIndex() {
   return index - 1;
  }

  public void remove() {
   if ( curr == null ) throw new IllegalStateException();
   /* If the last operation was a next(), we are removing an entry that preceeds
			   the current index, and thus we must decrement it. */
   if ( curr == prev ) index--;
   next = prev = curr;
   updatePrevious();
   updateNext();
   Object2CharAVLTreeMap.this.removeChar( curr.key );
   curr = null;
  }

  public int skip( final int n ) {
   int i = n;
   while( i-- != 0 && hasNext() ) nextEntry();
   return n - i - 1;
  }

  public int back( final int n ) {
   int i = n;
   while( i-- != 0 && hasPrevious() ) previousEntry();
   return n - i - 1;
  }
 }


 /** An iterator on the whole range.
	 *
	 * <P>This class can iterate in both directions on a threaded tree.
	 */

 private class EntryIterator extends TreeIterator implements ObjectListIterator<Object2CharMap.Entry <K> > {
  EntryIterator() {}

  EntryIterator( final K k ) {
   super( k );
  }

  public Object2CharMap.Entry <K> next() { return nextEntry(); }
  public Object2CharMap.Entry <K> previous() { return previousEntry(); }

  public void set( Object2CharMap.Entry <K> ok ) { throw new UnsupportedOperationException(); }
  public void add( Object2CharMap.Entry <K> ok ) { throw new UnsupportedOperationException(); }
 }


 public ObjectSortedSet<Object2CharMap.Entry <K> > object2CharEntrySet() {
  if ( entries == null ) entries = new AbstractObjectSortedSet<Object2CharMap.Entry <K> >() {
    final Comparator<? super Object2CharMap.Entry <K> > comparator = new Comparator<Object2CharMap.Entry <K> > () {
     public int compare( final Object2CharMap.Entry <K> x, final Object2CharMap.Entry <K> y ) {
      return Object2CharAVLTreeMap.this.storedComparator.compare( x.getKey(), y.getKey() );
     }
    };

    public Comparator<? super Object2CharMap.Entry <K> > comparator() {
     return comparator;
    }

    public ObjectBidirectionalIterator<Object2CharMap.Entry <K> > iterator() {
     return new EntryIterator();
    }

    public ObjectBidirectionalIterator<Object2CharMap.Entry <K> > iterator( final Object2CharMap.Entry <K> from ) {
     return new EntryIterator( (from.getKey()) );
    }

    @SuppressWarnings("unchecked")
    public boolean contains( final Object o ) {
     if (!(o instanceof Map.Entry)) return false;
     final Map.Entry <K, Character> e = (Map.Entry <K, Character>)o;
     final Entry <K> f = findKey( (e.getKey()) );
     return e.equals( f );
    }

    @SuppressWarnings("unchecked")
    public boolean remove( final Object o ) {
     if (!(o instanceof Map.Entry)) return false;
     final Map.Entry <K, Character> e = (Map.Entry <K, Character>)o;
     final Entry <K> f = findKey( (e.getKey()) );
     if ( f != null ) Object2CharAVLTreeMap.this.removeChar( f.key );
     return f != null;
    }

    public int size() { return count; }
    public void clear() { Object2CharAVLTreeMap.this.clear(); }

    public Object2CharMap.Entry <K> first() { return firstEntry; }
    public Object2CharMap.Entry <K> last() { return lastEntry; }
    public ObjectSortedSet<Object2CharMap.Entry <K> > subSet( Object2CharMap.Entry <K> from, Object2CharMap.Entry <K> to ) { return subMap( from.getKey(), to.getKey() ).object2CharEntrySet(); }
    public ObjectSortedSet<Object2CharMap.Entry <K> > headSet( Object2CharMap.Entry <K> to ) { return headMap( to.getKey() ).object2CharEntrySet(); }
    public ObjectSortedSet<Object2CharMap.Entry <K> > tailSet( Object2CharMap.Entry <K> from ) { return tailMap( from.getKey() ).object2CharEntrySet(); }
   };

  return entries;
 }

 /** An iterator on the whole range of keys.
	 *
	 * <P>This class can iterate in both directions on the keys of a threaded tree. We 
	 * simply override the {@link java.util.ListIterator#next()}/{@link java.util.ListIterator#previous()} methods (and possibly
	 * their type-specific counterparts) so that they return keys instead of entries.
	 */
 private final class KeyIterator extends TreeIterator implements ObjectListIterator <K> {
  public KeyIterator() {}
  public KeyIterator( final K k ) { super( k ); }
  public K next() { return nextEntry().key; }
  public K previous() { return previousEntry().key; }

  public void set( K k ) { throw new UnsupportedOperationException(); }
  public void add( K k ) { throw new UnsupportedOperationException(); }







 };

 /** A keyset implementation using a more direct implementation for iterators. */
 private class KeySet extends AbstractObject2CharSortedMap <K>.KeySet {
  public ObjectBidirectionalIterator <K> iterator() { return new KeyIterator(); }
  public ObjectBidirectionalIterator <K> iterator( final K from ) { return new KeyIterator( from ); }
 }

 /** Returns a type-specific sorted set view of the keys contained in this map.
	 *
	 * <P>In addition to the semantics of {@link java.util.Map#keySet()}, you can
	 * safely cast the set returned by this call to a type-specific sorted
	 * set interface.
	 *
	 * @return a type-specific sorted set view of the keys contained in this map.
	 */
 public ObjectSortedSet <K> keySet() {
  if ( keys == null ) keys = new KeySet();
  return keys;
 }

 /** An iterator on the whole range of values.
	 *
	 * <P>This class can iterate in both directions on the values of a threaded tree. We 
	 * simply override the {@link java.util.ListIterator#next()}/{@link java.util.ListIterator#previous()} methods (and possibly
	 * their type-specific counterparts) so that they return values instead of entries.
	 */
 private final class ValueIterator extends TreeIterator implements CharListIterator {
  public char nextChar() { return nextEntry().value; }
  public char previousChar() { return previousEntry().value; }
  public void set( char v ) { throw new UnsupportedOperationException(); }
  public void add( char v ) { throw new UnsupportedOperationException(); }


  public Character next() { return (Character.valueOf(nextEntry().value)); }
  public Character previous() { return (Character.valueOf(previousEntry().value)); }
  public void set( Character ok ) { throw new UnsupportedOperationException(); }
  public void add( Character ok ) { throw new UnsupportedOperationException(); }

 };

 /** Returns a type-specific collection view of the values contained in this map.
	 *
	 * <P>In addition to the semantics of {@link java.util.Map#values()}, you can
	 * safely cast the collection returned by this call to a type-specific collection
	 * interface.
	 *
	 * @return a type-specific collection view of the values contained in this map.
	 */

 public CharCollection values() {
  if ( values == null ) values = new AbstractCharCollection () {
    public CharIterator iterator() {
     return new ValueIterator();
    }

    public boolean contains( final char k ) {
     return containsValue( k );
    }

    public int size() {
     return count;
    }

    public void clear() {
     Object2CharAVLTreeMap.this.clear();
    }

   };

  return values;
 }

 public Comparator <? super K> comparator() {
  return actualComparator;
 }

 public Object2CharSortedMap <K> headMap( K to ) {
  return new Submap( (null), true, to, false );
 }

 public Object2CharSortedMap <K> tailMap( K from ) {
  return new Submap( from, false, (null), true );
 }

 public Object2CharSortedMap <K> subMap( K from, K to ) {
  return new Submap( from, false, to, false );
 }

 /** A submap with given range.
	 *
	 * <P>This class represents a submap. One has to specify the left/right
	 * limits (which can be set to -&infin; or &infin;). Since the submap is a
	 * view on the map, at a given moment it could happen that the limits of
	 * the range are not any longer in the main map. Thus, things such as
	 * {@link java.util.SortedMap#firstKey()} or {@link java.util.Collection#size()} must be always computed
	 * on-the-fly.  
	 */
 private final class Submap extends AbstractObject2CharSortedMap <K> implements java.io.Serializable {
     public static final long serialVersionUID = -7046029254386353129L;

  /** The start of the submap range, unless {@link #bottom} is true. */
  K from;
  /** The end of the submap range, unless {@link #top} is true. */
  K to;
  /** If true, the submap range starts from -&infin;. */
  boolean bottom;
  /** If true, the submap range goes to &infin;. */
  boolean top;
  /** Cached set of entries. */
  @SuppressWarnings("hiding")
  protected transient volatile ObjectSortedSet<Object2CharMap.Entry <K> > entries;
  /** Cached set of keys. */
  @SuppressWarnings("hiding")
   protected transient volatile ObjectSortedSet <K> keys;
  /** Cached collection of values. */
  @SuppressWarnings("hiding")
  protected transient volatile CharCollection values;

  /** Creates a new submap with given key range.
		 *
		 * @param from the start of the submap range.
		 * @param bottom if true, the first parameter is ignored and the range starts from -&infin;.
		 * @param to the end of the submap range.
		 * @param top if true, the third parameter is ignored and the range goes to &infin;.
		 */
  public Submap( final K from, final boolean bottom, final K to, final boolean top ) {
   if ( ! bottom && ! top && Object2CharAVLTreeMap.this.compare( from, to ) > 0 ) throw new IllegalArgumentException( "Start key (" + from + ") is larger than end key (" + to + ")" );

   this.from = from;
   this.bottom = bottom;
   this.to = to;
   this.top = top;
   this.defRetValue = Object2CharAVLTreeMap.this.defRetValue;
  }

  public void clear() {
   final SubmapIterator i = new SubmapIterator();
   while( i.hasNext() ) {
    i.nextEntry();
    i.remove();
   }
  }

  /** Checks whether a key is in the submap range.
		 * @param k a key.
		 * @return true if is the key is in the submap range.
		 */
  final boolean in( final K k ) {
   return ( bottom || Object2CharAVLTreeMap.this.compare( k, from ) >= 0 ) &&
    ( top || Object2CharAVLTreeMap.this.compare( k, to ) < 0 );
  }

  public ObjectSortedSet<Object2CharMap.Entry <K> > object2CharEntrySet() {
   if ( entries == null ) entries = new AbstractObjectSortedSet<Object2CharMap.Entry <K> >() {
     public ObjectBidirectionalIterator<Object2CharMap.Entry <K> > iterator() {
      return new SubmapEntryIterator();
     }

     public ObjectBidirectionalIterator<Object2CharMap.Entry <K> > iterator( final Object2CharMap.Entry <K> from ) {
      return new SubmapEntryIterator( (from.getKey()) );
     }

     public Comparator<? super Object2CharMap.Entry <K> > comparator() { return Object2CharAVLTreeMap.this.entrySet().comparator(); }

     @SuppressWarnings("unchecked")
     public boolean contains( final Object o ) {
      if (!(o instanceof Map.Entry)) return false;
      final Map.Entry <K, Character> e = (Map.Entry <K, Character>)o;
      final Object2CharAVLTreeMap.Entry <K> f = findKey( (e.getKey()) );
      return f != null && in( f.key ) && e.equals( f );
     }

     @SuppressWarnings("unchecked")
     public boolean remove( final Object o ) {
      if (!(o instanceof Map.Entry)) return false;
      final Map.Entry <K, Character> e = (Map.Entry <K, Character>)o;
      final Object2CharAVLTreeMap.Entry <K> f = findKey( (e.getKey()) );
      if ( f != null && in( f.key ) ) Submap.this.removeChar( f.key );
      return f != null;
     }

     public int size() {
      int c = 0;
      for( Iterator<?> i = iterator(); i.hasNext(); i.next() ) c++;
      return c;
     }


     public boolean isEmpty() {
      return ! new SubmapIterator().hasNext();
     }

     public void clear() {
      Submap.this.clear();
     }

     public Object2CharMap.Entry <K> first() { return firstEntry(); }
     public Object2CharMap.Entry <K> last() { return lastEntry(); }
     public ObjectSortedSet<Object2CharMap.Entry <K> > subSet( Object2CharMap.Entry <K> from, Object2CharMap.Entry <K> to ) { return subMap( from.getKey(), to.getKey() ).object2CharEntrySet(); }
     public ObjectSortedSet<Object2CharMap.Entry <K> > headSet( Object2CharMap.Entry <K> to ) { return headMap( to.getKey() ).object2CharEntrySet(); }
     public ObjectSortedSet<Object2CharMap.Entry <K> > tailSet( Object2CharMap.Entry <K> from ) { return tailMap( from.getKey() ).object2CharEntrySet(); }
    };

   return entries;
  }

  private class KeySet extends AbstractObject2CharSortedMap <K>.KeySet {
   public ObjectBidirectionalIterator <K> iterator() { return new SubmapKeyIterator(); }
   public ObjectBidirectionalIterator <K> iterator( final K from ) { return new SubmapKeyIterator( from ); }
  }

  public ObjectSortedSet <K> keySet() {
   if ( keys == null ) keys = new KeySet();
   return keys;
  }

  public CharCollection values() {
   if ( values == null ) values = new AbstractCharCollection () {
     public CharIterator iterator() {
      return new SubmapValueIterator();
     }

     public boolean contains( final char k ) {
      return containsValue( k );
     }

     public int size() {
      return Submap.this.size();
     }

     public void clear() {
      Submap.this.clear();
     }

    };

   return values;
  }

  @SuppressWarnings("unchecked")
  public boolean containsKey( final Object k ) {
   return in( (K) k ) && Object2CharAVLTreeMap.this.containsKey( k );
  }

  public boolean containsValue( final char v ) {
   final SubmapIterator i = new SubmapIterator();
   char ev;

   while( i.hasNext() ) {
    ev = i.nextEntry().value;
    if ( ( (ev) == (v) ) ) return true;
   }

   return false;
  }


  @SuppressWarnings("unchecked")
  public char getChar(final Object k) {
   final Object2CharAVLTreeMap.Entry <K> e;
   final K kk = (K) k;
   return in( kk ) && ( e = findKey( kk ) ) != null ? e.value : this.defRetValue;
  }




  @SuppressWarnings("unchecked")
  public Character get( final Object ok ) {
   final Object2CharAVLTreeMap.Entry <K> e;
   final K kk = (K) (ok);
   return in( kk ) && ( e = findKey( kk ) ) != null ? e.getValue() : (null);
  }


  public char put(final K k, final char v) {
   modified = false;
   if ( ! in( k ) ) throw new IllegalArgumentException( "Key (" + k + ") out of range [" + ( bottom ? "-" : String.valueOf( from ) ) + ", " + ( top ? "-" : String.valueOf( to ) ) + ")" );
   final char oldValue = Object2CharAVLTreeMap.this.put( k, v );
   return modified ? this.defRetValue : oldValue;
  }



  public Character put( final K ok, final Character ov ) {
   final char oldValue = put( (ok), ((ov).charValue()) );
   return modified ? (null) : (Character.valueOf(oldValue));
  }


  @SuppressWarnings("unchecked")
  public char removeChar( final Object k ) {
   modified = false;
   if ( ! in( (K) k ) ) return this.defRetValue;
   final char oldValue = Object2CharAVLTreeMap.this.removeChar( k );
   return modified ? oldValue : this.defRetValue;
  }


  public Character remove( final Object ok ) {
   final char oldValue = removeChar( (ok) );
   return modified ? (Character.valueOf(oldValue)) : (null);
  }


  public int size() {
   final SubmapIterator i = new SubmapIterator();
   int n = 0;

   while( i.hasNext() ) {
    n++;
    i.nextEntry();
   }

   return n;
  }


  public boolean isEmpty() {
   return ! new SubmapIterator().hasNext();
  }

  public Comparator <? super K> comparator() {
   return actualComparator;
  }

  public Object2CharSortedMap <K> headMap( final K to ) {
   if ( top ) return new Submap( from, bottom, to, false );
   return compare( to, this.to ) < 0 ? new Submap( from, bottom, to, false ) : this;
  }

  public Object2CharSortedMap <K> tailMap( final K from ) {
   if ( bottom ) return new Submap( from, false, to, top );
   return compare( from, this.from ) > 0 ? new Submap( from, false, to, top ) : this;
  }

  public Object2CharSortedMap <K> subMap( K from, K to ) {
   if ( top && bottom ) return new Submap( from, false, to, false );
   if ( ! top ) to = compare( to, this.to ) < 0 ? to : this.to;
   if ( ! bottom ) from = compare( from, this.from ) > 0 ? from : this.from;
    if ( ! top && ! bottom && from == this.from && to == this.to ) return this;
   return new Submap( from, false, to, false );
  }

  /** Locates the first entry.
		 *
		 * @return the first entry of this submap, or <code>null</code> if the submap is empty.
		 */
  public Object2CharAVLTreeMap.Entry <K> firstEntry() {
   if ( tree == null ) return null;
   // If this submap goes to -infinity, we return the main map first entry; otherwise, we locate the start of the map.
   Object2CharAVLTreeMap.Entry <K> e;
   if ( bottom ) e = firstEntry;
   else {
    e = locateKey( from );
    // If we find either the start or something greater we're OK.
    if ( compare( e.key, from ) < 0 ) e = e.next();
   }
   // Finally, if this subset doesn't go to infinity, we check that the resulting key isn't greater than the end.
   if ( e == null || ! top && compare( e.key, to ) >= 0 ) return null;
   return e;
  }

  /** Locates the last entry.
		 *
		 * @return the last entry of this submap, or <code>null</code> if the submap is empty.
		 */
  public Object2CharAVLTreeMap.Entry <K> lastEntry() {
   if ( tree == null ) return null;
   // If this submap goes to infinity, we return the main map last entry; otherwise, we locate the end of the map.
   Object2CharAVLTreeMap.Entry <K> e;
   if ( top ) e = lastEntry;
   else {
    e = locateKey( to );
    // If we find something smaller than the end we're OK.
    if ( compare( e.key, to ) >= 0 ) e = e.prev();
   }
   // Finally, if this subset doesn't go to -infinity, we check that the resulting key isn't smaller than the start.
   if ( e == null || ! bottom && compare( e.key, from ) < 0 ) return null;
   return e;
  }

  public K firstKey() {
   Object2CharAVLTreeMap.Entry <K> e = firstEntry();
   if ( e == null ) throw new NoSuchElementException();
   return e.key;
  }
  public K lastKey() {
   Object2CharAVLTreeMap.Entry <K> e = lastEntry();
   if ( e == null ) throw new NoSuchElementException();
   return e.key;
  }
  /** An iterator for subranges.
		 * 
		 * <P>This class inherits from {@link TreeIterator}, but overrides the methods that
		 * update the pointer after a {@link java.util.ListIterator#next()} or {@link java.util.ListIterator#previous()}. If we would
		 * move out of the range of the submap we just overwrite the next or previous
		 * entry with <code>null</code>.
		 */
  private class SubmapIterator extends TreeIterator {
   SubmapIterator() {
    next = firstEntry();
   }

   SubmapIterator( final K k ) {
    this();

    if ( next != null ) {
     if ( ! bottom && compare( k, next.key ) < 0 ) prev = null;
     else if ( ! top && compare( k, ( prev = lastEntry() ).key ) >= 0 ) next = null;
     else {
      next = locateKey( k );

      if ( compare( next.key, k ) <= 0 ) {
       prev = next;
       next = next.next();
      }
      else prev = next.prev();
     }
    }
   }

   void updatePrevious() {
    prev = prev.prev();
    if ( ! bottom && prev != null && Object2CharAVLTreeMap.this.compare( prev.key, from ) < 0 ) prev = null;
   }

   void updateNext() {
    next = next.next();
    if ( ! top && next != null && Object2CharAVLTreeMap.this.compare( next.key, to ) >= 0 ) next = null;
   }
  }

  private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Object2CharMap.Entry <K> > {
   SubmapEntryIterator() {}

   SubmapEntryIterator( final K k ) {
    super( k );
   }

   public Object2CharMap.Entry <K> next() { return nextEntry(); }
   public Object2CharMap.Entry <K> previous() { return previousEntry(); }

   public void set( Object2CharMap.Entry <K> ok ) { throw new UnsupportedOperationException(); }
   public void add( Object2CharMap.Entry <K> ok ) { throw new UnsupportedOperationException(); }
  }


  /** An iterator on a subrange of keys.
		 *
		 * <P>This class can iterate in both directions on a subrange of the
		 * keys of a threaded tree. We simply override the {@link
		 * java.util.ListIterator#next()}/{@link java.util.ListIterator#previous()} methods (and possibly their
		 * type-specific counterparts) so that they return keys instead of
		 * entries.
		 */
  private final class SubmapKeyIterator extends SubmapIterator implements ObjectListIterator <K> {
   public SubmapKeyIterator() { super(); }
   public SubmapKeyIterator( K from ) { super( from ); }
   public K next() { return nextEntry().key; }
   public K previous() { return previousEntry().key; }
   public void set( K k ) { throw new UnsupportedOperationException(); }
   public void add( K k ) { throw new UnsupportedOperationException(); }






  };

  /** An iterator on a subrange of values.
		 *
		 * <P>This class can iterate in both directions on the values of a
		 * subrange of the keys of a threaded tree. We simply override the
		 * {@link java.util.ListIterator#next()}/{@link java.util.ListIterator#previous()} methods (and possibly their
		 * type-specific counterparts) so that they return values instead of
		 * entries.  
		 */
  private final class SubmapValueIterator extends SubmapIterator implements CharListIterator {
   public char nextChar() { return nextEntry().value; }
   public char previousChar() { return previousEntry().value; }
   public void set( char v ) { throw new UnsupportedOperationException(); }
   public void add( char v ) { throw new UnsupportedOperationException(); }


   public Character next() { return (Character.valueOf(nextEntry().value)); }
   public Character previous() { return (Character.valueOf(previousEntry().value)); }
   public void set( Character ok ) { throw new UnsupportedOperationException(); }
   public void add( Character ok ) { throw new UnsupportedOperationException(); }

  };


 }


 /** Returns a deep copy of this tree map.
	 *
	 * <P>This method performs a deep copy of this tree map; the data stored in the
	 * set, however, is not cloned. Note that this makes a difference only for object keys.
	 *
	 * @return a deep copy of this tree map.
	 */

 @SuppressWarnings("unchecked")
 public Object clone() {
  Object2CharAVLTreeMap <K> c;
  try {
   c = (Object2CharAVLTreeMap <K>)super.clone();
  }
  catch(CloneNotSupportedException cantHappen) {
   throw new InternalError();
  }

  c.keys = null;
  c.values = null;
  c.entries = null;
  c.allocatePaths();

  if ( count != 0 ) {
   // Also this apparently unfathomable code is derived from GNU libavl.
   Entry <K> e, p, q, rp = new Entry <K>(), rq = new Entry <K>();

   p = rp;
   rp.left( tree );

   q = rq;
   rq.pred( null );

   while( true ) {
    if ( ! p.pred() ) {
     e = (Entry <K>)p.left.clone();
     e.pred( q.left );
     e.succ( q );
     q.left( e );

     p = p.left;
     q = q.left;
    }
    else {
     while( p.succ() ) {
      p = p.right;

      if ( p == null ) {
       q.right = null;
       c.tree = rq.left;

       c.firstEntry = c.tree;
       while( c.firstEntry.left != null ) c.firstEntry = c.firstEntry.left;
       c.lastEntry = c.tree;
       while( c.lastEntry.right != null ) c.lastEntry = c.lastEntry.right;

       return c;
      }
      q = q.right;
     }

     p = p.right;
     q = q.right;
    }

    if ( ! p.succ() ) {
     e = (Entry <K>)p.right.clone();
     e.succ( q.right );
     e.pred( q );
     q.right( e );
    }
   }
  }

  return c;
 }


 private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
  int n = count;
  EntryIterator i = new EntryIterator();
  Entry <K> e;

  s.defaultWriteObject();

  while(n-- != 0) {
   e = i.nextEntry();
   s.writeObject( e.key );
   s.writeChar( e.value );
  }
 }


 /** Reads the given number of entries from the input stream, returning the corresponding tree. 
	 *
	 * @param s the input stream.
	 * @param n the (positive) number of entries to read.
	 * @param pred the entry containing the key that preceeds the first key in the tree.
	 * @param succ the entry containing the key that follows the last key in the tree.
	 */
 @SuppressWarnings("unchecked")
 private Entry <K> readTree( final java.io.ObjectInputStream s, final int n, final Entry <K> pred, final Entry <K> succ ) throws java.io.IOException, ClassNotFoundException {
  if ( n == 1 ) {
   final Entry <K> top = new Entry <K>( (K) s.readObject(), s.readChar() );
   top.pred( pred );
   top.succ( succ );

   return top;
  }

  if ( n == 2 ) {
   /* We handle separately this case so that recursion will
			 *always* be on nonempty subtrees. */
   final Entry <K> top = new Entry <K>( (K) s.readObject(), s.readChar() );
   top.right( new Entry <K>( (K) s.readObject(), s.readChar() ) );
   top.right.pred( top );
   top.balance( 1 );
   top.pred( pred );
   top.right.succ( succ );

   return top;
  }

  // The right subtree is the largest one.
  final int rightN = n / 2, leftN = n - rightN - 1;

  final Entry <K> top = new Entry <K>();

  top.left( readTree( s, leftN, pred, top ) );

  top.key = (K) s.readObject();
  top.value = s.readChar();

  top.right( readTree( s, rightN, top, succ ) );

  if ( n == ( n & -n ) ) top.balance( 1 ); // Quick test for determining whether n is a power of 2.

  return top;
 }



 private void readObject( java.io.ObjectInputStream s ) throws java.io.IOException, ClassNotFoundException {
  s.defaultReadObject();
  /* The storedComparator is now correctly set, but we must restore
		   on-the-fly the actualComparator. */
  setActualComparator();
  allocatePaths();

  if ( count != 0 ) {
   tree = readTree( s, count, null, null );
   Entry <K> e;

   e = tree;
   while( e.left() != null ) e = e.left();
   firstEntry = e;

   e = tree;
   while( e.right() != null ) e = e.right();
   lastEntry = e;
  }

  if ( ASSERTS ) checkTree( tree );
 }


 @SuppressWarnings("unchecked")
 private static int checkTree( @SuppressWarnings("unused") Entry e ) { return 0; }
}
