/* Generic definitions */

#define Custom
#define PACKAGE it.unimi.dsi.fastutil.objects
#define VALUE_PACKAGE it.unimi.dsi.fastutil.objects
/* Assertions (useful to generate conditional code) */
#unassert keyclass
#assert keyclass(Object)
#unassert keys
 #assert keys(reference)
#unassert valueclass
#assert valueclass(Object)
#unassert values
 #assert values(reference)
/* Current type and class (and size, if applicable) */
#define KEY_TYPE Object
#define VALUE_TYPE Object
#define KEY_CLASS Object
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
#define KEY_VALUE ObjectValue
#define VALUE_VALUE ObjectValue
/* Interfaces (keys) */
#define COLLECTION ObjectCollection

#define SET ObjectSet

#define SORTED_SET ObjectSortedSet

#define STD_SORTED_SET SortedSet

#define FUNCTION Object2ObjectFunction
#define MAP Object2ObjectMap
#define SORTED_MAP Object2ObjectSortedMap
#if #keyclass(Object) || #keyclass(Reference)
#define STD_SORTED_MAP SortedMap

#else
#define STD_SORTED_MAP Object2ObjectSortedMap

#endif
#define LIST ObjectList

#define STACK Stack

#define PRIORITY_QUEUE PriorityQueue

#define INDIRECT_PRIORITY_QUEUE IndirectPriorityQueue

#define INDIRECT_DOUBLE_PRIORITY_QUEUE IndirectDoublePriorityQueue

#define KEY_ITERATOR ObjectIterator

#define KEY_ITERABLE ObjectIterable

#define KEY_BIDI_ITERATOR ObjectBidirectionalIterator

#define KEY_LIST_ITERATOR ObjectListIterator

#define STD_KEY_ITERATOR Iterator

#define KEY_COMPARATOR Comparator

/* Interfaces (values) */
#define VALUE_COLLECTION ObjectCollection

#define VALUE_ARRAY_SET ObjectArraySet

#define VALUE_ITERATOR ObjectIterator

#define VALUE_LIST_ITERATOR ObjectListIterator

/* Abstract implementations (keys) */
#define ABSTRACT_COLLECTION AbstractObjectCollection

#define ABSTRACT_SET AbstractObjectSet

#define ABSTRACT_SORTED_SET AbstractObjectSortedSet
#define ABSTRACT_FUNCTION AbstractObject2ObjectFunction
#define ABSTRACT_MAP AbstractObject2ObjectMap
#define ABSTRACT_FUNCTION AbstractObject2ObjectFunction
#define ABSTRACT_SORTED_MAP AbstractObject2ObjectSortedMap
#define ABSTRACT_LIST AbstractObjectList

#define SUBLIST ObjectSubList

#define ABSTRACT_PRIORITY_QUEUE AbstractPriorityQueue

#define ABSTRACT_STACK AbstractStack

#define KEY_ABSTRACT_ITERATOR AbstractObjectIterator

#define KEY_ABSTRACT_BIDI_ITERATOR AbstractObjectBidirectionalIterator

#define KEY_ABSTRACT_LIST_ITERATOR AbstractObjectListIterator

#if #keyclass(Object)
#define KEY_ABSTRACT_COMPARATOR Comparator

#else
#define KEY_ABSTRACT_COMPARATOR AbstractObjectComparator

#endif
/* Abstract implementations (values) */
#define VALUE_ABSTRACT_COLLECTION AbstractObjectCollection

#define VALUE_ABSTRACT_ITERATOR AbstractObjectIterator

#define VALUE_ABSTRACT_BIDI_ITERATOR AbstractObjectBidirectionalIterator

/* Static containers (keys) */
#define COLLECTIONS ObjectCollections

#define SETS ObjectSets

#define SORTED_SETS ObjectSortedSets

#define LISTS ObjectLists

#define MAPS Object2ObjectMaps
#define FUNCTIONS Object2ObjectFunctions
#define SORTED_MAPS Object2ObjectSortedMaps
#define PRIORITY_QUEUES ObjectPriorityQueues

#define HEAPS ObjectHeaps

#define SEMI_INDIRECT_HEAPS ObjectSemiIndirectHeaps

#define INDIRECT_HEAPS ObjectIndirectHeaps

#define ARRAYS ObjectArrays

#define ITERATORS ObjectIterators

#define COMPARATORS ObjectComparators

/* Static containers (values) */
#define VALUE_COLLECTIONS ObjectCollections

#define VALUE_SETS ObjectSets

#define VALUE_ARRAYS ObjectArrays

/* Implementations */
#define OPEN_HASH_SET ObjectOpenCustomHashSet

