/**
 * MusicVideoManagerDatabaseHandler object that manages the database of music videos,
 * playlist entries and comments
 */
const musicVideoManagerDatabaseHandler = require('./musicVideoManagerDatabaseHandler');

/**
 * Sort states enum (Song list)
 */
const sortEnumsSongList = [
    "TITLE_ASC",
    "TITLE_DESC",
    "ARTIST_ASC",
    "ARTIST_DESC"
];

/**
 * Sort states enum (Playlist)
 */
const sortEnumsPlaylist = [
    "UPVOTES_ASC",
    "UPVOTES_DESC",
    "USER_ASC",
    "USER_DESC",
    "DATE_ASC",
    "DATE_DESC",
    "TITLE_ASC",
    "TITLE_DESC",
    "ARTIST_ASC",
    "ARTIST_DESC"
];


/**
 * Class that handles all requests for the advent calendar
 * @author Dennis Keller, Niklas Mikeler
 */
class MusicVideoManagerWebHandler {

    // Constructor
    constructor() {

        // Log that the object was created
        console.log(">> Created MusicVideoManagerWebHandler object");

    }

    /**
     * Check if a given string is a supported sort type (Song list)
     * @param {string} sortType 
     */
    isCorrectSongListSortType(sortType) {

        // check therfore the "enum" contains the given sort type
        for (var i = 0; i < sortEnumsSongList.length; i++) {
            // if yes return true
            if (sortEnumsSongList[i] === sortType) { return true; }
        }

        // if no return false
        return false;
    }

    /**
     * Check if a given string is a supported sort type (Playlist)
     * @param {string} sortType 
     */
    isCorrectPlaylistSortType(sortType) {

        // check therfore the "enum" contains the given sort type
        for (var i = 0; i < sortEnumsPlaylist.length; i++) {
            // if yes return true
            if (sortEnumsPlaylist[i] === sortType) { return true; }
        }

        // if no return false
        return false;
    }
    

}

// export one DoorHandler object to every script that requires this module
// (this only get's executed once at "npm start")
module.exports = new MusicVideoManagerWebHandler();
