/*
 * Copyright (C) 2014  Hugh Eaves
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.biomojo.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class MarkAndCopyInputStream.
 */
public class MarkAndCopyInputStream extends FilterInputStream {
    
    /** The Constant logger. */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(MarkAndCopyInputStream.class.getName());

    /** The buffer. */
    // Various pointers / counters
    private byte[] buffer;
    
    /** The buffer length. */
    private int bufferLength = 0;
    
    /** The buffer pos. */
    private int bufferPos = 0;

    /** The eof. */
    private boolean eof = false;

    /** The segment start. */
    private int segmentStart = Integer.MAX_VALUE;
    
    /** The total length. */
    private int totalLength = 0;

    /** The Constant INITIAL_MAX_SEGMENTS. */
    private static final int INITIAL_MAX_SEGMENTS = 1000;
    
    /** The segment buffers. */
    private byte[][] segmentBuffers = new byte[INITIAL_MAX_SEGMENTS][];
    
    /** The segment starts. */
    private int[] segmentStarts = new int[INITIAL_MAX_SEGMENTS];
    
    /** The segment lengths. */
    private int[] segmentLengths = new int[INITIAL_MAX_SEGMENTS];
    
    /** The max segments. */
    private int maxSegments = INITIAL_MAX_SEGMENTS;

    /** The num segments. */
    private int numSegments = 0;

    /** The Constant DEFAULT_BUFFER_SIZE. */
    private static final int DEFAULT_BUFFER_SIZE = 0xFFFF;
    
    /** The buffer size. */
    private final int bufferSize;

    /**
     * Instantiates a new mark and copy input stream.
     *
     * @param inputStream the input stream
     */
    protected MarkAndCopyInputStream(final InputStream inputStream) {
        this(inputStream, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Instantiates a new mark and copy input stream.
     *
     * @param inputStream the input stream
     * @param bufferSize the buffer size
     */
    protected MarkAndCopyInputStream(final InputStream inputStream, final int bufferSize) {
        super(inputStream);
        this.bufferSize = bufferSize;
        loadBuffer();
    }

    /**
     * Load buffer.
     */
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

    /**
     * Next byte.
     */
    protected final void nextByte() {
        bufferPos++;

        // read a chunk of the file when we have no more data in the buffer
        if (bufferPos >= bufferLength) {
            loadBuffer();
        }
    }

    /**
     * Mark segment end.
     */
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
     * Reallocate segments.
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

    /**
     * Assemble segments.
     *
     * @return the byte[]
     */
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

    /**
     * Clear segments.
     */
    private final void clearSegments() {
        numSegments = 0;
        totalLength = 0;
    }

    /**
     * Read to end of line.
     */
    protected final void readToEndOfLine() {
        segmentStart = bufferPos;
        moveToEndOfLine();
        markSegmentEnd();
        movePastEndOfLineChars();
        if (eof) {
            throw new ParseException("Unexpected End of File");
        }
    }

    /**
     * Read to end of line or eof.
     *
     * @return true, if successful
     */
    protected final boolean readToEndOfLineOrEOF() {
        segmentStart = bufferPos;
        moveToEndOfLine();
        markSegmentEnd();
        movePastEndOfLineChars();
        return eof;
    }

    /**
     * Skip next line.
     */
    protected final void skipNextLine() {
        moveToEndOfLine();
        movePastEndOfLineChars();
    }

    /**
     * Move to end of line.
     */
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

    /**
     * Move past end of line chars.
     */
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

    /**
     * Skip byte.
     *
     * @param value the value
     */
    protected final void skipByte(final byte value) {
        if (buffer[bufferPos] != value) {
            throw new ParseException(
                    "Expected value not found. Expected = " + value + ", Received = " + buffer[bufferPos]);
        }
        nextByte();
    }

    /**
     * Peek.
     *
     * @return the byte
     */
    protected final byte peek() {
        return buffer[bufferPos];
    }

    /**
     * Checks if is eof.
     *
     * @return the eof
     */
    public final boolean isEof() {
        return eof;
    }

    /**
     * Gets the total length.
     *
     * @return the totalLength
     */
    public final int getTotalLength() {
        return totalLength;
    }
}
