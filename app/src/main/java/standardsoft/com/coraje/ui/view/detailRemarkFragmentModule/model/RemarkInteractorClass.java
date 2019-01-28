package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.model;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Bugs;
import standardsoft.com.coraje.data.model.entities.PromptTask;
import standardsoft.com.coraje.data.model.entities.Remark;
import standardsoft.com.coraje.data.model.entities.SubPlanning;
import standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.events.RemarkEvent;
import standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.model.dataAccess.DetailRemarkEventListener;
import standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.model.dataAccess.RealtimeDatabase;

public class RemarkInteractorClass implements RemarkInteractor {

    private RealtimeDatabase mDatabase;

    public RemarkInteractorClass() {
        this.mDatabase = new RealtimeDatabase();
    }

    @Override
    public void subscribeToSubPlanningList() {
        mDatabase.subscribeToSubPlanningList(new DetailRemarkEventListener() {
            @Override
            public void onDataChange(List<Remark> remarkList) {

            }

            @Override
            public void onDataSubPlanning(List<SubPlanning> subPlanningList) {
                postSub(RemarkEvent.RESULT_SUBPLANNING, subPlanningList);
                subscribeToRemarkList();
                subscribeToBugsList();
                subscribeToPromptList();
            }

            @Override
            public void onDataBugs(List<Bugs> bugsList) {

            }

            @Override
            public void onDataPrompt(List<PromptTask> promptTaskList) {

            }

            @Override
            public void onError(int resMsg) {

            }
        });
    }

    @Override
    public void subscribeToBugsList() {
        mDatabase.subscribeToBugsList(new DetailRemarkEventListener() {
            @Override
            public void onDataChange(List<Remark> remarkList) {

            }

            @Override
            public void onDataSubPlanning(List<SubPlanning> subPlanningList) {

            }

            @Override
            public void onDataBugs(List<Bugs> bugsList) {
                postBugs(RemarkEvent.RESULT_BUGS, bugsList);
            }

            @Override
            public void onDataPrompt(List<PromptTask> promptTaskList) {

            }

            @Override
            public void onError(int resMsg) {

            }
        });
    }

    @Override
    public void subscribeToPromptList() {
        mDatabase.subscribeToPromptList(new DetailRemarkEventListener() {
            @Override
            public void onDataChange(List<Remark> remarkList) {

            }

            @Override
            public void onDataSubPlanning(List<SubPlanning> subPlanningList) {

            }

            @Override
            public void onDataBugs(List<Bugs> bugsList) {
            }

            @Override
            public void onDataPrompt(List<PromptTask> promptTaskList) {
                postPrompt(RemarkEvent.RESULT_PROMPT, promptTaskList);
            }

            @Override
            public void onError(int resMsg) {

            }
        });

    }

    @Override
    public void subscribeToRemarkList() {
        mDatabase.subscribeToRemarkList(new DetailRemarkEventListener() {
            @Override
            public void onDataChange(List<Remark> remarkList) {
                post(RemarkEvent.RESULT_REMARK, remarkList);
            }

            @Override
            public void onDataSubPlanning(List<SubPlanning> subPlanningList) {

            }

            @Override
            public void onDataBugs(List<Bugs> bugsList) {

            }

            @Override
            public void onDataPrompt(List<PromptTask> promptTaskList) {

            }

            @Override
            public void onError(int resMsg) {

            }
        });
    }

    private void postBugs(int typeEvent, List<Bugs> bugsList){
        postBugs(typeEvent, bugsList, 0);
    }

    private void postPrompt(int typeEvent, List<PromptTask> promptTaskList){
        postPrompt(typeEvent, promptTaskList, 0);
    }

    private void postBugs(int typeEvent, List<Bugs> bugsList, int resMsg) {
        RemarkEvent event = new RemarkEvent();
        event.setTypeEvent(typeEvent);
        event.setBugsList(bugsList);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }

    private void postPrompt(int typeEvent, List<PromptTask> promptTaskList, int resMsg) {
        RemarkEvent event = new RemarkEvent();
        event.setTypeEvent(typeEvent);
        event.setPromptTaskList(promptTaskList);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }

    private void post(int typeEvent, List<Remark> subPlannings){
        post(typeEvent, subPlannings, 0);
    }

    private void post(int typeEvent, List<Remark> remarks, int resMsg) {
        RemarkEvent event = new RemarkEvent();
        event.setTypeEvent(typeEvent);
        event.setRemarks(remarks);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }

    private void postSub(int typeEvent, List<SubPlanning> subPlannings){
        postSub(typeEvent, subPlannings, 0);
    }

    private void postSub(int typeEvent, List<SubPlanning> subPlannings, int resMsg) {
        RemarkEvent event = new RemarkEvent();
        event.setTypeEvent(typeEvent);
        event.setSubPlannings(subPlannings);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }
}
