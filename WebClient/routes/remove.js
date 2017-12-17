// import express and create a new router
const router = require('express').Router();


/**
 * MusicVideoManagerWebHandler module
 * (contains MusicVideoManagerWebHandler object)
 */
const musicVideoManagerWebHandler = require('../musicVideoManagerWebHandler');


/**
 * Remove a song/music video
 */
router.post('/song/', (req, res) => {

  // debug info
  console.log(">> Post \"remove song\"");
  console.log(req.baseUrl + req.url);
  console.log(req.body);

  res.send("TODO");

});


/**
 * Remove a playlist entry
 */
router.post('/playlist/', (req, res) => {

  // debug info
  console.log(">> Post \"remove a playlist entry\"");
  console.log(req.baseUrl + req.url);
  console.log(req.body);

  res.send("TODO");

});


/**
 * Remove a comment
 */
router.post('/comment/', (req, res) => {

  // debug info
  console.log(">> Post \"remove a comment\"");
  console.log(req.baseUrl + req.url);
  console.log(req.body);

  res.send("TODO");

});


/**
 * Remove a user
 */
router.post('/user/', (req, res) => {

  // debug info
  console.log(">> Post \"remove a user\"");
  console.log(req.baseUrl + req.url);
  console.log(req.body);

  res.send("TODO");

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
