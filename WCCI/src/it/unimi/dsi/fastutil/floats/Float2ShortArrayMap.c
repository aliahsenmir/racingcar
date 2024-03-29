/* Generic definitions */


#define PACKAGE it.unimi.dsi.fastutil.floats
#define VALUE_PACKAGE it.unimi.dsi.fastutil.shorts
/* Assertions (useful to generate conditional code) */
#unassert keyclass
#assert keyclass(Float)
#unassert keys
 #assert keys(primitive)
#unassert valueclass
#assert valueclass(Short)
#unassert values
 #assert values(primitive)
/* Current type and class (and size, if applicable) */
#define KEY_TYPE float
#define VALUE_TYPE short
#define KEY_CLASS Float
#define VALUE_CLASS Short
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
#define KEY_VALUE floatValue
#define VALUE_VALUE shortValue
/* Interfaces (keys) */
#define COLLECTION FloatCollection

#define SET FloatSet

#define SORTED_SET FloatSortedSet

#define STD_SORTED_SET FloatSortedSet

#define FUNCTION Float2ShortFunction
#define MAP Float2ShortMap
#define SORTED_MAP Float2ShortSortedMap
#if #keyclass(Object) || #keyclass(Reference)
#define STD_SORTED_MAP SortedMap

#else
#define STD_SORTED_MAP Float2ShortSortedMap

#endif
#define LIST FloatList

#define STACK FloatStack

#define PRIORITY_QUEUE FloatPriorityQueue

#define INDIRECT_PRIORITY_QUEUE FloatIndirectPriorityQueue

#define INDIRECT_DOUBLE_PRIORITY_QUEUE FloatIndirectDoublePriorityQueue

#define KEY_ITERATOR FloatIterator

#define KEY_ITERABLE FloatIterable

#define KEY_BIDI_ITERATOR FloatBidirectionalIterator

#define KEY_LIST_ITERATOR FloatListIterator

#define STD_KEY_ITERATOR FloatIterator

#define KEY_COMPARATOR FloatComparator

/* Interfaces (values) */
#define VALUE_COLLECTION ShortCollection

#define VALUE_ARRAY_SET ShortArraySet

#define VALUE_ITERATOR ShortIterator

#define VALUE_LIST_ITERATOR ShortListIterator

/* Abstract implementations (keys) */
#define ABSTRACT_COLLECTION AbstractFloatCollection

#define ABSTRACT_SET AbstractFloatSet

#define ABSTRACT_SORTED_SET AbstractFloatSortedSet
#define ABSTRACT_FUNCTION AbstractFloat2ShortFunction
#define ABSTRACT_MAP AbstractFloat2ShortMap
#define ABSTRACT_FUNCTION AbstractFloat2ShortFunction
#define ABSTRACT_SORTED_MAP AbstractFloat2ShortSortedMap
#define ABSTRACT_LIST AbstractFloatList

#define SUBLIST FloatSubList

#define ABSTRACT_PRIORITY_QUEUE AbstractFloatPriorityQueue

#define ABSTRACT_STACK AbstractFloatStack

#define KEY_ABSTRACT_ITERATOR AbstractFloatIterator

#define KEY_ABSTRACT_BIDI_ITERATOR AbstractFloatBidirectionalIterator

#define KEY_ABSTRACT_LIST_ITERATOR AbstractFloatListIterator

#if #keyclass(Object)
#define KEY_ABSTRACT_COMPARATOR Comparator

#else
#define KEY_ABSTRACT_COMPARATOR AbstractFloatComparator

#endif
/* Abstract implementations (values) */
#define VALUE_ABSTRACT_COLLECTION AbstractShortCollection

#define VALUE_ABSTRACT_ITERATOR AbstractShortIterator

#define VALUE_ABSTRACT_BIDI_ITERATOR AbstractShortBidirectionalIterator

/* Static containers (keys) */
#define COLLECTIONS FloatCollections

#define SETS FloatSets

#define SORTED_SETS FloatSortedSets

#define LISTS FloatLists

#define MAPS Float2ShortMaps
#define FUNCTIONS Float2ShortFunctions
#define SORTED_MAPS Float2ShortSortedMaps
#define PRIORITY_QUEUES FloatPriorityQueues

#define HEAPS FloatHeaps

#define SEMI_INDIRECT_HEAPS FloatSemiIndirectHeaps

#define INDIRECT_HEAPS FloatIndirectHeaps

#define ARRAYS FloatArrays

#define ITERATORS FloatIterators

#define COMPARATORS FloatComparators

/* Static containers (values) */
#define VALUE_COLLECTIONS ShortCollections

#define VALUE_SETS ShortSets

#define VALUE_ARRAYS ShortArrays

/* Implementations */
#define OPEN_HASH_SET FloatOpenHashSet

#define OPEN_HASH_MAP Float2ShortOpenHashMap

#define ARRAY_SET FloatArraySet

