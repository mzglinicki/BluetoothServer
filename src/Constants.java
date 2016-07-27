/**
 * Created by mzglinicki.96 on 14.07.2016.
 */
public class Constants {

    public static final String MAIN_MODULE = "MODULE MainModule\n";
    public static final String GET_TOOL = "getTool();\n";
    public static final String PROC_MAIN = "PROC main() \n";
    public static final String GO_TO_FIRST_POINT_COMMENT = "     MoveL p10, v1000, z0, tool0; \n";
    public static final String MOVE_L_OFFS_P20_START_COMMAND = "     MoveL Offs (p20,";
    public static final String MOVE_L_OFFS_P20_END_COMMAND = ", 0), v1000, z0, tool0;";
    public static final String END_DRAWING = "     MoveL p10, v1000, z0, tool0;\n";
    public static final String END_OF_SHAPE_COMMENT = "!koniec kształtu";
    public static final String GET_FIRST_TOOL = "Black";
    public static final String GET_SECOND_TOOL = "Green";
    public static final String GET_THIRD_TOOL = "Blue";
    public static final String GET_FOURTH_TOOL = "Red";
    public static final String GET_FIFTH_TOOL = "Orange";
    public static final String GET_SIXTH_TOOL = "Yellow";
    public static final String WAITING_FOR_INPUT_DATA = "Serwer jest włączony możesz wysłać dane.";
    public static final String SAVED_DATA_INFO = "Dane zostały zapisane do pliku";
    public static final String FINISHED = "finished process";
    public static final String LOGO = "delta_logo.png";

    public static final String PROC_GET_TOOL = "PROC getTool()\n";
    public static final String SAVE_POSITION = "     MoveL p10, v1000, z50, tool0;\n";
    public static final String ABOVE_WAREHOUSE = "     MoveL p20, v1000, z50, tool0;\n";
    public static final String INSIDE_WAREHOUSE = "     MoveL p30, v1000, z50, tool0;\n";
    public static final String WAIT_TIME = "     WaitTime 1;\n";
    public static final String CLOSE_GRIPPER = "     SetGO goMechGripper, 4;\n";
    public static final String ENDPROC = "ENDPROC\n\n";
    public static final String OPER_GRIPPER = "     SetGO goMechGripper, 2;\n";

    public static final int END_OF_SHAPE_COMMAND_ID = -1;
    public static final int FIRST_TOOL_COMMAND_ID = -2;
    public static final int SECOND_TOOL_COMMAND_ID = -3;
    public static final int THIRD_TOOL_COMMAND_ID = -4;
    public static final int FOURTH_TOOL_COMMAND_ID = -5;
    public static final int FIFTH_TOOL_COMMAND_ID = -6;
    public static final int SIXTH_TOOL_COMMAND_ID = -7;

    public static final String DELTA_TITLE = "Delta Draw";
}