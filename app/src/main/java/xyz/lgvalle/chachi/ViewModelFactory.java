package xyz.lgvalle.chachi;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import xyz.lgvalle.chachi.guardian.TheGuardianDataSource;

class ViewModelFactory implements ViewModelProvider.Factory {

    private TheGuardianDataSource dataSource;

    ViewModelFactory(TheGuardianDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ArticleViewModel.class)) {
            return (T) new ArticleViewModel(dataSource);
        }
        if (modelClass.isAssignableFrom(MoodSeekbarViewModel.class)) {
            return (T) new MoodSeekbarViewModel();
        }
        return null;
    }
}
