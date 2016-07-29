package model;

/**
 * Created by mzglinicki.96 on 14.07.2016.
 */
public class Constants {

    public static final String MAIN_MODULE = "MODULE MainModule\n";
    public static final String GET_TOOL = "getTool();\n";
    public static final String GET_TOOL_START = "     getTool(";
    public static final String PROC_MAIN = "PROC main() \n";
    public static final String GO_TO_FIRST_POINT_COMMEND = "     MoveL p10, v1000, z0, tool0; \n";
    public static final String MOVE_L_OFFS_P20_START_COMMAND = "     MoveL Offs (p20,";
    public static final String MOVE_L_OFFS_P20_END_COMMAND = ", 0), v1000, z0, tool0;";
    public static final String END_DRAWING = "     MoveL p10, v1000, z0, tool0;\n";
    public static final String END_OF_SHAPE_COMMENT = "!koniec kształtu";
    public static final String GET_FIRST_TOOL = "Czarny";
    public static final String GET_SECOND_TOOL = "Zielony";
    public static final String GET_THIRD_TOOL = "Niebieski";
    public static final String GET_FOURTH_TOOL = "Czerwony";
    public static final String GET_FIFTH_TOOL = "Pomarańczowy";
    public static final String GET_SIXTH_TOOL = "Żółty";
    public static final String WAITING_FOR_INPUT_DATA = "Serwer jest włączony możesz wysłać dane.";
    public static final String SAVED_DATA_INFO = "Dane zostały zapisane do pliku";
    public static final String FINISHED = "finished process";

    public static final String PROC_GET_TOOL = "PROC getTool(int x, int y, int z)\n";

    public static final String SAVE_POSITION = "     MoveL p10, v1000, z50, tool0;\n";
    public static final String ABOVE_WAREHOUSE = "     MoveL p20, v1000, z50, tool0;\n";
    public static final String INSIDE_WAREHOUSE = "     MoveL p30, v1000, z50, tool0;\n";
    public static final String ABOVE_WAREHOUSE_WITH_PARAMETERS = "     MoveL p20(x,y), v1000, z50, tool0;\n";
    public static final String INSIDE_WAREHOUSE_WITH_PARAMETERS = "     MoveL p20(x,y,z), v1000, z50, tool0;\n";


    public static final String WAIT_TIME = "     WaitTime 1;\n";
    public static final String CLOSE_GRIPPER = "     SetGO goMechGripper, 4;\n";
    public static final String ENDPROC = "ENDPROC\n\n";
    public static final String OPER_GRIPPER = "     SetGO goMechGripper, 2;\n";
    public static final String START_NEW_SHAPE = "!było -1, jedź do początku nowego kształtu, nie było wymiany narzędzia\n";
    public static final String CHANGED_TOOL_GO_TO_START_POINT = "!wymiana narzędzia, jedź do początku nowego kształtu\n";
    public static final String FIRST_POINT_START = "     MoveL p10(";
    public static final String FIRST_POINT_END = "), v1000, z0, tool0; \n";

    public static final String CHECK_DATA = "Sprawdź poprawność wprowadzonych danych:\n";
    public static final String WAREHOUSE_SETTINGS = "\nUstawienia magazynu:\n";
    public static final String FIRST_PLACE_EMPTY = " Magazyn 1: pusty:\n";
    public static final String WAREHOUSE = "magazyn ";
    public static final String FIRST_WAHEHOUSE_BLACK = "magazyn 1: Czarny";
    public static final String ONE_TOOL = "Magazyn może być pusty. Rysujesz jednym kolorem. Narzędzie powinno być zainstalowane";
    public static final String PAPER_ORIENTATION = "Orientacja kartki: ";
    public static final String VERTICALLY = "pionowa\n";
    public static final String HORIZONTALLY = "pozioma\n";
    public static final String PAPER_FORMAT = "Format kartki: ";
    public static final String PAPER_FORMAT_A_4 = "A4\n";
    public static final String PAPER_FORMAT_A_3 = "A3\n";
    public static final String TOOL_INSTALLED = "W chwytaku nie powinno byĆ zamontowanego narzędzia\n";
    public static final String TOOL_NOT_INSTALLED = "W chwytaku powinno byĆ zamontowane narzędzie\n";

    public static final int END_OF_SHAPE_COMMAND_ID = -1;
    public static final int FIRST_TOOL_COMMAND_ID = -2;
    public static final int SECOND_TOOL_COMMAND_ID = -3;
    public static final int THIRD_TOOL_COMMAND_ID = -4;
    public static final int FOURTH_TOOL_COMMAND_ID = -5;
    public static final int FIFTH_TOOL_COMMAND_ID = -6;
    public static final int SIXTH_TOOL_COMMAND_ID = -7;
}