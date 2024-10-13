package nand.context;

import java.util.ArrayList;
import java.util.List;

public class StartOptions {

    private String path = null;
    private boolean recursion = false;
    private Integer maxDepth = null;
    private Integer thread = null;
    private final List<String> include = new ArrayList<>();
    private final List<String> exclude = new ArrayList<>();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isRecursion() {
        return recursion;
    }

    public void setRecursion(boolean recursion) {
        this.recursion = recursion;
    }

    public Integer getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(Integer maxDepth) {
        this.maxDepth = maxDepth;
    }

    public Integer getThread() {
        return thread;
    }

    public void setThread(Integer thread) {
        this.thread = thread;
    }

    public List<String> getInclude() {
        return include;
    }

    public List<String> getExclude() {
        return exclude;
    }
}
