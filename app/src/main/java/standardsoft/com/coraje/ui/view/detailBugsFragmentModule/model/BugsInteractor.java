package standardsoft.com.coraje.ui.view.detailBugsFragmentModule.model;

public interface BugsInteractor {
    void subscribeToBugsList(String phone, boolean filter);
    void subscribeToDeveloperList();
    void unsubscribeToBugsList();
}
