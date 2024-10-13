package nand.steps.search;

import nand.context.AppContext;
import nand.context.SearchedFiles;
import nand.context.StartOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Searcher {

    private AppContext context = null;
    private SearchedFiles searchedFiles = null;

    private String startDirStr;
    private boolean isRecursive;
    private boolean isInfinitelyRecursive;
    private int recursiveDepth;
    private List<String> include = null;
    private List<String> exclude = null;
    private Thread searchWorker = null;

    public Searcher(AppContext context) {
        if (context == null) {
            throw new RuntimeException("App context can not be null!");
        }
        this.context = context;
        this.searchedFiles = context.getSearchedFiles();

        StartOptions options = context.getStartOptions();
        isRecursive = options.isRecursion();
        if (isRecursive && options.getMaxDepth() == null) {
            isInfinitelyRecursive = true;
        }
        if (options.getMaxDepth() != null) {
            recursiveDepth = options.getMaxDepth();
        }
        if (!isRecursive) {
            recursiveDepth = 0;
        }

        if (!options.getInclude().isEmpty()) {
            include = new ArrayList<>();
            for (String i : options.getInclude()) {
                include.add("." + i);
            }
        }

        if (!options.getExclude().isEmpty()) {
            exclude = new ArrayList<>();
            for (String e : options.getExclude()) {
                exclude.add("." + e);
            }
        }
        startDirStr = options.getPath();
    }

    public void search() {
        searchWorker = new Thread(this::threadBody);
        searchWorker.start();
    }

    public void join() {
        try {
            searchWorker.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void threadBody() {
        File startDir = new File(startDirStr);

        LinkedList<File> dirs = new LinkedList<>();
        LinkedList<Integer> depths = new LinkedList<>();

        dirs.add(startDir);
        depths.add(0);

        while (!dirs.isEmpty()) {
            File dir = dirs.removeFirst();
            int depth = depths.removeFirst();

            if (depth > recursiveDepth && !isInfinitelyRecursive) {
                continue;
            }

            File[] childArray = dir.listFiles();
            if (childArray == null) {
                continue;
            }

            for (File child : childArray) {
                if (child.isDirectory()) {
                    dirs.addLast(child);
                    depths.addLast(depth + 1);
                } else if (child.isFile()) {
                    if (isFilenameOK(child)) {
                        searchedFiles.addFile(child);
                    }
                }
            }
        }

        searchedFiles.markEnd();
    }

    private boolean isFilenameOK(File file) {
        String filename = file.getName();

        if (include != null) {
            boolean isOk = false;
            for (String i : include) {
                if (filename.endsWith(i)) {
                    isOk = true;
                    break;
                }
            }
            if (!isOk) return false;
        }

        if (exclude != null) {
            boolean isOk = true;
            for (String e : exclude) {
                if (filename.endsWith(e)) {
                    isOk = false;
                    break;
                }
            }
            if (!isOk) return false;
        }

        return true;
    }


}
