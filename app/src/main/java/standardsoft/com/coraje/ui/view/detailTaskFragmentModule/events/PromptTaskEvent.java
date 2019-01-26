package standardsoft.com.coraje.ui.view.detailTaskFragmentModule.events;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.PromptTask;

public class PromptTaskEvent {
    public static final int PROMTP_TASK_ADDED = 0;
    public static final int RESULT_DEVELOPER  = 1;
    public static final int ERROR_SERVER = 100;

    private List<PromptTask> promptTaskList;
    private List<Developer> developers;
    private int typeEvent;
    private int resMsg;

    // Constructor Vacio
    public PromptTaskEvent() {
    }

    // get & set
    public List<PromptTask> getPromptTaskList() {
        return promptTaskList;
    }

    public void setPromptTaskList(List<PromptTask> promptTaskList) {
        this.promptTaskList = promptTaskList;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    public int getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }

    public int getResMsg() {
        return resMsg;
    }

    public void setResMsg(int resMsg) {
        this.resMsg = resMsg;
    }
}
