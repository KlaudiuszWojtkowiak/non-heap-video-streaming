package digital.dac.klaudiusz.movie;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.lang.String.format;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Information {

    public static String getMemoryInformation(long nonHeapSize){
        long heapSize = Runtime.getRuntime().totalMemory();
        return format("Current memory usage: HEAP: %s, NO-HEAP: %s",formatSize(heapSize),formatSize(nonHeapSize));
    }

    public static String formatSize(long v) {
        if (v < 1024) return v + " B";
        int z = (63 - Long.numberOfLeadingZeros(v)) / 10;
        return format("%.1f %sB", (double)v / (1L << (z*10)), " KMGTPE".charAt(z));
    }
}
