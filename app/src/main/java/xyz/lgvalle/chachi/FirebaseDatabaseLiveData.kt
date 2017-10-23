package xyz.lgvalle.chachi

import android.arch.lifecycle.LiveData
import android.util.Log

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener

import xyz.lgvalle.chachi.guardian.Article


class FirebaseDatabaseLiveData : LiveData<List<Article>>() {

    override fun onActive() {
        super.onActive()

        FirebaseDatabase.getInstance()
                .getReference("feed/articles")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            value = dataSnapshot.getValue(object : GenericTypeIndicator<List<Article>>() {

                            })
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {}
                })

    }


    override fun onInactive() {
        super.onInactive()

        Log.d(TAG, "onActive")
    }

    companion object {

        private val TAG = FirebaseDatabaseLiveData::class.java.simpleName
    }
}
