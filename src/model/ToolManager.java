package model;

import controller.ButtonManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mzglinicki.96 on 29.07.2016.
 */
public class ToolManager {

    private static ToolManager toolManager = null;
    private List<Tool> tools;
    private final int amountOfTools = 6;
    private final int x = 0;
    private final int y = 1;
    private final int z = 2;

    private ToolManager() {
        createListOfTool();
    }

    public static ToolManager getInstance() {
        if (toolManager == null) {
            toolManager = new ToolManager();
        }
        return toolManager;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public List<Tool> createListOfTool() {
        tools = new ArrayList<>();
        int toolId = -2;

        for (int i = 1; i <= amountOfTools; i++) {
            List<Integer> coordinates = ButtonManager.getInstance().getWarehouseCoordinates(i);
            tools.add(new Tool(toolId, coordinates.get(x), coordinates.get(y), coordinates.get(z)));
            --toolId;
        }
        return tools;
    }
}