// import express and create a new router
const router = require('express').Router();


/**
 * MusicVideoManagerWebHandler module
 * (contains MusicVideoManagerWebHandler object)
 */
const musicVideoManagerWebHandler = require('../musicVideoManagerWebHandler');


/**
 * Give the user the first 100 music video results with additional parameters:
 * - search query
 * - page number
 * - sort type :searchQuery?/:pageNumber?/:sortType?
 */
router.get('/', (req, res) => {

    // debug info
    // --------------------------------------------
    console.log(">>> Get song list");
    console.log("-> URL:    \"" + req.baseUrl + req.url + "\"");
    console.log("-> QUERYS: \"" + JSON.stringify(req.query) + "\"");

    // get the given url parameter
    // --------------------------------------------
    const searchQuery = req.query.search;
    const pageNumber = req.query.page;
    const sortType = req.query.sort;
    const jsonBoolean = req.query.json;

    // check all the variables
    // --------------------------------------------
    const searchWanted = (searchQuery !== undefined);
    const specialPageWanted = (pageNumber !== undefined && !isNaN(pageNumber) && pageNumber >= 1);
    const sortWanted = (sortType !== undefined && musicVideoManagerWebHandler.isCorrectSongListSortType(sortType));
    const jsonWanted = (jsonBoolean !== undefined && jsonBoolean === "true");

    // react to the results
    // --------------------------------------------
    let returnString = "Song list wanted: ";

    if (searchWanted) returnString += "Search for \"" + searchQuery + "\" ";
    if (specialPageWanted) returnString += "Get me page #\"" + pageNumber + "\" ";
    if (sortWanted) returnString += "And sort it like this \"" + sortType + "\"";

    const jsonArray = { 'json-file': 'with the following content: ' + returnString };

    // return json if wanted or else a website
    jsonWanted ? res.json(jsonArray) : res.send(returnString);

});




/**
 * Redirect the user to the error site
 */
router.get((req, res) => {
    let err = new Error('Not Found');
    err.status = 404;
    next(err);
});


// export router back to express
module.exports = router;