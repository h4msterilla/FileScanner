package nand.context;

import com.sun.istack.internal.Nullable;

import java.io.File;
import java.util.LinkedList;

public class SearchedFiles {

    private boolean isEnd = false;
    private final LinkedList<File> files = new LinkedList<>();

    public synchronized void addFile(File file) {
        files.addLast(file);
        //System.out.println("FILE: " + file.getPath());
    }

    public synchronized void markEnd() {
        isEnd = true;
    }

    public synchronized boolean isEnd() {
        return isEnd && files.isEmpty();
    }

    @Nullable
    public synchronized File getFile() {
        if (!files.isEmpty()) {
            return files.removeFirst();
        }
        return null;
    }

}
