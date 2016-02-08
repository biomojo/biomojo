package org.biomojo.benchmark.micro;

import org.biomojo.sequence.BasicByteSeq;
import org.biomojo.sequence.Seq;
import org.java0.util.timing.Stopwatch;

public class SeqCreation {
    private static final Stopwatch sw = new Stopwatch();

    public static void main(final String[] args) {
        sw.start();
        final int count = 0;
        for (int i = 0; i < 100000000; ++i) {
            final Seq<?, ?> seq = new BasicByteSeq<>();
            // seq.setProp("key", "abc");
            // if (seq.getProp("key") == "abcd") {
            // ++count;
            // }
        }
        System.out.println(count);
        sw.stop();
    }
}
