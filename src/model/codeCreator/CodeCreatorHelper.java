package model.codeCreator;

import static model.Constants.*;

/**
 * Created by mzglinicki.96 on 14.07.2016.
 */
public enum CodeCreatorHelper {

    EndOfShape(END_OF_SHAPE_COMMAND_ID, END_OF_SHAPE_COMMENT),
    Black(BLACK_TOOL_COMMAND_ID, GET_BLACK_TOOL),
    Green(GREEN_TOOL_COMMAND_ID, GET_GREEN_TOOL),
    Blue(BLUE_TOOL_COMMAND_ID, GET_BLUE_TOOL),
    Red(RED_TOOL_COMMAND_ID, GET_RED_TOOL),
    Orange(ORANGE_TOOL_COMMAND_ID, GET_ORANGE_TOOL),
    Yellow(YELLOW_TOOL_COMMAND_ID, GET_YELLOW_TOOL);

    private int commandId;
    private String colorName;

    CodeCreatorHelper(int commandId, String colorName) {
        this.commandId = commandId;
        this.colorName = colorName + "\n";
    }

    public int getCommandId() {
        return commandId;
    }

    public String getColorName() {
        return colorName;
    }
}