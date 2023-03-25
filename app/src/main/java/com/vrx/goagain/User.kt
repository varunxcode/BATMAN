package com.vrx.goagain

class User {

    var name : String? = null
    var username : String? = null
    var uid : String? = null

    constructor(){}

    constructor(name:String,username:String,uid:String){
        this.name = name
        this.username = username
        this.uid = uid

    }

}