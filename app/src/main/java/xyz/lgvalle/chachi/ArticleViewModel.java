package xyz.lgvalle.chachi;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class ArticleViewModel extends ViewModel {

    private final MutableLiveData<List<DummyArticleRepository.DummyItem>> dummyItems = new MutableLiveData<>();
    private final MutableLiveData<DummyArticleRepository.DummyItem> selectedItem = new MutableLiveData<>();

    public ArticleViewModel(DummyArticleRepository dummyArticleRepository) {
        dummyItems.setValue(dummyArticleRepository.getItems());
    }

    MutableLiveData<List<DummyArticleRepository.DummyItem>> dummyItems() {
        return dummyItems;
    }

    void selectItem(String itemId) {
        DummyArticleRepository.DummyItem dummyItem = DummyArticleRepository.getItemById(itemId);
        selectedItem.setValue(dummyItem);
    }

    MutableLiveData<DummyArticleRepository.DummyItem> getSelectedItem() {
        return selectedItem;
    }


}
