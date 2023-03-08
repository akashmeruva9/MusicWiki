package com.akashmeruva.musicwiki.GenreInfo.tracks

class Track {
    var name : String? = null;
    var image_link : String? = null

    constructor()
    constructor(name : String , image_link :String)
    {
        this.name = name
        this.image_link = image_link
    }
}