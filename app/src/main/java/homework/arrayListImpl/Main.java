package homework.arrayListImpl;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        MyArrayList<Dummy> dummies = new MyArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 15; i++) {
            int val = rand.nextInt(100);
            dummies.add(new Dummy(val));
        }
        System.out.println(dummies);
        dummies.sort(Comparator.comparingInt(Dummy::getVal).reversed());
        System.out.println(dummies);
    }

    static class Dummy {
        int val;

        public Dummy(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "val=" + val;
        }
    }
}
