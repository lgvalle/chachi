package xyz.lgvalle.chachi;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

class ViewModelFactory implements ViewModelProvider.Factory {

    private final DummyArticleRepository dummyArticleRepository;

    ViewModelFactory(DummyArticleRepository dummyArticleRepository) {
        this.dummyArticleRepository = dummyArticleRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ArticleViewModel.class)) {
            return (T) new ArticleViewModel(dummyArticleRepository);
        }
        return null;
    }
}
