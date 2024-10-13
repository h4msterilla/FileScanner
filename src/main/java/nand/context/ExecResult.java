package nand.context;

import java.util.HashMap;
import java.util.Map;

public class ExecResult {

    private final Map<String, FilesStats> stats = new HashMap<>();

    public synchronized FilesStats getFilesStatsByExt(String ext) {
        if (!stats.containsKey(ext)) {
            stats.put(ext, new FilesStats());
        }
        return stats.get(ext);
    }

    public Map<String, FilesStats> getStats() {
        return stats;
    }

}
