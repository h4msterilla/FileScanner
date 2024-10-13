package nand.steps.parseargs;

public class HelpTextContainer {

    private final String helpText = "Usage: java -jar FileScanner.jar <path> [options]\n" +
            "\n" +
            "This utility calculates file statistics for all files in a given directory, grouped by file extensions.\n" +
            "\n" +
            "Options:\n" +
            "  <path>                        Path to the directory for statistics collection (required).\n" +
            "  --recursive                   Traverse the directory tree recursively (optional).\n" +
            "  --max-depth=<number>          Maximum depth for recursive traversal (default is unlimited).\n" +
            "  --thread=<number>             Number of threads to use for traversal (default is 1).\n" +
            "  --include-ext=<ext1,ext2,..>  Only process files with the specified extensions (comma-separated).\n" +
            "  --exclude-ext=<ext1,ext2,..>  Do not process files with the specified extensions (comma-separated).\n" +
            "  --git-ignore                  Skip files listed in the .gitignore file (optional).\n" +
            "  --output=<plain,xml,json>     Output format for statistics: plain (default), xml, or json.\n" +
            "\n" +
            "Statistics collected:\n" +
            "  1. Number of files for each file extension.\n" +
            "  2. Total file size in bytes for each file extension.\n" +
            "  3. Total number of lines in files for each extension.\n" +
            "  4. Number of non-empty lines (lines containing at least one printable character).\n" +
            "  5. Number of comment lines (single-line comments, supported for Java and Bash files).\n" +
            "\n" +
            "Examples:\n" +
            "  file-stats /home/user/project --recursive --max-depth=3 --include-ext=java,sh --output=json\n" +
            "  file-stats /var/log --exclude-ext=log,tmp --git-ignore --thread=4";

    public void showHelp() {
        System.out.println(helpText);
    }

}
