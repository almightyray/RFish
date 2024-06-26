package com.example.rfishx;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class CaptureRepository {
    private FirebaseFirestore db;
    private CollectionReference capturesRef;

    public CaptureRepository() {
        db = FirebaseFirestore.getInstance();
        capturesRef = db.collection("captures");
    }

    public void addCapture(Capture capture, OnCompleteListener<DocumentReference> onCompleteListener) {
        capturesRef.add(capture).addOnCompleteListener(onCompleteListener);
    }

    public void getCapturesByUserId(String userId, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        capturesRef.whereEqualTo("userId", userId).get().addOnCompleteListener(onCompleteListener);
    }
}
