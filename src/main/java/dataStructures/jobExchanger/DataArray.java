package dataStructures.jobExchanger;

import sun.misc.Unsafe;
import util.concurrency.MiscUtil;

public class DataArray<T> {

    private static final Unsafe unsafe;
    private final DataElement<T>[] elements;
    private final int size;
    private static final int arrayBase;
    private static final int shiftForScale;


    static {
        try {
            unsafe = MiscUtil.getUnsafe();
            arrayBase = unsafe.arrayBaseOffset(Object[].class);
            shiftForScale = MiscUtil.calculateShiftForScale(unsafe.arrayIndexScale(DataElement[].class));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public DataArray(int size) {
        this.size = size;
        this.elements = new DataElement[size];
    }

    public int getSize() {
        return size;
    }

    public int getOffset(int index) {
        return calculateOffset(index);
    }

    public void setElement(DataElement element, int position) {
        elements[position] = element;
    }


    public boolean addElement(DataElement element, int index) {
        boolean added = false;
        int offset = getOffset(index);
        if (unsafe.compareAndSwapObject(elements, offset, null, element)) {
            return true;
        } else {
            return false;
        }

    }

    public DataElement consumeElement(int index) {

        int offset = getOffset(index);
        DataElement element = elements[index];
        if (unsafe.compareAndSwapObject(elements, offset, elements[index], null)) {
            return element;
        } else {
            return null;
        }

    }

    private static int calculateOffset(final int index) {
        return arrayBase + (index << shiftForScale);
    }


}
