package nand.steps.parseargs;

import nand.context.StartOptions;
import nand.exceptions.BadArgsException;

import java.io.File;

public class StartOptionsTunerStep {

    private StartOptions startOptions = null;

    public StartOptionsTunerStep(StartOptions startOptions) {
        if (startOptions == null) {
            throw new RuntimeException();
        }
        this.startOptions = startOptions;
    }

    public void tune(String[] argsArray) throws BadArgsException {

        if (argsArray.length == 0) {
            throw new BadArgsException("Args array is empty!");
        }
        /*
          Getting args
         */
        for (int i = 0; i < argsArray.length; i++) {
            String arg = null;
            try {
                arg = argsArray[i];
            } catch (IndexOutOfBoundsException e) {
                throw new BadArgsException("Cant find arg on position " + i + " (count starts with 0)");
            }

            if (i == 0) {
                File file = new File(arg);
                if (!file.exists()) {
                    throw new BadArgsException("Dir not exists: " + arg, false);
                }
                if (!file.isDirectory()) {
                    throw new BadArgsException("Plz enter directory!", false);
                }
                startOptions.setPath(arg);
                continue;
            }

            if (arg.equalsIgnoreCase("--recursive")) {
                startOptions.setRecursion(true);
                continue;
            }

            if (arg.startsWith("--max-depth=")) {
                try {
                    String depthStr = arg.split("=")[1];
                    int depth = Integer.parseInt(depthStr);
                    if (!(depth >= 0)) {
                        throw new BadArgsException("--max-depth=<number> must be grater or equals then 0!", false);
                    }
                    startOptions.setMaxDepth(depth);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    throw new BadArgsException("Not correct definition for --max-depth=<number>", e);
                }
                continue;
            }

            if (arg.startsWith("--thread=")) {
                try {
                    String threadCountStr = arg.split("=")[1];
                    int threadCount = Integer.parseInt(threadCountStr);
                    if (!(threadCount >= 1)) {
                        throw new BadArgsException("--thread=<number> must be grater or equals then 1!", false);
                    }
                    startOptions.setThread(threadCount);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    throw new BadArgsException("Not correct definition for --thread=<number>", e);
                }
                continue;
            }

            if (arg.startsWith("--include-ext=") || arg.startsWith("--exclude-ext=")) {
                String key = null;
                try {
                    String[] keyAndArgs = arg.split("=");
                    key = keyAndArgs[0];
                    String argsAndCommas = keyAndArgs[1];
                    String[] args = argsAndCommas.split(",");
                    for (String protoExt : args) {
                        String ext = protoExt.trim();
                        if (ext.isEmpty()) {
                            throw new BadArgsException(key + "<ext1,ext2,..> - ext can not be empty", false);
                        }
                        if (key.startsWith("--i")) {
                            startOptions.getInclude().add(ext);
                        } else {
                            startOptions.getExclude().add(ext);
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    throw new BadArgsException("Not correct definition for " + key + "<ext1,ext2,..>", e);
                }
                continue;
            }

            throw new BadArgsException("Unknown arg: " + arg);
        }

        /*
          Rule check
         */

        if (!startOptions.getInclude().isEmpty() && !startOptions.getExclude().isEmpty()) {
            throw new BadArgsException("You can not include and exclude files at same time. select only one!", false);
        }

    }

}
