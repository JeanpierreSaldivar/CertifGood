package com.saldivar.certifgood.repo

import com.google.firebase.FirebaseOptions

object conexionFirebase {
     var optionsDesarrollo = FirebaseOptions.Builder()
        .setProjectId("certifgooddesarrollo")
        .setApplicationId("1:1074649664988:android:31ed970e2556653340d319")
        .setApiKey("AIzaSyALDhZNcD3jhIwVuhZzjuqEm6xQ-cOTzUg")
        .setDatabaseUrl("https://certifgooddesarrollo.firebaseio.com")
        .build()

    var optionsProduccion = FirebaseOptions.Builder()
        .setProjectId("certifgood")
        .setApplicationId("1:665629529032:android:2394934587766fc5ee66fd")
        .setApiKey("AIzaSyBU7kuF_028afGv_YHXIxW33E4HgElrRzw")
        .setDatabaseUrl("https://certifgood.firebaseio.com")
        .build()
}