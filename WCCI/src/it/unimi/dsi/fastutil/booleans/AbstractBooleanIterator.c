/* Generic definitions */


#define PACKAGE it.unimi.dsi.fastutil.booleans
#define VALUE_PACKAGE it.unimi.dsi.fastutil.objects
/* Assertions (useful to generate conditional code) */
#unassert keyclass
#assert keyclass(Boolean)
#unassert keys
 #assert keys(primitive)
#unassert valueclass
#assert valueclass(Object)
#unassert values
 #assert values(reference)
/* Current type and class (and size, if applicable) */
#define KEY_TYPE boolean
#define VALUE_TYPE Object
#define KEY_CLASS Boolean
#define VALUE_CLASS Object
#if #keyclass(Object) || #keyclass(Reference)
#define KEY_GENERIC_CLASS K
#define KEY_GENERIC_TYPE K
#define KEY_GENERIC <K>
#define KEY_GENERIC_WILDCARD <?>
#define KEY_EXTENDS_GENERIC <? extends K>
#define KEY_SUPER_GENERIC <? super K>
#define KEY_GENERIC_CAST (K)
#define KEY_GENERIC_ARRAY_CAST (K[])
#else
#define KEY_GENERIC_CLASS KEY_CLASS
#define KEY_GENERIC_TYPE KEY_TYPE
#define KEY_GENERIC
#define KEY_GENERIC_WILDCARD
#define KEY_EXTENDS_GENERIC
#define KEY_SUPER_GENERIC
#define KEY_GENERIC_CAST
#define KEY_GENERIC_ARRAY_CAST
#endif
#if #valueclass(Object) || #valueclass(Reference)
#define VALUE_GENERIC_CLASS V
#define VALUE_GENERIC_TYPE V
#define VALUE_GENERIC <V>
#define VALUE_EXTENDS_GENERIC <? extends V>
#define VALUE_GENERIC_CAST (V)
#define VALUE_GENERIC_ARRAY_CAST (V[])
#else
#define VALUE_GENERIC_CLASS VALUE_CLASS
#define VALUE_GENERIC_TYPE VALUE_TYPE
#define VALUE_GENERIC
#define VALUE_EXTENDS_GENERIC
#define VALUE_GENERIC_CAST
#define VALUE_GENERIC_ARRAY_CAST
#endif
#if #keyclass(Object) || #keyclass(Reference)
#if #valueclass(Object) || #valueclass(Reference)
#define KEY_VALUE_GENERIC <K,V>
#define KEY_VALUE_EXTENDS_GENERIC <? extends K, ? extends V>
#else
#define KEY_VALUE_GENERIC <K>
#define KEY_VALUE_EXTENDS_GENERIC <? extends K>
#endif
#else
#if #valueclass(Object) || #valueclass(Reference)
#define KEY_VALUE_GENERIC <V>
#define KEY_VALUE_EXTENDS_GENERIC <? extends V>
#else
#define KEY_VALUE_GENERIC
#define KEY_VALUE_EXTENDS_GENERIC
#endif
#endif
/* Value methods */
#define KEY_VALUE booleanValue
#define VALUE_VALUE ObjectValue
/* Interfaces (keys) */
#define COLLECTION BooleanCollection

#define SET BooleanSet

#define SORTED_SET BooleanSortedSet

#define STD_SORTED_SET BooleanSortedSet

#define FUNCTION Boolean2ObjectFunction
#define MAP Boolean2ObjectMap
#define SORTED_MAP Boolean2ObjectSortedMap
#if #keyclass(Object) || #keyclass(Reference)
#define STD_SORTED_MAP SortedMap

#else
#define STD_SORTED_MAP Boolean2ObjectSortedMap

#endif
#define LIST BooleanList

#define STACK BooleanStack

#define PRIORITY_QUEUE BooleanPriorityQueue

#define INDIRECT_PRIORITY_QUEUE BooleanIndirectPriorityQueue

#define INDIRECT_DOUBLE_PRIORITY_QUEUE BooleanIndirectDoublePriorityQueue

#define KEY_ITERATOR BooleanIterator

#define KEY_ITERABLE BooleanIterable

#define KEY_BIDI_ITERATOR BooleanBidirectionalIterator

#define KEY_LIST_ITERATOR BooleanListIterator

#define STD_KEY_ITERATOR BooleanIterator

#define KEY_COMPARATOR BooleanComparator

