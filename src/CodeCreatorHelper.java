/**
 * Created by mzglinicki.96 on 14.07.2016.
 */
public enum CodeCreatorHelper {

    EndOfShape(Constants.END_OF_SHAPE_COMMAND_ID) {
        @Override
        public StringBuffer getCommand() {
            return createCommand(Constants.END_OF_SHAPE_COMMENT);
        }
    }, Black(Constants.FIRST_TOOL_COMMAND_ID) {
        @Override
        public StringBuffer getCommand() {
            return createCommand(Constants.GET_FIRST_TOOL);
        }
    }, Green(Constants.SECOND_TOOL_COMMAND_ID) {
        @Override
        public StringBuffer getCommand() {
            return createCommand(Constants.GET_SECOND_TOOL);
        }
    }, Blue(Constants.THIRD_TOOL_COMMAND_ID) {
        @Override
        public StringBuffer getCommand() {
            return createCommand(Constants.GET_THIRD_TOOL);
        }
    }, Red(Constants.FOURTH_TOOL_COMMAND_ID) {
        @Override
        public StringBuffer getCommand() {
            return createCommand(Constants.GET_FOURTH_TOOL);
        }
    }, Orange(Constants.FIFTH_TOOL_COMMAND_ID) {
        @Override
        public StringBuffer getCommand() {
            return createCommand(Constants.GET_FIFTH_TOOL);
        }
    }, Yellow(Constants.SIXTH_TOOL_COMMAND_ID) {
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