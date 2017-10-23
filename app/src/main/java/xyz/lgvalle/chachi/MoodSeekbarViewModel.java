package xyz.lgvalle.chachi;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class MoodSeekbarViewModel extends ViewModel {

    public MutableLiveData<Integer> seekbarValue = new MutableLiveData<>();
}