/* Interfaces (values) */
#define VALUE_COLLECTION ObjectCollection

#define VALUE_ARRAY_SET ObjectArraySet

#define VALUE_ITERATOR ObjectIterator

#define VALUE_LIST_ITERATOR ObjectListIterator

/* Abstract implementations (keys) */
#define ABSTRACT_COLLECTION AbstractBooleanCollection

#define ABSTRACT_SET AbstractBooleanSet

#define ABSTRACT_SORTED_SET AbstractBooleanSortedSet
#define ABSTRACT_FUNCTION AbstractBoolean2ObjectFunction
#define ABSTRACT_MAP AbstractBoolean2ObjectMap
#define ABSTRACT_FUNCTION AbstractBoolean2ObjectFunction
#define ABSTRACT_SORTED_MAP AbstractBoolean2ObjectSortedMap
#define ABSTRACT_LIST AbstractBooleanList

#define SUBLIST BooleanSubList

#define ABSTRACT_PRIORITY_QUEUE AbstractBooleanPriorityQueue

#define ABSTRACT_STACK AbstractBooleanStack

#define KEY_ABSTRACT_ITERATOR AbstractBooleanIterator

#define KEY_ABSTRACT_BIDI_ITERATOR AbstractBooleanBidirectionalIterator

#define KEY_ABSTRACT_LIST_ITERATOR AbstractBooleanListIterator

#if #keyclass(Object)
#define KEY_ABSTRACT_COMPARATOR Comparator

#else
#define KEY_ABSTRACT_COMPARATOR AbstractBooleanComparator

#endif
/* Abstract implementations (values) */
#define VALUE_ABSTRACT_COLLECTION AbstractObjectCollection

#define VALUE_ABSTRACT_ITERATOR AbstractObjectIterator

#define VALUE_ABSTRACT_BIDI_ITERATOR AbstractObjectBidirectionalIterator

/* Static containers (keys) */
#define COLLECTIONS BooleanCollections

#define SETS BooleanSets

#define SORTED_SETS BooleanSortedSets

#define LISTS BooleanLists

#define MAPS Boolean2ObjectMaps
#define FUNCTIONS Boolean2ObjectFunctions
#define SORTED_MAPS Boolean2ObjectSortedMaps
#define PRIORITY_QUEUES BooleanPriorityQueues

#define HEAPS BooleanHeaps

#define SEMI_INDIRECT_HEAPS BooleanSemiIndirectHeaps

#define INDIRECT_HEAPS BooleanIndirectHeaps

#define ARRAYS BooleanArrays

#define ITERATORS BooleanIterators

#define COMPARATORS BooleanComparators

/* Static containers (values) */
#define VALUE_COLLECTIONS ObjectCollections

#define VALUE_SETS ObjectSets

#define VALUE_ARRAYS ObjectArrays

/* Implementations */
#define OPEN_HASH_SET BooleanOpenHashSet

#define OPEN_HASH_MAP Boolean2ObjectOpenHashMap

#define ARRAY_SET BooleanArraySet

#define ARRAY_MAP Boolean2ObjectArrayMap

#define LINKED_OPEN_HASH_SET BooleanLinkedOpenHashSet

#define AVL_TREE_SET BooleanAVLTreeSet

#define RB_TREE_SET BooleanRBTreeSet

#define AVL_TREE_MAP Boolean2ObjectAVLTreeMap

#define RB_TREE_MAP Boolean2ObjectRBTreeMap

#define ARRAY_LIST BooleanArrayList

#define ARRAY_FRONT_CODED_LIST BooleanArrayFrontCodedList

#define HEAP_PRIORITY_QUEUE BooleanHeapPriorityQueue

#define HEAP_SEMI_INDIRECT_PRIORITY_QUEUE BooleanHeapSemiIndirectPriorityQueue

#define HEAP_INDIRECT_PRIORITY_QUEUE BooleanHeapIndirectPriorityQueue

#define HEAP_SESQUI_INDIRECT_DOUBLE_PRIORITY_QUEUE BooleanHeapSesquiIndirectDoublePriorityQueue

#define HEAP_INDIRECT_DOUBLE_PRIORITY_QUEUE BooleanHeapIndirectDoublePriorityQueue

#define ARRAY_PRIORITY_QUEUE BooleanArrayPriorityQueue

#define ARRAY_INDIRECT_PRIORITY_QUEUE BooleanArrayIndirectPriorityQueue

