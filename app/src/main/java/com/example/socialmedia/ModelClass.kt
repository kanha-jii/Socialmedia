package com.example.socialmedia


//class ModelClass {
//    public lateinit var eventImage: String
//    public lateinit var eventName: String

//    public fun getImg(): Uri {
//        return img
//    }
//    public fun getEventName(): String {
//        return eventName
//    }

//    ModelClass(img:Uri,eventName:String) {
//        this.img = img
//        this.eventName = eventName
//    }

//    constructor(img: Uri, eventName:String) : this() {
//        this.img = img
//        this.eventName = eventName
//    }
//
//    constructor(){}
//}

data class ModelClass(var eventName: String?="", var eventImage: String?="") {

}