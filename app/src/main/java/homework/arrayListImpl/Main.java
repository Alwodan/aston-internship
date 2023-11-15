package homework.arrayListImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {

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
            return "Dummy{" +
                    "val=" + val +
                    '}';
        }
    }
}