#define ARRAY_INDIRECT_DOUBLE_PRIORITY_QUEUE BooleanArrayIndirectDoublePriorityQueue

/* Synchronized wrappers */
#define SYNCHRONIZED_COLLECTION SynchronizedBooleanCollection

#define SYNCHRONIZED_SET SynchronizedBooleanSet

#define SYNCHRONIZED_SORTED_SET SynchronizedBooleanSortedSet

#define SYNCHRONIZED_FUNCTION SynchronizedBoolean2ObjectFunction

#define SYNCHRONIZED_MAP SynchronizedBoolean2ObjectMap

#define SYNCHRONIZED_LIST SynchronizedBooleanList

/* Unmodifiable wrappers */
#define UNMODIFIABLE_COLLECTION UnmodifiableBooleanCollection

#define UNMODIFIABLE_SET UnmodifiableBooleanSet

#define UNMODIFIABLE_SORTED_SET UnmodifiableBooleanSortedSet

#define UNMODIFIABLE_FUNCTION UnmodifiableBoolean2ObjectFunction

#define UNMODIFIABLE_MAP UnmodifiableBoolean2ObjectMap

#define UNMODIFIABLE_LIST UnmodifiableBooleanList

#define UNMODIFIABLE_KEY_ITERATOR UnmodifiableBooleanIterator

#define UNMODIFIABLE_KEY_BIDI_ITERATOR UnmodifiableBooleanBidirectionalIterator

#define UNMODIFIABLE_KEY_LIST_ITERATOR UnmodifiableBooleanListIterator

/* Other wrappers */
#define KEY_READER_WRAPPER BooleanReaderWrapper

#define KEY_DATA_INPUT_WRAPPER BooleanDataInputWrapper

/* Methods (keys) */
#define NEXT_KEY nextBoolean
#define PREV_KEY previousBoolean
#define FIRST_KEY firstBooleanKey
#define LAST_KEY lastBooleanKey
#define GET_KEY getBoolean
#define REMOVE_KEY removeBoolean
#define READ_KEY readBoolean
#define WRITE_KEY writeBoolean
#define DEQUEUE dequeueBoolean
#define SUBLIST_METHOD booleanSubList
#define SINGLETON_METHOD booleanSingleton

#define FIRST firstBoolean
#define LAST lastBoolean
#define TOP topBoolean
#define PEEK peekBoolean
#define POP popBoolean
#define KEY_ITERATOR_METHOD booleanIterator

#define KEY_LIST_ITERATOR_METHOD booleanListIterator

#define KEY_EMPTY_ITERATOR_METHOD emptyBooleanIterator

#define AS_KEY_ITERATOR asBooleanIterator

#define TO_KEY_ARRAY toBooleanArray
#define ENTRY_GET_KEY getBooleanKey
#define PARSE_KEY parseBoolean
#define LOAD_KEYS loadBooleans
#define STORE_KEYS storeBooleans
/* Methods (values) */
#define NEXT_VALUE next
#define PREV_VALUE previous
#define READ_VALUE readObject
#define WRITE_VALUE writeObject
#define VALUE_ITERATOR_METHOD objectIterator

#define ENTRY_GET_VALUE getValue
/* Methods (keys/values) */
#define ENTRYSET boolean2ObjectEntrySet
/* Methods that have special names depending on keys (but the special names depend on values) */
#if #keyclass(Object) || #keyclass(Reference)
#define GET_VALUE get
#define REMOVE_VALUE remove
#else
#define GET_VALUE get
#define REMOVE_VALUE remove
#endif
/* Equality */
#if #keyclass(Object)
#ifdef Custom
#define KEY_EQUALS(x,y) ( strategy.equals( (x), (y) ) )
#define KEY_EQUALS_HASH(x,h,y) ( (y) != HashCommon.REMOVED && (h) == strategy.hashCode(y) && strategy.equals((x), (y)) )
#else
#define KEY_EQUALS(x,y) ( (x) == null ? (y) == null : (x).equals(y) )
#define KEY_EQUALS_NOT_NULL(x,y) ( (x).equals(y) )
#define KEY_EQUALS_HASH(x,h,y) ( (y) == null ? (x) == null : (h) == (y).hashCode() && (y).equals(x) )
#endif
#else
#define KEY_EQUALS(x,y) ( (x) == (y) )
#define KEY_EQUALS_NOT_NULL(x,y) ( (x) == (y) )
#define KEY_EQUALS_HASH(x,h,y) ( (x) == (y) )
#endif

