package j2me.java.util;

import java.util.NoSuchElementException;



/**
 * Fake Collections for BB/J2ME, with just one method emptyList()
 * @author Jaak
 *
 */
public class Collections {
	
    private static class EmptyIterator implements Iterator {
        public boolean hasNext() { return false; }
        public Object next() { throw new NoSuchElementException(); }
        public void remove() { throw new IllegalStateException(); }
    }

	
	private static class EmptyList implements List {

		public int size() {
			return 0;
		}

		public boolean isEmpty() {
			return true;
		}

		public boolean contains(Object o) {
			return false;
		}

		public Iterator iterator() {
			return new EmptyIterator();
		}

		public Object[] toArray() {
			// TODO Auto-generated method stub
			return null;
		}

		public Object[] toArray(Object[] a) {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean add(Object o) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean remove(Object o) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean containsAll(Collection c) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean addAll(Collection c) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean addAll(int index, Collection c) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean removeAll(Collection c) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean retainAll(Collection c) {
			// TODO Auto-generated method stub
			return false;
		}

		public void clear() {
			// TODO Auto-generated method stub

		}

		public Object get(int index) {
			// TODO Auto-generated method stub
			return null;
		}

		public Object set(int index, Object element) {
			// TODO Auto-generated method stub
			return null;
		}

		public void add(int index, Object element) {
			// TODO Auto-generated method stub

		}

		public Object remove(int index) {
			// TODO Auto-generated method stub
			return null;
		}

		public int indexOf(Object o) {
			// TODO Auto-generated method stub
			return 0;
		}

		public int lastIndexOf(Object o) {
			// TODO Auto-generated method stub
			return 0;
		}

		public ListIterator listIterator() {
			// TODO Auto-generated method stub
			return null;
		}

		public ListIterator listIterator(int index) {
			// TODO Auto-generated method stub
			return null;
		}

		public List subList(int fromIndex, int toIndex) {
			// TODO Auto-generated method stub
			return null;
		}

	}

//	public static final List EMPTY_LIST = new Collections.EmptyList();

	public static final List emptyList() {
        return (List) new EmptyList();
    }
}

