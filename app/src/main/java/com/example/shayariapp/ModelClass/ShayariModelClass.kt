package com.example.shayariapp

data class ShayariModelClass (var c_id:Int,var name:String)

data class ShayariDisplay(var s_id:Int,var Shayari:String,var c_id:Int,var fav:Int)

data class FavouriteShayariclass(var s_id:Int,var Shayari:String,var fav:Int )