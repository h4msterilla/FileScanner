package nand.context;

import java.util.concurrent.atomic.AtomicLong;

public class FilesStats {

    private final AtomicLong count = new AtomicLong(0);
    private final AtomicLong sizeInBytes = new AtomicLong(0);
    private final AtomicLong lineCount = new AtomicLong(0);
    private final AtomicLong nonEmptyLineCount = new AtomicLong(0);
    private final AtomicLong commentedLines = new AtomicLong(0);


    public AtomicLong getCount() {
        return count;
    }

    public AtomicLong getSizeInBytes() {
        return sizeInBytes;
    }

    public AtomicLong getLineCount() {
        return lineCount;
    }

    public AtomicLong getNonEmptyLineCount() {
        return nonEmptyLineCount;
    }

    public AtomicLong getCommentedLines() {
        return commentedLines;
    }
}
