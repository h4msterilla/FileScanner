package nand.context;

public class AppContext {

    private final StartOptions startOptions = new StartOptions();
    private final ExecResult execResult = new ExecResult();
    private final SearchedFiles searchedFiles = new SearchedFiles();

    public StartOptions getStartOptions() {
        return startOptions;
    }

    public ExecResult getExecResult() {
        return execResult;
    }

    public SearchedFiles getSearchedFiles() {
        return searchedFiles;
    }
}