#define ARRAY_MAP Float2ShortArrayMap

#define LINKED_OPEN_HASH_SET FloatLinkedOpenHashSet

#define AVL_TREE_SET FloatAVLTreeSet

#define RB_TREE_SET FloatRBTreeSet

#define AVL_TREE_MAP Float2ShortAVLTreeMap

#define RB_TREE_MAP Float2ShortRBTreeMap

#define ARRAY_LIST FloatArrayList

#define ARRAY_FRONT_CODED_LIST FloatArrayFrontCodedList

#define HEAP_PRIORITY_QUEUE FloatHeapPriorityQueue

#define HEAP_SEMI_INDIRECT_PRIORITY_QUEUE FloatHeapSemiIndirectPriorityQueue

#define HEAP_INDIRECT_PRIORITY_QUEUE FloatHeapIndirectPriorityQueue

#define HEAP_SESQUI_INDIRECT_DOUBLE_PRIORITY_QUEUE FloatHeapSesquiIndirectDoublePriorityQueue

#define HEAP_INDIRECT_DOUBLE_PRIORITY_QUEUE FloatHeapIndirectDoublePriorityQueue

#define ARRAY_PRIORITY_QUEUE FloatArrayPriorityQueue

#define ARRAY_INDIRECT_PRIORITY_QUEUE FloatArrayIndirectPriorityQueue

#define ARRAY_INDIRECT_DOUBLE_PRIORITY_QUEUE FloatArrayIndirectDoublePriorityQueue

/* Synchronized wrappers */
#define SYNCHRONIZED_COLLECTION SynchronizedFloatCollection

#define SYNCHRONIZED_SET SynchronizedFloatSet

#define SYNCHRONIZED_SORTED_SET SynchronizedFloatSortedSet

#define SYNCHRONIZED_FUNCTION SynchronizedFloat2ShortFunction

#define SYNCHRONIZED_MAP SynchronizedFloat2ShortMap

#define SYNCHRONIZED_LIST SynchronizedFloatList

/* Unmodifiable wrappers */
#define UNMODIFIABLE_COLLECTION UnmodifiableFloatCollection

#define UNMODIFIABLE_SET UnmodifiableFloatSet

#define UNMODIFIABLE_SORTED_SET UnmodifiableFloatSortedSet

#define UNMODIFIABLE_FUNCTION UnmodifiableFloat2ShortFunction

#define UNMODIFIABLE_MAP UnmodifiableFloat2ShortMap

#define UNMODIFIABLE_LIST UnmodifiableFloatList

#define UNMODIFIABLE_KEY_ITERATOR UnmodifiableFloatIterator

#define UNMODIFIABLE_KEY_BIDI_ITERATOR UnmodifiableFloatBidirectionalIterator

#define UNMODIFIABLE_KEY_LIST_ITERATOR UnmodifiableFloatListIterator

/* Other wrappers */
#define KEY_READER_WRAPPER FloatReaderWrapper

#define KEY_DATA_INPUT_WRAPPER FloatDataInputWrapper

/* Methods (keys) */
#define NEXT_KEY nextFloat
#define PREV_KEY previousFloat
#define FIRST_KEY firstFloatKey
#define LAST_KEY lastFloatKey
#define GET_KEY getFloat
#define REMOVE_KEY removeFloat
#define READ_KEY readFloat
#define WRITE_KEY writeFloat
#define DEQUEUE dequeueFloat
#define SUBLIST_METHOD floatSubList
#define SINGLETON_METHOD floatSingleton

#define FIRST firstFloat
#define LAST lastFloat
#define TOP topFloat
#define PEEK peekFloat
#define POP popFloat
#define KEY_ITERATOR_METHOD floatIterator

#define KEY_LIST_ITERATOR_METHOD floatListIterator

#define KEY_EMPTY_ITERATOR_METHOD emptyFloatIterator

#define AS_KEY_ITERATOR asFloatIterator

#define TO_KEY_ARRAY toFloatArray
#define ENTRY_GET_KEY getFloatKey
#define PARSE_KEY parseFloat
#define LOAD_KEYS loadFloats
#define STORE_KEYS storeFloats
/* Methods (values) */
#define NEXT_VALUE nextShort
#define PREV_VALUE previousShort
#define READ_VALUE readShort
#define WRITE_VALUE writeShort
#define VALUE_ITERATOR_METHOD shortIterator

#define ENTRY_GET_VALUE getShortValue
/* Methods (keys/values) */
#define ENTRYSET float2ShortEntrySet
/* Methods that have special names depending on keys (but the special names depend on values) */
#if #keyclass(Object) || #keyclass(Reference)
#define GET_VALUE getShort
#define REMOVE_VALUE removeShort
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
#define KEY2INT(x) it.unimi.dsi.fastutil.HashCommon.float2int(x)
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
#define VALUE2INT(x) it.unimi.dsi.fastutil.HashCommon.short2int(x)
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
#include "ArrayMap.drv"

