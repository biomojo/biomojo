package org.biomojo.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarkAndCopyInputStream extends FilterInputStream {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(MarkAndCopyInputStream.class.getName());

    // Various pointers / counters
    private byte[] buffer;
    private int bufferLength = 0;
    private int bufferPos = 0;

    private boolean eof = false;

    private int segmentStart = Integer.MAX_VALUE;
    private int totalLength = 0;

    private static final int INITIAL_MAX_SEGMENTS = 1000;
    private byte[][] segmentBuffers = new byte[INITIAL_MAX_SEGMENTS][];
    private int[] segmentStarts = new int[INITIAL_MAX_SEGMENTS];
    private int[] segmentLengths = new int[INITIAL_MAX_SEGMENTS];
    private int maxSegments = INITIAL_MAX_SEGMENTS;

    private int numSegments = 0;

    private static final int DEFAULT_BUFFER_SIZE = 0xFFFF;
    private final int bufferSize;

    protected MarkAndCopyInputStream(final InputStream inputStream) {
        this(inputStream, DEFAULT_BUFFER_SIZE);
    }

    protected MarkAndCopyInputStream(final InputStream inputStream, final int bufferSize) {
        super(inputStream);
        this.bufferSize = bufferSize;
        loadBuffer();
    }

    private final void loadBuffer() {
        try {
            final boolean inSegment = segmentStart != Integer.MAX_VALUE;
            markSegmentEnd();
            buffer = new byte[bufferSize];
            bufferLength = read(buffer);
            if (bufferLength != -1) {
                bufferPos = 0;
                if (inSegment) {
                    segmentStart = 0;
                }
            } else {
                eof = true;
            }
        } catch (final IOException e) {
            throw new ParseException(e);
        }
    }

    protected final void nextByte() {
        bufferPos++;

        // read a chunk of the file when we have no more data in the buffer
        if (bufferPos >= bufferLength) {
            loadBuffer();
        }
    }

    private final void markSegmentEnd() {
        final int segmentLength = bufferPos - segmentStart;

        if (segmentLength > 0) {
            totalLength += segmentLength;
            segmentBuffers[numSegments] = buffer;
            segmentStarts[numSegments] = segmentStart;
            segmentLengths[numSegments] = segmentLength;
            ++numSegments;
            if (numSegments == maxSegments) {
                reallocateSegments();
            }
        }

        segmentStart = Integer.MAX_VALUE;
    }

    /**
     *
     */
    private void reallocateSegments() {
        final int newLen = maxSegments * 2;

        // final byte[][] newBuffers = new byte[newLen][];
        // System.arraycopy(segmentBuffers, 0, newBuffers, 0, maxSegments);
        // segmentBuffers = newBuffers;

        segmentBuffers = Arrays.copyOf(segmentBuffers, newLen);
        segmentStarts = Arrays.copyOf(segmentStarts, newLen);
        segmentLengths = Arrays.copyOf(segmentLengths, newLen);

        maxSegments = newLen;
    }

    protected final byte[] assembleSegments() {
        final byte[] assembledSegments = new byte[totalLength];
        int assembledSegmentsPos = 0;

        for (int pos = 0; pos < numSegments; ++pos) {
            // byte[] segmentData = new byte[segmentLengths[pos]];
            // System.arraycopy(segmentBuffers[pos], segmentStarts[pos],
            // segmentData, 0, segmentLengths[pos]);
            // logger.debug("{} {} {} {}", pos, segmentStarts[pos],
            // segmentLengths[pos], new String(segmentData));
            System.arraycopy(segmentBuffers[pos], segmentStarts[pos], assembledSegments, assembledSegmentsPos,
                    segmentLengths[pos]);
            assembledSegmentsPos += segmentLengths[pos];
            segmentBuffers[pos] = null;
        }

        clearSegments();

        return (assembledSegments);
    }

    private final void clearSegments() {
        numSegments = 0;
        totalLength = 0;
    }

    protected final void readToEndOfLine() {
        segmentStart = bufferPos;
        moveToEndOfLine();
        markSegmentEnd();
        movePastEndOfLineChars();
        if (eof) {
            throw new ParseException("Unexpected End of File");
        }
    }

    protected final boolean readToEndOfLineOrEOF() {
        segmentStart = bufferPos;
        moveToEndOfLine();
        markSegmentEnd();
        movePastEndOfLineChars();
        return eof;
    }

    protected final void skipNextLine() {
        moveToEndOfLine();
        movePastEndOfLineChars();
    }

    private final void moveToEndOfLine() {
        do {
            do {
                final int ch = buffer[bufferPos];
                if ((ch == 10) || (ch == 13)) {
                    return;
                }
            } while (++bufferPos < bufferLength);
            loadBuffer();
        } while (!eof);
    }

    private final void movePastEndOfLineChars() {
        do {
            do {
                final int ch = buffer[bufferPos];
                if ((ch != 10) && (ch != 13)) {
                    return;
                }
                bufferPos += 1;
            } while (bufferPos < bufferLength);
            loadBuffer();
        } while (!eof);
    }

    protected final void skipByte(final byte value) {
        if (buffer[bufferPos] != value) {
            throw new ParseException(
                    "Expected value not found. Expected = " + value + ", Received = " + buffer[bufferPos]);
        }
        nextByte();
    }

    protected final byte peek() {
        return buffer[bufferPos];
    }

    /**
     * @return the eof
     */
    public final boolean isEof() {
        return eof;
    }

    /**
     * @return the totalLength
     */
    public final int getTotalLength() {
        return totalLength;
    }
}
