package codeCreator;

/**
 * Created by mzglinicki.96 on 14.07.2016.
 */
public enum CodeCreatorHelper {

    EndOfShape(-1) {
        @Override
        public StringBuffer getCommand() {
            return createCommand(Constants.END_OF_SHAPE_COMMENT);
        }
    }, Black(-2) {
        @Override
        public StringBuffer getCommand() {
            return createCommand(Constants.GET_FIRST_TOOL);
        }
    }, Green(-3) {
        @Override
        public StringBuffer getCommand() {
            return createCommand(Constants.GET_SECOND_TOOL);
        }
    }, Blue(-4) {
        @Override
        public StringBuffer getCommand() {
            return createCommand(Constants.GET_THIRD_TOOL);
        }
    }, Red(-5) {
        @Override
        public StringBuffer getCommand() {
            return createCommand(Constants.GET_FOURTH_TOOL);
        }
    }, Orange(-6) {
        @Override
        public StringBuffer getCommand() {
            return createCommand(Constants.GET_FIFTH_TOOL);
        }
    }, Yellow(-7) {
        @Override
        public StringBuffer getCommand() {
            return createCommand(Constants.GET_SIXTH_TOOL);
        }
    };

    public abstract StringBuffer getCommand();

    private int commandId;

    CodeCreatorHelper(int commandId) {
        this.commandId = commandId;
    }

    public int getCommandId() {
        return commandId;
    }

    private static StringBuffer createCommand(String command) {
        StringBuffer newCommand = new StringBuffer();
        newCommand.append(command).append("\n");
        return newCommand;
    }
}