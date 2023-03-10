package com.akashmeruva.musicwiki.models

class Album {

    var name : String? = null;
    var artist : String? = null
    var image_link : String? = null

    constructor()
    constructor(name : String  , artist: String, image_link :String)
    {
        this.name = name
        this.artist = artist
        this.image_link = image_link
    }

}