package nand;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nand.context.AppContext;
import nand.exceptions.BadArgsException;
import nand.steps.parseargs.HelpTextContainer;
import nand.steps.parseargs.StartOptionsTunerStep;
import nand.steps.reader.Reader;
import nand.steps.search.Searcher;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {

        try {
            AppContext context = new AppContext();

            StartOptionsTunerStep tuner = new StartOptionsTunerStep(context.getStartOptions());
            tuner.tune(args);

            ObjectMapper objectMapper = new ObjectMapper();
            String optionsJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(context.getStartOptions());
            System.out.println(optionsJson);

            Searcher searcher = new Searcher(context);
            searcher.search();

            Reader reader = new Reader(context);
            reader.read();

            searcher.join();
            reader.join();

            String resultJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(context.getExecResult());
            System.out.print(resultJson);

        } catch (BadArgsException e) {
            e.printStackTrace();
            if (e.askForHelp) {
                HelpTextContainer handler = new HelpTextContainer();
                handler.showHelp();
            }
            System.exit(1);
        }


    }
}