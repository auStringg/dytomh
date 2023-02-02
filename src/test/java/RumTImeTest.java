import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

public class RumTImeTest {
    // o(n)
    public static void function1(long n) {
        System.out.println("o(n)算法");
        long k = 0;
        for (long i = 0; i < n; i++) {
            k++;
        }
    }

    // o(n^2)
    public static void function2(long n) {
        System.out.println("o(n^2)算法");
        long k = 0;
        for (long i = 0; i < n; i++) {
            for (long j = 0; j < n; j++) {
                k++;
            }
        }
    }

    // o(nlogn)
    public static void function3(long n) {
        System.out.println("o(nlogn)算法");
        long k = 0;
        for (long i = 0; i < n; i++) {
            for (long j = 1; j < n; j = j * 2) { // 注意这里j=1
                k++;
            }
        }
    }

    public static void main(String[] args) {
        BidiMap<String, Integer> map = new DualHashBidiMap<>();
        map.put("1", 2);
        map.put("1", 3);
        System.out.println(map.get("1"));
    }
}
