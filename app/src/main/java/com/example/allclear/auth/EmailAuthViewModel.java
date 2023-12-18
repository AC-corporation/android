//package com.example.allclear.auth;
//
//import android.util.Log;
//
//import androidx.lifecycle.MediatorLiveData;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.ViewModel;
//
//public class EmailAuthViewModel extends ViewModel {
//    public MutableLiveData<String> email = new MutableLiveData<>("");
//    public MediatorLiveData<Boolean> isButtonValid = new MediatorLiveData<>();
//
//    public EmailAuthViewModel() {
//        isButtonValid.addSource(email, s -> checkButton());
//    }
//
//    public void checkButton() {
//        String currentEmail = email.getValue();
//        Log.d("LYB", "이메일 확인하는 중");
//        if(currentEmail != null && !currentEmail.isEmpty()){
//            Log.d("LYB", "값이 있음! 비어있지 않아!");
//            isButtonValid.setValue(true); // 이메일이 입력되면 버튼을 활성화
//        } else {
//            isButtonValid.setValue(false); // 이메일이 비어있으면 버튼을 비활성화
//        }
//    }
//
//
//    public MutableLiveData<String> getEmail() {
//        return email;
//    }
//
//    public MediatorLiveData<Boolean> getIsButtonValid() {
//        Log.d("LYB", "버튼의 현재 값 = "+isButtonValid.getValue());
//        return isButtonValid;
//    }
//}
//