#define OPEN_HASH_MAP Object2ObjectOpenCustomHashMap

#define ARRAY_SET ObjectArraySet

#define ARRAY_MAP Object2ObjectArrayMap

#define LINKED_OPEN_HASH_SET ObjectLinkedOpenHashSet

#define AVL_TREE_SET ObjectAVLTreeSet

#define RB_TREE_SET ObjectRBTreeSet

#define AVL_TREE_MAP Object2ObjectAVLTreeMap

#define RB_TREE_MAP Object2ObjectRBTreeMap

#define ARRAY_LIST ObjectArrayList

#define ARRAY_FRONT_CODED_LIST ObjectArrayFrontCodedList

#define HEAP_PRIORITY_QUEUE ObjectHeapPriorityQueue

#define HEAP_SEMI_INDIRECT_PRIORITY_QUEUE ObjectHeapSemiIndirectPriorityQueue

#define HEAP_INDIRECT_PRIORITY_QUEUE ObjectHeapIndirectPriorityQueue

#define HEAP_SESQUI_INDIRECT_DOUBLE_PRIORITY_QUEUE ObjectHeapSesquiIndirectDoublePriorityQueue

#define HEAP_INDIRECT_DOUBLE_PRIORITY_QUEUE ObjectHeapIndirectDoublePriorityQueue

#define ARRAY_PRIORITY_QUEUE ObjectArrayPriorityQueue

#define ARRAY_INDIRECT_PRIORITY_QUEUE ObjectArrayIndirectPriorityQueue

#define ARRAY_INDIRECT_DOUBLE_PRIORITY_QUEUE ObjectArrayIndirectDoublePriorityQueue

/* Synchronized wrappers */
#define SYNCHRONIZED_COLLECTION SynchronizedObjectCollection

#define SYNCHRONIZED_SET SynchronizedObjectSet

#define SYNCHRONIZED_SORTED_SET SynchronizedObjectSortedSet

#define SYNCHRONIZED_FUNCTION SynchronizedObject2ObjectFunction

#define SYNCHRONIZED_MAP SynchronizedObject2ObjectMap

#define SYNCHRONIZED_LIST SynchronizedObjectList

/* Unmodifiable wrappers */
#define UNMODIFIABLE_COLLECTION UnmodifiableObjectCollection

#define UNMODIFIABLE_SET UnmodifiableObjectSet

#define UNMODIFIABLE_SORTED_SET UnmodifiableObjectSortedSet

#define UNMODIFIABLE_FUNCTION UnmodifiableObject2ObjectFunction

#define UNMODIFIABLE_MAP UnmodifiableObject2ObjectMap

#define UNMODIFIABLE_LIST UnmodifiableObjectList

#define UNMODIFIABLE_KEY_ITERATOR UnmodifiableObjectIterator

#define UNMODIFIABLE_KEY_BIDI_ITERATOR UnmodifiableObjectBidirectionalIterator

#define UNMODIFIABLE_KEY_LIST_ITERATOR UnmodifiableObjectListIterator

/* Other wrappers */
#define KEY_READER_WRAPPER ObjectReaderWrapper

#define KEY_DATA_INPUT_WRAPPER ObjectDataInputWrapper

/* Methods (keys) */
#define NEXT_KEY next
#define PREV_KEY previous
#define FIRST_KEY firstKey
#define LAST_KEY lastKey
#define GET_KEY get
#define REMOVE_KEY remove
#define READ_KEY readObject
#define WRITE_KEY writeObject
#define DEQUEUE dequeue
#define SUBLIST_METHOD objectSubList
#define SINGLETON_METHOD objectSingleton

#define FIRST first
#define LAST last
#define TOP top
#define PEEK peek
#define POP pop
#define KEY_ITERATOR_METHOD objectIterator

#define KEY_LIST_ITERATOR_METHOD objectListIterator

#define KEY_EMPTY_ITERATOR_METHOD emptyObjectIterator

#define AS_KEY_ITERATOR asObjectIterator

#define TO_KEY_ARRAY toArray
#define ENTRY_GET_KEY getKey
#define PARSE_KEY parse
#define LOAD_KEYS loads
#define STORE_KEYS stores
/* Methods (values) */
#define NEXT_VALUE next
#define PREV_VALUE previous
#define READ_VALUE readObject
#define WRITE_VALUE writeObject
#define VALUE_ITERATOR_METHOD objectIterator

#define ENTRY_GET_VALUE getValue
/* Methods (keys/values) */
#define ENTRYSET object2ObjectEntrySet
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
#define KEY2INT(x) it.unimi.dsi.fastutil.HashCommon.Object2int(x)
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
#include "OpenCustomHashSet.drv"

