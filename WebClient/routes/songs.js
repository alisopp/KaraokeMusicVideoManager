// import express and create a new router
const router = require('express').Router();


/**
 * MusicVideoManagerWebHandler module
 * (contains MusicVideoManagerWebHandler object)
 */
const musicVideoManagerWebHandler = require('../musicVideoManagerWebHandler');


/**
 * Give the user the first 100 music video results
 */
router.get('/', (req, res) => {

  // debug info
  console.log(">> Get song list [default]");
  console.log(req.baseUrl + req.url);

  // return the song list with the first 100 results
  res.send("hi >> song list (first 100 results)");

});
router.get('/json/', (req, res) => {
  res.json("hi >> song list (first 100 results)");
});


/**
 * Give the user the first 100 music video results with additional parameters:
 * - search query
 */
router.get('/:searchQuery', (req, res) => {

  // debug info
  console.log(">> Get song list [searchQuery]");
  console.log(req.baseUrl + req.url);
  console.log(req.params);

  // get the given parameter
  const searchQuery = req.params.searchQuery;

  // check if the search query is "incorrect"
  if (searchQuery === undefined || searchQuery === '*') {
    // if yes redirect the user to the default song list website
    res.redirect('/songs');
    return;
  }

  // if the parameters are ok get the website for the given search query
  res.send("hi >> song list (first 100 results of the query \"" + searchQuery + "\")");

});
router.get('/json/:searchQuery', (req, res) => {
  const searchQuery = req.params.searchQuery;
  if (searchQuery === undefined || searchQuery === '*') { res.redirect('/songs/json'); return; }
  res.json("hi >> song list (first 100 results of the query \"" + searchQuery + "\")");
});

/**
 * Give the user the first 100 music video results
 * - search query
 * - page number
 */
router.get('/:searchQuery/:pageNumber', (req, res) => {

  // debug info
  console.log(">> Get song list [searchQuery, pageNumber]");
  console.log(req.baseUrl + req.url);
  console.log(req.params);

  // get the given parameters
  const searchQuery = req.params.searchQuery;
  const pageNumber = req.params.pageNumber;

  // check if the search query is "incorrect"
  if (searchQuery === undefined) {
    // if yes redirect the user to the default song list website
    res.redirect('/songs');
    return;
  }

  // check if the page number is "incorrect"
  if (pageNumber === undefined || pageNumber < 1) {
    // if yes redirect the user to the query router
    res.redirect('/songs/' + searchQuery);
    return;
  }

  // if the parameters are ok get the website for the given search query and page number
  res.send("hi - wecome to page #" + pageNumber + " (query: \"" + searchQuery + "\")");

});
router.get('/json/:searchQuery/:pageNumber', (req, res) => {
  const searchQuery = req.params.searchQuery;
  const pageNumber = req.params.pageNumber;
  if (searchQuery === undefined) { res.redirect('/songs/json/'); return; }
  if (pageNumber === undefined || pageNumber < 1) { res.redirect('/songs/json/' + searchQuery); return; }
  res.json("hi - wecome to page #" + pageNumber + " (query: \"" + searchQuery + "\")");
});


/**
 * Give the user the first 100 music video results with additional parameters:
 * - search query
 * - page number
 * - sort type
 */
router.get('/:searchQuery/:pageNumber/:sortType', (req, res) => {

  // debug info
  console.log(">> Get song list [searchQuery, pageNumber, sortType]");
  console.log(req.baseUrl + req.url);
  console.log(req.params);

  // get the given parameters
  const searchQuery = req.params.searchQuery;
  const pageNumber = req.params.pageNumber;
  const sortType = req.params.sortType;

  // check if the search query is "incorrect"
  if (searchQuery === undefined) {
    // if yes redirect the user to the default song list website
    res.redirect('/songs');
    return;
  }

  // check if the page number is "incorrect"
  if (pageNumber === undefined || pageNumber < 1) {
    // if yes redirect the user to the query router
    res.redirect('/songs/' + searchQuery);
    return;
  }

  // check if the sort type is "incorrect"
  if (sortType === undefined || !musicVideoManagerWebHandler.isCorrectSongListSortType(sortType)) {
    // if yes redirect the user to the query and page router
    res.redirect('/songs/' + searchQuery + '/' + pageNumber);
    return;
  }

  // if the parameters are ok get the website for the given search query and page number
  res.send("hi - wecome to page #" + pageNumber + " (query: \"" + searchQuery + "\", sort type: \"" + sortType + ")");

});
router.get('/json/:searchQuery/:pageNumber/:sortType', (req, res) => {
  const searchQuery = req.params.searchQuery;
  const pageNumber = req.params.pageNumber;
  const sortType = req.params.sortType;
  if (searchQuery === undefined) { res.redirect('/songs/json/'); return; }
  if (pageNumber === undefined || pageNumber < 1) { res.redirect('/songs/json/' + searchQuery); return; }
  if (sortType === undefined || !musicVideoManagerWebHandler.isCorrectSongListSortType(sortType)) { res.redirect('/songs/json/' + searchQuery + '/' + pageNumber); return; }
  res.json("hi - wecome to page #" + pageNumber + " (query: \"" + searchQuery + "\", sort type: \"" + sortType + ")");
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
