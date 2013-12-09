package util.concurrency;

import sun.misc.Unsafe;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class ManyToManyConcurrentArrayQueue<E> implements Queue<E> {
    private static final Unsafe unsafe;
    private static final int arrayBase;
    private static final int arrayScale;

    static {
        try {
            unsafe = MiscUtil.getUnsafe();
            arrayBase = unsafe.arrayBaseOffset(Object[].class);
            arrayScale = MiscUtil.calculateShiftForScale(unsafe.arrayIndexScale(Object[].class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final E[] buffer;
    private final int mask;
    private final int capacity;

    private final AtomicSequence head = new AtomicSequence(0);
    private final AtomicSequence tail = new AtomicSequence(0);

    @SuppressWarnings("unchecked")
    public ManyToManyConcurrentArrayQueue(final int capacity) {
        final int actualCapacity = MiscUtil.findNextPositivePowerOfTwo(capacity);
        mask = actualCapacity - 1;
        this.capacity = actualCapacity;
        buffer = (E[]) new Object[actualCapacity];
    }

    public boolean add(final E e) {
        if (offer(e)) {
            return true;
        }

        throw new IllegalStateException("Queue is full");
    }

    public boolean offer(final E e) {
        if (null == e) {
            throw new NullPointerException("Null is not a valid element");
        }

        long currentHead = head.get();
        long currentTail;
        do {
            currentTail = tail.get();
            final long wrapPoint = currentTail - capacity;
            if (currentHead <= wrapPoint) {
                return false;
            }
        }
        while (!tail.compareAndSet(currentTail, currentTail + 1));

        final int elementOffset = calculateOffset((int) currentTail & mask);
        while (!unsafe.compareAndSwapObject(buffer, elementOffset, null, e)) {
            // busy spin
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    public E poll() {
        long currentTail = tail.get();
        long currentHead;
        do {
            currentHead = head.get();
            if (currentHead >= currentTail) {
                return null;
            }
        }
        while (!head.compareAndSet(currentHead, currentHead + 1));

        final int elementOffset = calculateOffset((int) currentHead & mask);
        Object e;
        do {
            e = unsafe.getObjectVolatile(buffer, elementOffset);
        }
        while (null == e);

        unsafe.putOrderedObject(buffer, elementOffset, null);

        return (E) e;
    }

    public E remove() {
        final E e = poll();
        if (null == e) {
            throw new IllegalStateException("Queue is empty");
        }

        return e;
    }

    public E element() {
        final E e = peek();
        if (null == e) {
            throw new NoSuchElementException("Queue is empty");
        }

        return e;
    }

    public E peek() {
        final int index = (int) head.get() & mask;
        return getElementVolatile(index);
    }

    public int size() {
        return (int) (tail.get() - head.get());
    }

    public boolean isEmpty() {
        return tail.get() == head.get();
    }

    public boolean contains(final Object o) {
        if (null == o) {
            return false;
        }

        for (long i = head.get(), limit = tail.get(); i < limit; i++) {
            final E e = getElementVolatile((int) i & mask);
            if (o.equals(e)) {
                return true;
            }
        }

        return false;
    }

    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    public <T> T[] toArray(final T[] a) {
        throw new UnsupportedOperationException();
    }

    public boolean remove(final Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(final Collection<?> c) {
        for (final Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    public boolean addAll(final Collection<? extends E> c) {
        for (final E o : c) {
            add(o);
        }

        return true;
    }

    public boolean removeAll(final Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(final Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    private static int calculateOffset(final int index) {
        return arrayBase + (index << arrayScale);
    }

    @SuppressWarnings("unchecked")
    private E getElementVolatile(final int index) {
        return (E) unsafe.getObjectVolatile(buffer, calculateOffset(index));
    }
}
