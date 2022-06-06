public class Problem3b {
    private int getParent(int i) {
        return i >> 1; // getting the floor of division by 2
    }

    private int getGrandparent(int i) {
        return i >> 2; // getting the floor of division by 4
    }

    private boolean hasGrandparent(int i) {
        return i != 1 && i != 2 && i != 3;
    }

    private void pushUpMin(List<T> heap , int i) {
        while(hasGrandparent(i) &&
                heap.get(i).compareTo(heap.get(getGrandparent(i))) < 0) {
            swap(i, getGrandparent(i), heap);
            i = getGrandparent(i);
        }
    }

    private void pushUpMax(List<T> heap , int i) {
        while(hasGrandparent(i) &&
                heap.get(i).compareTo(heap.get(getGrandparent(i))) > 0) {
            swap(i, getGrandparent(i), heap);
            i = getGrandparent(i);
        }
    }

    private void maintainMinMaxHeapProp(List<T> heap, int i) {
        if (!(i == 1)) {
            if (isEvenLevel(i)) {
                if (heap.get(i).compareTo(heap.get(getParent(i))) < 0) {
                    pushUpMin(heap, i);
                } else {
                    swap(i, getParent(i), heap);
                    i = getParent(i);
                    pushUpMax(heap, i);
                }
            } else if (heap.get(i).compareTo(heap.get(getParent(i))) > 0) {
                pushUpMax(heap, i);
            } else {
                swap(i, getParent(i), heap);
                i = getParent(i);
                pushUpMin(heap, i);
            }
        }
    }

    public void insert(T item) {
        if (this.isEmpty()) {
            this.array.add(item);
            ++this.size;
        } else if (!this.isFull()) {
            this.array.add(item);
            this.maintainMinMaxHeapProp(this.array, this.size);
            ++this.size;
        } else {
            throw new IllegalStateException("Invalid Operation!");
        }
    }
}