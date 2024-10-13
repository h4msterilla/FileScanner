package nand.steps.reader;

import java.util.HashMap;
import java.util.Map;

public class VerifyComment {

    private Map<String, String> extToComment = new HashMap<>();

    public VerifyComment() {
        extToComment.put(".sh", "#");
        extToComment.put(".java", "//");
        extToComment.put(".py", "#");
        extToComment.put(".bat", "#");
        extToComment.put(".yml","#");
        extToComment.put(".js", "//");
    }

    public boolean isSupportedExt(String ext) {
        return extToComment.containsKey(ext);
    }

    public boolean isComment(String line, String ext) {
        if (extToComment.containsKey(ext)) {
            return line.startsWith(extToComment.get(ext));
        }
        return false;
    }

}
