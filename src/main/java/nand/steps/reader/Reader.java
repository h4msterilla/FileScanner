package nand.steps.reader;

import nand.context.AppContext;
import nand.context.ExecResult;
import nand.context.FilesStats;
import nand.context.SearchedFiles;

import java.io.*;
import java.nio.file.Files;

public class Reader {

    private AppContext context = null;
    private SearchedFiles searchedFiles = null;
    private ExecResult execResult = null;
    private int threadCount = 1;
    private Thread[] threads = null;
    private final VerifyComment verifyComment = new VerifyComment();

    public Reader(AppContext context) {
        if (context == null) {
            throw new RuntimeException("context can not be null");
        }
        this.context = context;
        this.searchedFiles = context.getSearchedFiles();
        this.execResult = context.getExecResult();
        if (context.getStartOptions().getThread() != null) {
            threadCount = context.getStartOptions().getThread();
        }
        threads = new Thread[threadCount];
    }

    public void read() {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(this::threadBody);
            threads[i].start();
        }
    }

    public void join() {
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void threadBody() {

        while (true) {
            File file = searchedFiles.getFile();
            if (file == null) {
                if (searchedFiles.isEnd()) {
                    break;
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                continue;
            }
            processFile(file);
        }
    }

    private void processFile(File file) {
        String ext = getFileExt(file);
        boolean isSupportedCommentExt = verifyComment.isSupportedExt(ext);
        FilesStats stats = execResult.getFilesStatsByExt(ext);
        stats.getCount().addAndGet(1);

        try {
            long sizeInBytes = Files.size(file.toPath());
            stats.getSizeInBytes().addAndGet(sizeInBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int lineCounter = 0;
        int nonEmptyLineCounter = 0;
        int commentedLineCounter = 0;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            String line;
            while ((line = reader.readLine()) != null) {
                lineCounter++;
                if (!line.isEmpty()) {
                    nonEmptyLineCounter++;
                }
                if (isSupportedCommentExt && verifyComment.isComment(line, ext)) {
                    commentedLineCounter++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        stats.getLineCount().addAndGet(lineCounter);
        stats.getCommentedLines().addAndGet(commentedLineCounter);
        stats.getNonEmptyLineCount().addAndGet(nonEmptyLineCounter);

    }

    private String getFileExt(File file) {
        String filename = file.getName();
        if (filename.contains(".")) {
            return filename.substring(filename.lastIndexOf("."));
        } else {
            return "file with out format";
        }
    }

}