#if #valueclass(Object)
#define VALUE_EQUALS(x,y) ( (x) == null ? (y) == null : (x).equals(y) )
#else
#define VALUE_EQUALS(x,y) ( (x) == (y) )
#endif

/* Object/Reference-only definitions (keys) */
#if #keyclass(Object) || #keyclass(Reference)
#define REMOVE remove
#define KEY_OBJ2TYPE(x) (x)
#define KEY_CLASS2TYPE(x) (x)
#define KEY2OBJ(x) (x)
#if #keyclass(Object)
#ifdef Custom
#define KEY2INT(x) ( strategy.hashCode(x) )
#else
#define KEY2INT(x) ( (x) == null ? 0 : (x).hashCode() )
#endif
#else
#define KEY2INT(x) (System.identityHashCode(x))
#endif
#define KEY_CMP(x,y) ( ((Comparable<KEY_GENERIC_CLASS>)(x)).compareTo(y) )
#define KEY_LESS(x,y) ( ((Comparable<KEY_GENERIC_CLASS>)(x)).compareTo(y) < 0 )
#define KEY_LESSEQ(x,y) ( ((Comparable<KEY_GENERIC_CLASS>)(x)).compareTo(y) <= 0 )
#define KEY_NULL (null)
#else
/* Primitive-type-only definitions (keys) */
#define REMOVE rem
#define KEY_CLASS2TYPE(x) ((x).KEY_VALUE())
#define KEY_OBJ2TYPE(x) (KEY_CLASS2TYPE((KEY_CLASS)(x)))
#define KEY2OBJ(x) (KEY_CLASS.valueOf(x))
#if #keyclass(Boolean)
#define KEY_CMP(x,y) ( !(x) && (y) ? -1 : ( (x) == (y) ? 0 : 1 ) )
#define KEY_LESS(x,y) ( !(x) && (y) )
#define KEY_LESSEQ(x,y) ( !(x) || (y) )
#else
#define KEY_CMP(x,y) ( (x) < (y) ? -1 : ( (x) == (y) ? 0 : 1 ) )
#define KEY_LESS(x,y) ( (x) < (y) )
#define KEY_LESSEQ(x,y) ( (x) <= (y) )
#endif
#if #keyclass(Float) || #keyclass(Double) || #keyclass(Long)
#define KEY_NULL (0)
#define KEY2INT(x) it.unimi.dsi.fastutil.HashCommon.boolean2int(x)
#elif #keyclass(Boolean)
#define KEY_NULL (false)
#define KEY2INT(x) (x ? 1231 : 1237)
#else
#if #keyclass(Integer)
#define KEY_NULL (0)
#else
#define KEY_NULL ((KEY_TYPE)0)
#endif
#define KEY2INT(x) (x)
#endif
#endif
/* Object/Reference-only definitions (values) */
#if #valueclass(Object) || #valueclass(Reference)
#define VALUE_OBJ2TYPE(x) (x)
#define VALUE_CLASS2TYPE(x) (x)
#define VALUE2OBJ(x) (x)
#if #valueclass(Object)
#define VALUE2INT(x) ( (x) == null ? 0 : (x).hashCode() )
#else
#define VALUE2INT(x) (System.identityHashCode(x))
#endif
#define VALUE_NULL (null)
#define OBJECT_DEFAULT_RETURN_VALUE (this.defRetValue)
#else
/* Primitive-type-only definitions (values) */
#define VALUE_CLASS2TYPE(x) ((x).VALUE_VALUE())
#define VALUE_OBJ2TYPE(x) (VALUE_CLASS2TYPE((VALUE_CLASS)(x)))
#define VALUE2OBJ(x) (VALUE_CLASS.valueOf(x))
#if #valueclass(Float) || #valueclass(Double) || #valueclass(Long)
#define VALUE_NULL (0)
#define VALUE2INT(x) it.unimi.dsi.fastutil.HashCommon.Object2int(x)
#elif #valueclass(Boolean)
#define VALUE_NULL (false)
#define VALUE2INT(x) (x ? 1231 : 1237)
#else
#if #valueclass(Integer)
#define VALUE_NULL (0)
#else
#define VALUE_NULL ((VALUE_TYPE)0)
#endif
#define VALUE2INT(x) (x)
#endif
#define OBJECT_DEFAULT_RETURN_VALUE (null)
#endif
#include "AbstractIterator.drv"

