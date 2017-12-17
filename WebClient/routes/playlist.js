const router = require('express').Router();

/**
 * MusicVideoManagerWebHandler module
 * (contains MusicVideoManagerWebHandler object)
 */
const musicVideoManagerWebHandler = require('../musicVideoManagerWebHandler');


/**
 * Give the user the whole playlist
 */
router.get('/', (req, res) => {

  // debug info
  console.log(">> Get playlist [default]");
  console.log(req.baseUrl + req.url);

  // return the song list with the first 100 results
  res.send("hi >> playlist (first 100 results)");

});
router.get('/json/', (req, res) => {
  res.send("hi >> playlist");
});


/**
 * Give the user the whole playlist with different sort options
 */
router.get('/:sortType', (req, res) => {

  // debug info
  console.log(">> Get playlist [sortType]");
  console.log(req.baseUrl + req.url);
  console.log(req.params);

  // get the given parameter
  const sortType = req.params.sortType;

  // check if the search query is "incorrect"
  if (sortType === undefined || !musicVideoManagerWebHandler.isCorrectPlaylistSortType(sortType)) {
    // if yes redirect the user to the default song list website
    res.redirect('/');
    return;
  }

  // if the parameters are ok get the website for the given search query
  res.send("hi >> playlist (sorted like this: \"" + sortType + "\")");

});
router.get('/json/:sortType', (req, res) => {
  const sortType = req.params.sortType;
  if (sortType === undefined || !musicVideoManagerWebHandler.isCorrectPlaylistSortType(sortType)) { res.redirect('/json/'); return; }
  res.json("hi >> playlist (first 100 results sorted like this: \"" + sortType + "\")");
});



// error handling
router.get((req, res) => {
  let err = new Error('Not Found');
  err.status = 404;
  next(err);
});

module.exports = router;
